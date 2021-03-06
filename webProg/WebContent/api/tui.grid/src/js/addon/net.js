/**
 * @fileoverview Add-on for binding to remote data
 * @author NHN. FE Development Lab <dl_javascript@nhn.com>
 */

'use strict';

var $ = require('jquery');
var Backbone = require('backbone');
var _ = require('underscore');

var View = require('../base/view');
var Router = require('./net-router');
var util = require('../common/util');
var formUtil = require('../common/formUtil');
var i18n = require('../common/i18n');
var GridEvent = require('../event/gridEvent');

var renderStateMap = require('../common/constMap').renderState;
var DELAY_FOR_LOADING_STATE = 200;

var requestMessageMap = {
    createData: 'net.confirmCreate',
    updateData: 'net.confirmUpdate',
    deleteData: 'net.confirmDelete',
    modifyData: 'net.confirmModify'
};
var errorMessageMap = {
    createData: 'net.noDataToCreate',
    updateData: 'net.noDataToUpdate',
    deleteData: 'net.noDataToDelete',
    modifyData: 'net.noDataToModify'
};

/**
 * Add-on for binding to remote data
 * @module addon/net
 * @param {object} options
 *      @param {jquery} [options.el] - Form element (to be used for ajax request)
 *      @param {boolean} [options.initialRequest=true] - Whether to request 'readData' after initialized
 *      @param {string} [options.readDataMethod='POST'] - Http method to be used for 'readData' API ('POST' or 'GET')
 *      @param {object} [options.api] - URL map
 *          @param {string} [options.api.readData] - URL for read-data
 *          @param {string} [options.api.createData] - URL for create
 *          @param {string} [options.api.updateData] - URL for update
 *          @param {string} [options.api.modifyData] - URL for modify (create/update/delete at once)
 *          @param {string} [options.api.deleteData] - URL for delete
 *          @param {string} [options.api.downloadExcel] - URL for download data of this page as an excel-file
 *          @param {string} [options.api.downloadExcelAll] - URL for download all data as an excel-file
 *      @param {number} [options.perPage=500] - The number of items to be shown in a page
 *      @param {boolean} [options.enableAjaxHistory=true] - Whether to use the browser history for the ajax requests
 *      @param {boolean} [options.withCredentials=false] - Use withCredentials flag of XMLHttpRequest for ajax requests if true
allow cross-domain requests if true
 * @example
 *   <form id="data_form">
 *   <input type="text" name="query"/>
 *   </form>
 *   <script>
 *      var net;
 *      var grid = new tui.Grid({
 *          //...options...
 *      });
 *
 *      // Activate 'Net' addon
 *      grid.use('Net', {
 *         el: $('#data_form'),
 *         initialRequest: true,
 *         readDataMethod: 'GET',
 *         perPage: 500,
 *         enableAjaxHistory: true,
 *         withCredentials: false,
 *         api: {
 *             'readData': './api/read',
 *             'createData': './api/create',
 *             'updateData': './api/update',
 *             'deleteData': './api/delete',
 *             'modifyData': './api/modify',
 *             'downloadExcel': './api/download/excel',
 *             'downloadExcelAll': './api/download/excelAll'
 *         }
 *      });
 *
 *      // Bind event handlers
 *      grid.on('beforeRequest', function(data) {
 *          // For all requests
 *      }).on('response', function(data) {
 *          // For all response (regardless of success or failure)
 *      }).on('successResponse', function(data) {
 *          // Only if response.result is true
 *      }).on('failResponse', function(data) {
 *          // Only if response.result is false
 *      }).on('errorResponse', function(data) {
 *          // For error response
 *      });
 *
 *      net = grid.getAddOn('Net');
 *
 *      // Request create
 *      net.request('createData');
 *
 *      // Request update
 *      net.request('updateData');
 *
 *      // Request delete
 *      net.request('deleteData');
 *
 *      // Request create/update/delete at once
 *      net.request('modifyData');
 *   </script>
 */
var Net = View.extend(/** @lends module:addon/net.prototype */{
    initialize: function(options) {
        var defaultOptions = {
            initialRequest: true,
            perPage: 500,
            enableAjaxHistory: true,
            withCredentials: false
        };
        var defaultApi = {
            readData: '',
            createData: '',
            updateData: '',
            deleteData: '',
            modifyData: '',
            downloadExcel: '',
            downloadExcelAll: ''
        };

        options = _.assign(defaultOptions, options);
        options.api = _.assign(defaultApi, options.api);

        _.assign(this, {
            // models
            dataModel: options.dataModel,
            renderModel: options.renderModel,

            // extra objects
            router: null,
            domEventBus: options.domEventBus,
            pagination: options.pagination,

            // configs
            api: options.api,
            enableAjaxHistory: options.enableAjaxHistory,
            readDataMethod: options.readDataMethod || 'POST',
            perPage: options.perPage,
            withCredentials: options.withCredentials,

            // state data
            curPage: 1,
            timeoutIdForDelay: null,
            requestedFormData: null,
            isLocked: false,
            lastRequestedReadData: null
        });

        this._initializeDataModelNetwork();
        this._initializeRouter();
        this._initializePagination();

        this.listenTo(this.dataModel, 'sortChanged', this._onSortChanged);
        this.listenTo(this.domEventBus, 'click:excel', this._onClickExcel);

        if (options.initialRequest) {
            if (!this.lastRequestedReadData) {
                this._readDataAt(1, false);
            }
        }
    },

    tagName: 'form',

    events: {
        submit: '_onSubmit'
    },

    /**
     * pagination instance ??? ????????? ??????.
     * @private
     */
    _initializePagination: function() {
        var pagination = this.pagination;

        if (pagination) {
            pagination.setItemsPerPage(this.perPage);
            pagination.setTotalItems(1);
            pagination.on('beforeMove', $.proxy(this._onPageBeforeMove, this));
        }
    },

    /**
     * Event listener for 'route:read' event on Router
     * @param  {String} queryStr - Query string
     * @private
     */
    _onRouterRead: function(queryStr) {
        var data = util.toQueryObject(queryStr);
        this._requestReadData(data);
    },

    /**
     * Event listener for 'click:excel' event on domEventBus
     * @param {module:event/gridEvent} gridEvent - GridEvent
     * @private
     */
    _onClickExcel: function(gridEvent) {
        var downloadType = (gridEvent.type === 'all') ? 'excelAll' : 'excel';
        this.download(downloadType);
    },

    /**
     * dataModel ??? network ????????? ??? ??? ????????? ????????????.
     * @private
     */
    _initializeDataModelNetwork: function() {
        this.dataModel.url = this.api.readData;
        this.dataModel.sync = $.proxy(this._sync, this);
    },

    /**
     * ajax history ??? ???????????? ?????? router ??? ???????????????.
     * @private
     */
    _initializeRouter: function() {
        if (this.enableAjaxHistory) {
            this.router = new Router({
                net: this
            });
            this.listenTo(this.router, 'route:read', this._onRouterRead);

            if (!Backbone.History.started) {
                Backbone.history.start();
            }
        }
    },

    /**
     * pagination ?????? before page move??? ???????????? ??? ????????? ?????????
     * @param {{page:number}} customEvent pagination ???????????? ???????????? ????????? ??????
     * @private
     */
    _onPageBeforeMove: function(customEvent) {
        var page = customEvent.page;
        if (this.curPage !== page) {
            this._readDataAt(page, true);
        }
    },

    /**
     * form ??? submit ????????? ????????? ????????? ?????????
     * @param {event} submitEvent   submit ????????? ??????
     * @private
     */
    _onSubmit: function(submitEvent) {
        submitEvent.preventDefault();
        this._readDataAt(1, false);
    },

    /**
     * ??? ???????????? ????????????.
     * @param {Object} data - ??? ????????? ??????
     * @private
     */
    _setFormData: function(data) {
        var formData = _.clone(data);

        _.each(this.lastRequestedReadData, function(value, key) {
            if ((_.isUndefined(formData[key]) || _.isNull(formData[key])) && value) {
                formData[key] = '';
            }
        });
        formUtil.setFormData(this.$el, formData);
    },

    /**
     * fetch ?????? ?????? custom ajax ?????? ????????? ?????? Backbone ??? ?????? sync ??? ??????????????? ???????????? ?????????.
     * @param {String} method   router ????????? ???????????? method ???
     * @param {Object} model    fetch ??? ????????? dataModel
     * @param {Object} options  request ??????
     * @private
     */
    _sync: function(method, model, options) {
        var params;
        if (method === 'read') {
            options = options || {};
            params = $.extend({}, options);
            if (!options.url) {
                params.url = _.result(model, 'url');
            }
            this._ajax(params);
        } else {
            Backbone.sync(Backbone, method, model, options);
        }
    },

    /**
     * network ????????? ?????? _lock ??? ??????.
     * @private
     */
    _lock: function() {
        var renderModel = this.renderModel;

        this.timeoutIdForDelay = setTimeout(function() {
            renderModel.set('state', renderStateMap.LOADING);
        }, DELAY_FOR_LOADING_STATE);

        this.isLocked = true;
    },

    /**
     * network ????????? ?????? unlock ??????.
     * loading layer hide ??? rendering ?????? ???????????? ????????????.
     * @private
     */
    _unlock: function() {
        if (this.timeoutIdForDelay !== null) {
            clearTimeout(this.timeoutIdForDelay);
            this.timeoutIdForDelay = null;
        }

        this.isLocked = false;
    },

    /**
     * form ?????? ????????? ??????????????? Data ??? ????????????.
     * @returns {object} formData ????????? ????????????
     * @private
     */
    _getFormData: function() {
        return formUtil.getFormData(this.$el);
    },

    /**
     * DataModel ?????? Backbone.fetch ?????? ?????? success ??????
     * @param {object} dataModel grid ??? dataModel
     * @param {object} responseData ?????? ?????????
     * @private
     */
    _onReadSuccess: function(dataModel, responseData) {
        var pagination = this.pagination;
        var page, totalCount;

        dataModel.setOriginalRowList();

        if (pagination && responseData.pagination) {
            page = responseData.pagination.page;
            totalCount = responseData.pagination.totalCount;

            pagination.setItemsPerPage(this.perPage);
            pagination.setTotalItems(totalCount);
            pagination.movePageTo(page);
            this.curPage = page;
        }
    },

    /**
     * DataModel ?????? Backbone.fetch ?????? ?????? error ??????
     * @param {object} dataModel grid ??? dataModel
     * @param {object} responseData ?????? ?????????
     * @param {object} options  ajax ?????? ??????
     * @private
     */
    _onReadError: function(dataModel, responseData, options) {}, // eslint-disable-line

    /**
     * Requests 'readData' with last requested data.
     */
    reloadData: function() {
        this._requestReadData(this.lastRequestedReadData);
    },

    /**
     * Requests 'readData' to the server. The last requested data will be extended with new data.
     * @param {Number} page - Page number
     * @param {Object} data - Data(parameters) to send to the server
     * @param {Boolean} resetData - If set to true, last requested data will be ignored.
     */
    readData: function(page, data, resetData) {
        if (resetData) {
            if (!data) {
                data = {};
            }
            data.perPage = this.perPage;
            this._changeSortOptions(data, this.dataModel.sortOptions);
        } else {
            data = _.assign({}, this.lastRequestedReadData, data);
        }
        data.page = page;
        this._requestReadData(data);
    },

    /**
     * ????????? ?????? ??????.
     * @param {object} data ????????? ????????? request ????????????
     * @private
     */
    _requestReadData: function(data) {
        var startNumber = 1;

        this._setFormData(data);

        if (!this.isLocked) {
            this.renderModel.initializeVariables();
            this._lock();

            this.requestedFormData = _.clone(data);
            this.curPage = data.page || this.curPage;
            startNumber = ((this.curPage - 1) * this.perPage) + 1;
            this.renderModel.set({
                startNumber: startNumber
            });

            // ????????? ????????? reloadData?????? ???????????? ?????? data ??? ?????????.
            this.lastRequestedReadData = _.clone(data);
            this.dataModel.fetch({
                requestType: 'readData',
                data: data,
                type: this.readDataMethod,
                success: $.proxy(this._onReadSuccess, this),
                error: $.proxy(this._onReadError, this),
                reset: true,
                withCredentials: this.withCredentials
            });
            this.dataModel.setSortOptionValues(data.sortColumn, data.sortAscending);
        }

        if (this.router) {
            this.router.navigate('read/' + util.toQueryString(data), {
                trigger: false
            });
        }
    },

    /**
     * sortChanged ????????? ????????? ???????????? ??????
     * @private
     * @param {object} sortOptions ?????? ??????
     * @param {string} sortOptions.sortColumn ????????? ?????????
     * @param {boolean} sortOptions.ascending ???????????? ??????
     */
    _onSortChanged: function(sortOptions) {
        if (sortOptions.requireFetch) {
            this._readDataAt(1, true, sortOptions);
        }
    },

    /**
     * ????????? ????????? ?????? ?????? ?????? ?????? ????????????.
     * @private
     * @param {object} data ????????? ??????
     * @param {object} sortOptions ?????? ??????
     * @param {string} sortOptions.sortColumn ????????? ?????????
     * @param {boolean} sortOptions.ascending ???????????? ??????
     */
    _changeSortOptions: function(data, sortOptions) {
        if (!sortOptions) {
            return;
        }
        if (sortOptions.columnName === 'rowKey') {
            delete data.sortColumn;
            delete data.sortAscending;
        } else {
            data.sortColumn = sortOptions.columnName;
            data.sortAscending = sortOptions.ascending;
        }
    },

    /**
     * ?????? form data ????????????, page ??? ???????????? ???????????? ?????? ??????.
     * @param {Number} page ????????? ????????? ??????
     * @param {Boolean} [isUsingRequestedData=true] page ?????? ???????????????, form ??????????????? ???????????? ?????? ?????? form ???????????? ???????????? ????????? ????????????.
     * @param {object} sortOptions ?????? ??????
     * @param {string} sortOptions.sortColumn ????????? ?????????
     * @param {boolean} sortOptions.ascending ???????????? ??????
     * @private
     */
    _readDataAt: function(page, isUsingRequestedData, sortOptions) {
        var data;

        isUsingRequestedData = _.isUndefined(isUsingRequestedData) ? true : isUsingRequestedData;
        data = isUsingRequestedData ? this.requestedFormData : this._getFormData();
        data.page = page;
        data.perPage = this.perPage;
        this._changeSortOptions(data, sortOptions);
        this._requestReadData(data);
    },

    /**
     * Send request to server to sync data
     * @param {String} requestType - 'createData|updateData|deleteData|modifyData'
     * @param {object} options - Options
     *      @param {String} [options.url] - URL to send the request
     *      @param {boolean} [options.hasDataParam=true] - Whether the row-data to be included in the request param
     *      @param {boolean} [options.checkedOnly=true] - Whether the request param only contains checked rows
     *      @param {boolean} [options.modifiedOnly=true] - Whether the request param only contains modified rows
     *      @param {boolean} [options.showConfirm=true] - Whether to show confirm dialog before sending request
     *      @param {boolean} [options.updateOriginal=false] - Whether to update original data with current data
     *      @param {boolean} [options.withCredentials=false] - Use withCredentials flag of XMLHttpRequest for ajax requests if true
     * @returns {boolean} Whether requests or not
     */
    request: function(requestType, options) {
        var newOptions = _.extend({
            url: this.api[requestType],
            type: null,
            hasDataParam: true,
            checkedOnly: true,
            modifiedOnly: true,
            showConfirm: true,
            updateOriginal: false
        }, options);
        var param = this._getRequestParam(requestType, newOptions);

        if (param) {
            if (newOptions.updateOriginal) {
                this.dataModel.setOriginalRowList();
            }
            this._ajax(param);
        }

        return !!param;
    },

    /**
     * Change window.location to registered url for downloading data
     * @param {string} type - Download type. 'excel' or 'excelAll'.
     *        Will be matched with API 'downloadExcel', 'downloadExcelAll'.
     */
    download: function(type) {
        var apiName = 'download' + util.toUpperCaseFirstLetter(type),
            data = this.requestedFormData,
            url = this.api[apiName],
            paramStr;

        if (type === 'excel') {
            data.page = this.curPage;
            data.perPage = this.perPage;
        } else {
            data = _.omit(data, 'page', 'perPage');
        }

        paramStr = $.param(data);
        window.location = url + '?' + paramStr;
    },

    /**
     * Set number of rows per page and reload current page
     * @param {number} perPage - Number of rows per page
     */
    setPerPage: function(perPage) {
        this.perPage = perPage;
        this._readDataAt(1);
    },

    /**
     * ????????? ????????? ????????? ???????????? ??? Grid ??? ???????????? ???????????? ???????????? Option ??? ????????? ????????????.
     * @param {String} requestType  ?????? ??????. 'createData|updateData|deleteData|modifyData' ??? ????????? ????????? ?????????.
     * @param {Object} [options] Options
     *      @param {boolean} [options.hasDataParam=true] request ???????????? rows ?????? ???????????? ????????? ??? ??????.
     *      @param {boolean} [options.modifiedOnly=true] rows ?????? ????????? ??? ????????? ???????????? ????????? ??? ??????
     *      @param {boolean} [options.checkedOnly=true] rows ?????? ????????? ??? checked ??? ???????????? ????????? ??? ??????
     * @returns {{count: number, data: {requestType: string, url: string, rows: object,
     *      type: string, dataType: string}}} ?????? ????????? ???????????? ????????? ????????? ??????
     * @private
     */
    _getDataParam: function(requestType, options) {
        var dataModel = this.dataModel,
            checkMap = {
                createData: ['createdRows'],
                updateData: ['updatedRows'],
                deleteData: ['deletedRows'],
                modifyData: ['createdRows', 'updatedRows', 'deletedRows']
            },
            checkList = checkMap[requestType],
            data = {},
            count = 0,
            dataMap;

        options = _.defaults(options || {}, {
            hasDataParam: true,
            modifiedOnly: true,
            checkedOnly: true
        });

        if (options.hasDataParam) {
            if (options.modifiedOnly) {
                // {createdRows: [], updatedRows:[], deletedRows: []} ??? ?????????.
                dataMap = dataModel.getModifiedRows({
                    checkedOnly: options.checkedOnly
                });
                _.each(dataMap, function(list, name) {
                    if (_.contains(checkList, name) && list.length) {
                        count += list.length;
                        data[name] = JSON.stringify(list);
                    }
                }, this);
            } else {
                // {rows: []} ??? ?????????.
                data.rows = dataModel.getRows(options.checkedOnly);
                count = data.rows.length;
            }
        }

        return {
            data: data,
            count: count
        };
    },

    /**
     * requestType ??? ?????? ????????? ????????? ??????????????? ????????????.
     * @param {String} requestType ?????? ??????. 'createData|updateData|deleteData|modifyData' ??? ????????? ????????? ?????????.
     * @param {Object} [options] Options
     *      @param {String} [options.url=this.api[requestType]] ????????? url.
     *      ???????????? ?????? ??? option ?????? ?????? API ??? request Type ??? ???????????? url ??? ?????????
     *      @param {String} [options.type='POST'] request method ??????
     *      @param {boolean} [options.hasDataParam=true] request ???????????? rowList ?????? ???????????? ????????? ??? ??????.
     *      @param {boolean} [options.modifiedOnly=true] rowList ?????? ????????? ??? ????????? ???????????? ????????? ??? ??????
     *      @param {boolean} [options.checkedOnly=true] rowList ?????? ????????? ??? checked ??? ???????????? ????????? ??? ??????
     * @returns {{requestType: string, url: string, data: object, type: string, dataType: string}}
     *      ajax ????????? ????????? option ????????????
     * @private
     */
    _getRequestParam: function(requestType, options) {
        var defaultOptions = {
            url: this.api[requestType],
            type: null,
            hasDataParam: true,
            modifiedOnly: true,
            checkedOnly: true,
            withCredentials: this.withCredentials
        };
        var newOptions = $.extend(defaultOptions, options);
        var dataParam = this._getDataParam(requestType, newOptions);
        var param = null;

        if (!newOptions.showConfirm || this._isConfirmed(requestType, dataParam.count)) {
            param = {
                requestType: requestType,
                url: newOptions.url,
                data: dataParam.data,
                type: newOptions.type,
                withCredentials: newOptions.withCredentials
            };
        }

        return param;
    },

    /**
     * requestType ??? ?????? ?????? ???????????? ????????????.
     * @param {String} requestType ?????? ??????. 'createData|updateData|deleteData|modifyData' ??? ????????? ????????? ?????????.
     * @param {Number} count   ????????? ????????? ??????
     * @returns {boolean}    ?????? ???????????? ????????? ????????????.
     * @private
     */
    _isConfirmed: function(requestType, count) {
        var result = false;

        if (count > 0) {
            result = confirm(this._getConfirmMessage(requestType, count));
        } else {
            alert(this._getConfirmMessage(requestType, count));
        }

        return result;
    },

    /**
     * confirm message ??? ????????????.
     * @param {String} requestType ?????? ??????. 'createData|updateData|deleteData|modifyData' ??? ????????? ????????? ?????????.
     * @param {Number} count ????????? ????????? ??????
     * @returns {string} ????????? confirm ?????????
     * @private
     */
    _getConfirmMessage: function(requestType, count) {
        var messageKey = (count > 0) ? requestMessageMap[requestType] : errorMessageMap[requestType];
        var replacedValues = {
            count: count
        };

        return i18n.get(messageKey, replacedValues);
    },

    /**
     * Request server using $.ajax
     * @param {object} options - request parameters for $.ajax
     *     @param {string} options.url - url
     *     @param {object} [options.data] - data
     *     @param {string} [options.type] - 'GET|POST'
     *     @param {string} [options.dataType] - 'text|html|xml|json|jsonp'
     *     @param {string} [options.requestType] - 'createData|updateData|deleteData|modifyData'
     *     @param {boolean} [options.withCredentials=false] - use withCredentials flag of XMLHttpRequest for ajax requests if true
     * @private
     */
    _ajax: function(options) {
        var gridEvent = new GridEvent(null, options.data);
        var params;

        /**
         * Occurs before the http request is sent
         * @event Grid#beforeRequest
         * @type {module:event/gridEvent}
         * @property {Grid} instance - Current grid instance
         */
        this.trigger('beforeRequest', gridEvent);
        if (gridEvent.isStopped()) {
            return;
        }

        options = $.extend({requestType: ''}, options);
        params = {
            url: options.url,
            data: options.data || {},
            type: options.type || 'POST',
            dataType: options.dataType || 'json',
            complete: $.proxy(this._onComplete, this, options.complete, options),
            success: $.proxy(this._onSuccess, this, options.success, options),
            error: $.proxy(this._onError, this, options.error, options),
            xhrFields: {
                withCredentials: options.withCredentials
            }
        };
        if (options.url) {
            $.ajax(params);
        }
    },

    /**
     * ajax complete ????????? ?????????
     * @param {Function} callback   ?????? ?????? ?????? ????????? ????????????
     * @param {object} jqXHR    jqueryXHR  ??????
     * @param {number} status   http status ??????
     * @private
     */
    _onComplete: function(callback, jqXHR, status) { // eslint-disable-line no-unused-vars
        this._unlock();
    },

    /* eslint-disable complexity */
    /**
     * ajax success ????????? ?????????
     * @param {Function} callback Callback function
     * @param {{requestType: string, url: string, data: object, type: string, dataType: string}} options ajax ?????? ????????????
     * @param {Object} responseData ?????? ?????????
     * @param {number} status   http status ??????
     * @param {object} jqXHR    jqueryXHR  ??????
     * @private
     */
    _onSuccess: function(callback, options, responseData, status, jqXHR) {
        var responseMessage = responseData && responseData.message;
        var gridEvent = new GridEvent(null, {
            httpStatus: status,
            requestType: options.requestType,
            requestParameter: options.data,
            responseData: responseData
        });

        /**
         * Occurs when the response is received from the server
         * @event Grid#response
         * @type {module:event/gridEvent}
         * @property {number} httpStatus - HTTP status
         * @property {string} requestType - Request type
         * @property {string} requestParameter - Request parameters
         * @property {Object} responseData - response data
         * @property {Grid} instance - Current grid instance
         */
        this.trigger('response', gridEvent);
        if (gridEvent.isStopped()) {
            return;
        }
        if (responseData && responseData.result) {
            /**
             * Occurs after the response event, if the result is true
             * @event Grid#successResponse
             * @type {module:event/gridEvent}
             * @property {number} httpStatus - HTTP status
             * @property {string} requestType - Request type
             * @property {string} requestParameter - Request parameter
             * @property {Object} responseData - response data
             * @property {Grid} instance - Current grid instance
             */
            this.trigger('successResponse', gridEvent);
            if (gridEvent.isStopped()) {
                return;
            }
            if (_.isFunction(callback)) {
                callback(responseData.data || {}, status, jqXHR);
            }
        } else {
            /**
             * Occurs after the response event, if the result is false
             * @event Grid#failResponse
             * @type {module:event/gridEvent}
             * @property {number} httpStatus - HTTP status
             * @property {string} requestType - Request type
             * @property {string} requestParameter - Request parameter
             * @property {Object} responseData - response data
             * @property {Grid} instance - Current grid instance
             */
            this.trigger('failResponse', gridEvent);
            if (gridEvent.isStopped()) {
                return;
            }
            if (responseMessage) {
                alert(responseMessage);
            }
        }
    },
    /* eslint-enable complexity */

    /**
     * ajax error ????????? ?????????
     * @param {Function} callback Callback function
     * @param {{requestType: string, url: string, data: object, type: string, dataType: string}} options ajax ?????? ????????????
     * @param {object} jqXHR    jqueryXHR  ??????
     * @param {number} status   http status ??????
     * @param {String} errorMessage ?????? ?????????
     * @private
     */
    _onError: function(callback, options, jqXHR, status) {
        var eventData = new GridEvent(null, {
            httpStatus: status,
            requestType: options.requestType,
            requestParameter: options.data,
            responseData: null
        });
        this.renderModel.set('state', renderStateMap.DONE);

        this.trigger('response', eventData);
        if (eventData.isStopped()) {
            return;
        }

        /**
         * Occurs after the response event, if the response is Error
         * @event Grid#errorResponse
         * @type {module:event/gridEvent}
         * @property {number} httpStatus - HTTP status
         * @property {string} requestType - Request type
         * @property {string} requestParameter - Request parameters
         * @property {Grid} instance - Current grid instance
         */
        this.trigger('errorResponse', eventData);
        if (eventData.isStopped()) {
            return;
        }

        if (jqXHR.readyState > 1) {
            alert(i18n.get('net.failResponse'));
        }
    }
});

module.exports = Net;
