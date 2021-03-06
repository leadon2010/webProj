/**
 * @fileoverview Focus Model
 * @author NHN. FE Development Lab <dl_javascript@nhn.com>
 */

'use strict';

var _ = require('underscore');

var Model = require('../base/model');
var util = require('../common/util');
var GridEvent = require('../event/gridEvent');

/**
 * whether using mobile browser
 * @type {boolean}
 * @private
 */
var _isMobile = util.isMobile();

/**
 * Focus model
 * @param {Object} attrs - Attributes
 * @param {Object} options - Options
 * @module model/focus
 * @extends module:base/model
 * @ignore
 */
var Focus = Model.extend(/** @lends module:model/focus.prototype */{
    initialize: function(attrs, options) {
        var editEventName = options.editingEvent + ':cell';
        var domEventBus;

        Model.prototype.initialize.apply(this, arguments);

        _.assign(this, {
            dataModel: options.dataModel,
            columnModel: options.columnModel,
            coordRowModel: options.coordRowModel,
            domEventBus: options.domEventBus,
            domState: options.domState
        });

        this.listenTo(this.dataModel, 'reset', this._onResetData);
        this.listenTo(this.dataModel, 'add', this._onAddDataModel);

        if (this.domEventBus) {
            domEventBus = this.domEventBus;
            this.listenTo(domEventBus, editEventName, this._onMouseClickEdit);
            this.listenTo(domEventBus, 'mousedown:focus', this._onMouseDownFocus);
            this.listenTo(domEventBus, 'key:move', this._onKeyMove);
            this.listenTo(domEventBus, 'key:edit', this._onKeyEdit);
        }
    },

    defaults: {
        /**
         * row key of the current cell
         * @type {String|Number}
         */
        rowKey: null,

        /**
         * column name of the current cell
         * @type {String}
         */
        columnName: null,

        /**
         * row key of the previously focused cell
         * @type {String|Number}
         */
        prevRowKey: null,

        /**
         * column name of the previously focused cell
         * @type {String}
         */
        prevColumnName: '',

        /**
         * address of the editing cell
         * @type {{rowKey:(String|Number), columnName:String}}
         */
        editingAddress: null,

        /**
         * Whether focus state is active or not
         * @type {Boolean}
         */
        active: false
    },

    /**
     * Event handler for 'reset' event on dataModel.
     * @private
     */
    _onResetData: function() {
        this.blur();
    },

    /**
     * Event handler for 'add' event on dataModel.
     * @param  {module:model/data/rowList} dataModel - data model
     * @param  {Object} options - options for appending. See {@link module:model/data/rowList#append}
     * @private
     */
    _onAddDataModel: function(dataModel, options) {
        if (options.focus) {
            this.focusAt(options.at, 0);
        }
    },

    /**
     * Event handler for 'click:cell' or 'dblclick:cell' event on domEventBus
     * @param {module:event/gridEvent} ev - event data
     * @private
     */
    _onMouseClickEdit: function(ev) {
        this.focusIn(ev.rowKey, ev.columnName);
    },

    /* eslint-disable complexity */
    /**
     * Event handler for key:move event
     * @param {module:event/gridEvent} ev - GridEvent
     * @private
     */
    _onKeyMove: function(ev) {
        var rowKey, columnName;

        switch (ev.command) {
            case 'up':
                rowKey = this.prevRowKey();
                break;
            case 'down':
                rowKey = this.nextRowKey();
                break;
            case 'left':
                columnName = this.prevColumnName();
                break;
            case 'right':
                columnName = this.nextColumnName();
                break;
            case 'pageUp':
                rowKey = this._getPageMovedRowKey(false);
                break;
            case 'pageDown':
                rowKey = this._getPageMovedRowKey(true);
                break;
            case 'firstColumn':
                columnName = this.firstColumnName();
                break;
            case 'lastColumn':
                columnName = this.lastColumnName();
                break;
            case 'firstCell':
                rowKey = this.firstRowKey();
                columnName = this.firstColumnName();
                break;
            case 'lastCell':
                rowKey = this.lastRowKey();
                columnName = this.lastColumnName();
                break;
            default:
        }

        rowKey = _.isUndefined(rowKey) ? this.get('rowKey') : rowKey;
        columnName = columnName || this.get('columnName');

        this.focus(rowKey, columnName, true);
    },
    /* eslint-enable complexity */

    /**
     * Event handler for key:edit event
     * @param {module:event/gridEvent} ev - GridEvent
     * @private
     */
    _onKeyEdit: function(ev) {
        var address;

        switch (ev.command) {
            case 'currentCell':
                address = this.which();
                break;
            case 'nextCell':
                address = this.nextAddress();
                break;
            case 'prevCell':
                address = this.prevAddress();
                break;
            default:
        }

        if (address) {
            this.focusIn(address.rowKey, address.columnName, true);
        }
    },

    /**
     * Returns the moved rowKey by page unit from current position
     * @param {boolean} isDownDir - true: down, false: up
     * @returns {number}
     * @private
     */
    _getPageMovedRowKey: function(isDownDir) {
        var rowIndex = this.dataModel.indexOfRowKey(this.get('rowKey'));
        var prevPageRowIndex = this.coordRowModel.getPageMovedIndex(rowIndex, isDownDir);
        var rowKey;

        if (isDownDir) {
            rowKey = this.nextRowKey(prevPageRowIndex - rowIndex);
        } else {
            rowKey = this.prevRowKey(rowIndex - prevPageRowIndex);
        }

        return rowKey;
    },

    /**
     * Event handler for 'mousedown' event on domEventBus
     * @private
     */
    _onMouseDownFocus: function() {
        this.focusClipboard();
    },

    /**
     * Saves previous data.
     * @private
     */
    _savePrevious: function() {
        if (this.get('rowKey') !== null) {
            this.set('prevRowKey', this.get('rowKey'));
        }
        if (this.get('columnName')) {
            this.set('prevColumnName', this.get('columnName'));
        }
    },

    /**
     * Returns whether given rowKey and columnName is equal to current value
     * @param {(Number|String)} rowKey - row key
     * @param {String} columnName - column name
     * @param {Boolean} isMainRowKey - true if the target row key is main row
     * @returns {Boolean} - True if equal
     */
    isCurrentCell: function(rowKey, columnName, isMainRowKey) {
        var curColumnName = this.get('columnName');
        var curRowKey = this.get('rowKey');

        if (isMainRowKey) {
            curRowKey = this.dataModel.getMainRowKey(curRowKey, curColumnName);
        }

        return String(curRowKey) === String(rowKey) && curColumnName === columnName;
    },

    /* eslint-disable complexity */
    /**
     * Focus to the cell identified by given rowKey and columnName.
     * @param {Number|String} rowKey - rowKey
     * @param {String} columnName - columnName
     * @param {Boolean} isScrollable - if set to true, move scroll position to focused position
     * @returns {Boolean} true if focused cell is changed
     */
    focus: function(rowKey, columnName, isScrollable) {
        if (!this.get('active')) {
            this.set('active', true);
        }

        if (!this._isValidCell(rowKey, columnName) ||
            util.isMetaColumn(columnName) ||
            this.isCurrentCell(rowKey, columnName)) {
            return true;
        }

        if (!this._triggerFocusChangeEvent(rowKey, columnName)) {
            return false;
        }

        this.blur();
        this.set({
            rowKey: rowKey,
            columnName: columnName
        });

        this.trigger('focus', rowKey, columnName, isScrollable);

        if (this.columnModel.get('selectType') === 'radio') {
            this.dataModel.check(rowKey);
        }

        return true;
    },
    /* eslint-enable complexity */

    /**
     * Trigger 'focusChange' event and returns the result
     * @param {(number|string)} rowKey - rowKey
     * @param {stringd} columnName - columnName
     * @returns {boolean}
     * @private
     */
    _triggerFocusChangeEvent: function(rowKey, columnName) {
        var gridEvent = new GridEvent(null, {
            rowKey: rowKey,
            prevRowKey: this.get('rowKey'),
            columnName: columnName,
            prevColumnName: this.get('columnName')
        });

        /**
         * Occurs when focused cell is about to change
         * @api
         * @event Grid#focusChange
         * @type {module:event/gridEvent}
         * @property {number} rowKey - rowKey of the target cell
         * @property {number} columnName - columnName of the target cell
         * @property {number} prevRowKey - rowKey of the currently focused cell
         * @property {number} prevColumnName - columnName of the currently focused cell
         * @property {Grid} instance - Current grid instance
         */
        this.trigger('focusChange', gridEvent);

        return !gridEvent.isStopped();
    },

    /**
     * Focus to the cell identified by given rowIndex and columnIndex.
     * @param {(Number|String)} rowIndex - rowIndex
     * @param {String} columnIndex - columnIndex
     * @param {boolean} [isScrollable=false] - if set to true, scroll to focused cell
     * @returns {Boolean} true if success
     */
    focusAt: function(rowIndex, columnIndex, isScrollable) {
        var row = this.dataModel.at(rowIndex);
        var column = this.columnModel.at(columnIndex, true);
        var result = false;

        if (row && column) {
            result = this.focus(row.get('rowKey'), column.name, isScrollable);
        }

        return result;
    },

    /**
     * Focus to the cell identified by given rowKey and columnName and change it to edit-mode if editable.
     * @param {(Number|String)} rowKey - rowKey
     * @param {String} columnName - columnName
     * @param {boolean} [isScrollable=false] - if set to true, scroll to focused cell
     * @returns {Boolean} true if success
     */
    focusIn: function(rowKey, columnName, isScrollable) {
        var result = this.focus(rowKey, columnName, isScrollable);

        if (result) {
            rowKey = this.dataModel.getMainRowKey(rowKey, columnName);

            if (this.dataModel.get(rowKey).isEditable(columnName)) {
                this.finishEditing();
                this.startEditing(rowKey, columnName);
            } else {
                this.focusClipboard();
            }
        }

        return result;
    },

    /**
     * Focus to the cell identified by given rowIndex and columnIndex and change it to edit-mode if editable.
     * @param {(Number|String)} rowIndex - rowIndex
     * @param {String} columnIndex - columnIndex
     * @param {Boolean} [isScrollable=false] - if set to true, scroll to focused cell
     * @returns {Boolean} true if success
     */
    focusInAt: function(rowIndex, columnIndex, isScrollable) {
        var row = this.dataModel.at(rowIndex);
        var column = this.columnModel.at(columnIndex, true);
        var result = false;

        if (row && column) {
            result = this.focusIn(row.get('rowKey'), column.name, isScrollable);
        }

        return result;
    },

    /**
     * clipboard ??? focus ??????.
     */
    focusClipboard: function() {
        if (!_isMobile) {
            this.trigger('focusClipboard');
        }
    },

    /**
     * If the grid has an element which has a focus, make sure that focusModel has a valid data,
     * Otherwise change the focus state.
     */
    refreshState: function() {
        var restored;

        if (!this.domState.hasFocusedElement()) {
            this.set('active', false);
        } else if (!this.has()) {
            restored = this.restore();
            if (!restored) {
                this.focusAt(0, 0);
            }
        }
    },

    /**
     * Apply blur state on cell
     * @returns {Model.Focus} This object
     */
    blur: function() {
        if (!this.has()) {
            return this;
        }

        if (this.has(true)) {
            this._savePrevious();
        }

        this.trigger('blur', this.get('rowKey'), this.get('columnName'));

        this.set({
            rowKey: null,
            columnName: null
        });

        return this;
    },

    /**
     * ?????? focus ????????? ????????????.
     * @returns {{rowKey: (number|string), columnName: string}} ?????? focus ????????? ???????????? rowKey, columnName
     */
    which: function() {
        return {
            rowKey: this.get('rowKey'),
            columnName: this.get('columnName')
        };
    },

    /**
     * ?????? focus ????????? index ???????????? ????????????.
     * @param {boolean} isPrevious ?????? focus ????????? ???????????? ??????
     * @returns {{row: number, column: number}} The object that contains index info
     */
    indexOf: function(isPrevious) {
        var rowKey = isPrevious ? this.get('prevRowKey') : this.get('rowKey');
        var columnName = isPrevious ? this.get('prevColumnName') : this.get('columnName');

        return {
            row: this.dataModel.indexOfRowKey(rowKey),
            column: this.columnModel.indexOfColumnName(columnName, true)
        };
    },

    /**
     * Returns whether has focus.
     * @param {boolean} checkValid - if set to true, check whether the current cell is valid.
     * @returns {boolean} True if has focus.
     */
    has: function(checkValid) {
        var rowKey = this.get('rowKey');
        var columnName = this.get('columnName');

        if (checkValid) {
            return this._isValidCell(rowKey, columnName);
        }

        return !util.isBlank(rowKey) && !util.isBlank(columnName);
    },

    /**
     * Restore previous focus data.
     * @returns {boolean} True if restored
     */
    restore: function() {
        var prevRowKey = this.get('prevRowKey');
        var prevColumnName = this.get('prevColumnName');
        var restored = false;

        if (this._isValidCell(prevRowKey, prevColumnName)) {
            this.focus(prevRowKey, prevColumnName);
            this.set({
                prevRowKey: null,
                prevColumnName: null
            });
            restored = true;
        }

        return restored;
    },

    /**
     * Returns whether the cell identified by given rowKey and columnName is editing now.
     * @param {Number} rowKey - row key
     * @param {String} columnName - column name
     * @returns {Boolean}
     */
    isEditingCell: function(rowKey, columnName) {
        var address = this.get('editingAddress');

        return address &&
            (String(address.rowKey) === String(rowKey)) &&
            (address.columnName === columnName);
    },

    /**
     * Starts editing a cell identified by given rowKey and columnName, and returns the result.
     * @param {(String|Number)} rowKey - row key
     * @param {String} columnName - column name
     * @returns {Boolean} true if succeeded, false otherwise.
     */
    startEditing: function(rowKey, columnName) {
        if (this.get('editingAddress')) {
            return false;
        }

        if (_.isUndefined(rowKey) && _.isUndefined(columnName)) {
            rowKey = this.get('rowKey');
            columnName = this.get('columnName');
        } else if (!this.isCurrentCell(rowKey, columnName, true)) {
            return false;
        }

        rowKey = this.dataModel.getMainRowKey(rowKey, columnName);
        if (!this.dataModel.get(rowKey).isEditable(columnName)) {
            return false;
        }
        this.set('editingAddress', {
            rowKey: rowKey,
            columnName: columnName
        });

        return true;
    },

    /**
     * Finishes editing the current cell, and returns the result.
     * @returns {Boolean} - true if an editing cell exist, false otherwise.
     */
    finishEditing: function() {
        if (!this.get('editingAddress')) {
            return false;
        }

        this.set('editingAddress', null);

        return true;
    },

    /**
     * Returns whether the specified cell is exist
     * @param {String|Number} rowKey - Rowkey
     * @param {String} columnName - ColumnName
     * @returns {boolean} True if exist
     * @private
     */
    _isValidCell: function(rowKey, columnName) {
        var isValidRowKey = !util.isBlank(rowKey) && !!this.dataModel.get(rowKey);
        var isValidColumnName = !util.isBlank(columnName) && !!this.columnModel.getColumnModel(columnName);

        return isValidRowKey && isValidColumnName;
    },

    /**
     * ?????? focus ??? row ???????????? offset ?????? ????????? rowKey ??? ????????????.
     * @param {Number} offset   ????????? offset
     * @returns {?Number|String} rowKey   offset ?????? ????????? ????????? rowKey
     * @private
     */
    _findRowKey: function(offset) {
        var dataModel = this.dataModel;
        var rowKey = null;
        var index, row;

        if (this.has(true)) {
            index = Math.max(
                Math.min(
                    dataModel.indexOfRowKey(this.get('rowKey')) + offset,
                    this.dataModel.length - 1
                ), 0
            );
            row = dataModel.at(index);
            if (row) {
                rowKey = row.get('rowKey');
            }
        }

        return rowKey;
    },

    /**
     * ?????? focus ??? column ???????????? offset ?????? ????????? columnName ??? ????????????.
     * @param {Number} offset   ????????? offset
     * @returns {?String} columnName  offset ?????? ????????? ????????? columnName
     * @private
     */
    _findColumnName: function(offset) {
        var columnModel = this.columnModel;
        var columns = columnModel.getVisibleColumns();
        var columnIndex = columnModel.indexOfColumnName(this.get('columnName'), true);
        var columnName = null;
        var index;

        if (this.has(true)) {
            index = Math.max(Math.min(columnIndex + offset, columns.length - 1), 0);
            columnName = columns[index] && columns[index].name;
        }

        return columnName;
    },

    /**
     * Returns data of rowSpan
     * @param {Number|String} rowKey - Row key
     * @param {String} columnName - Column name
     * @returns {boolean|{count: number, isMainRow: boolean, mainRowKey: *}} rowSpanData - Data of rowSpan
     * @private
     */
    _getRowSpanData: function(rowKey, columnName) {
        if (rowKey && columnName) {
            return this.dataModel.get(rowKey).getRowSpanData(columnName);
        }

        return false;
    },

    /**
     * ?????? ????????? ???????????? ????????????.
     * @returns {Number} ?????? ????????? index
     */
    nextColumnIndex: function() {
        var columnName = this.nextColumnName();

        return this.columnModel.indexOfColumnName(columnName, true);
    },

    /**
     * ?????? ????????? ???????????? ????????????.
     * @returns {Number} ?????? ????????? ?????????
     */
    prevColumnIndex: function() {
        var columnName = this.prevColumnName();

        return this.columnModel.indexOfColumnName(columnName, true);
    },

    /**
     * keyEvent ?????? ??? ????????? ????????????,
     * rowSpan ?????? ?????? ????????? ?????? rowKey ??? ????????????.
     * @param {number}  offset ????????? offset
     * @returns {Number|String} offset ?????? ????????? ????????? rowKey
     */
    nextRowKey: function(offset) {
        var focused = this.which();
        var rowKey = focused.rowKey;
        var count, rowSpanData;

        offset = (typeof offset === 'number') ? offset : 1;

        if (offset > 1) {
            rowKey = this._findRowKey(offset);
            rowSpanData = this._getRowSpanData(rowKey, focused.columnName);

            if (rowSpanData && !rowSpanData.isMainRow) {
                rowKey = this._findRowKey(rowSpanData.count + offset);
            }
        } else {
            rowSpanData = this._getRowSpanData(rowKey, focused.columnName);

            if (rowSpanData.isMainRow && rowSpanData.count > 0) {
                rowKey = this._findRowKey(rowSpanData.count);
            } else if (rowSpanData && !rowSpanData.isMainRow) {
                count = rowSpanData.count;
                rowSpanData = this._getRowSpanData(rowSpanData.mainRowKey, focused.columnName);
                rowKey = this._findRowKey(rowSpanData.count + count);
            } else {
                offset = this.coordRowModel.getNextOffset(rowKey);
                rowKey = this._findRowKey(offset);
            }
        }

        return rowKey;
    },

    /**
     * keyEvent ?????? ??? ????????? ????????????,
     * rowSpan ?????? ?????? ????????? ?????? rowKey ??? ????????????.
     * @param {number}  offset ????????? offset
     * @returns {Number|String} offset ?????? ????????? ????????? rowKey
     */
    prevRowKey: function(offset) {
        var focused = this.which();
        var rowKey = focused.rowKey;
        var rowSpanData;

        offset = typeof offset === 'number' ? offset : 1;
        offset *= -1;

        if (offset < -1) {
            rowKey = this._findRowKey(offset);
            rowSpanData = this._getRowSpanData(rowKey, focused.columnName);

            if (rowSpanData && !rowSpanData.isMainRow) {
                rowKey = this._findRowKey(rowSpanData.count + offset);
            }
        } else {
            rowSpanData = this._getRowSpanData(rowKey, focused.columnName);

            if (rowSpanData && !rowSpanData.isMainRow) {
                rowKey = this._findRowKey(rowSpanData.count - 1);
            } else {
                offset = this.coordRowModel.getPreviousOffset(rowKey);
                rowKey = this._findRowKey(offset);
            }
        }

        return rowKey;
    },

    /**
     * keyEvent ?????? ??? ????????? ????????????, ?????? columnName ??? ????????????.
     * @returns {String} ?????? ?????????
     */
    nextColumnName: function() {
        return this._findColumnName(1);
    },

    /**
     * keyEvent ?????? ??? ????????? ????????????, ?????? columnName ??? ????????????.
     * @returns {String} ?????? ?????????
     */
    prevColumnName: function() {
        return this._findColumnName(-1);
    },

    /**
     * ????????? row ??? key ??? ????????????.
     * @returns {(string|number)} ????????? row ??? ??????
     */
    firstRowKey: function() {
        return this.dataModel.at(0).get('rowKey');
    },

    /**
     * ????????? row??? key ??? ????????????.
     * @returns {(string|number)} ????????? row ??? ??????
     */
    lastRowKey: function() {
        return this.dataModel.at(this.dataModel.length - 1).get('rowKey');
    },

    /**
     * ????????? columnName ??? ????????????.
     * @returns {string} ????????? ?????????
     */
    firstColumnName: function() {
        var columns = this.columnModel.getVisibleColumns();

        return columns[0].name;
    },

    /**
     * ????????? columnName ??? ????????????.
     * @returns {string} ????????? ?????????
     */
    lastColumnName: function() {
        var columns = this.columnModel.getVisibleColumns();
        var lastIndex = columns.length - 1;

        return columns[lastIndex].name;
    },

    /**
     * Returns the address of previous cell.
     * @returns {{rowKey: number, columnName: string}}
     */
    prevAddress: function() {
        var rowKey = this.get('rowKey');
        var columnName = this.get('columnName');
        var isFirstColumn = columnName === this.firstColumnName();
        var isFirstRow = rowKey === this.firstRowKey();
        var prevRowKey, prevColumnName;

        if (isFirstRow && isFirstColumn) {
            prevRowKey = rowKey;
            prevColumnName = columnName;
        } else if (isFirstColumn) {
            prevRowKey = this.prevRowKey();
            prevColumnName = this.lastColumnName();
        } else {
            prevRowKey = rowKey;
            prevColumnName = this.prevColumnName();
        }

        return {
            rowKey: prevRowKey,
            columnName: prevColumnName
        };
    },

    /**
     * Returns the address of next cell.
     * @returns {{rowKey: number, columnName: string}}
     */
    nextAddress: function() {
        var rowKey = this.get('rowKey');
        var columnName = this.get('columnName');
        var isLastColumn = columnName === this.lastColumnName();
        var isLastRow = rowKey === this.lastRowKey();
        var nextRowKey, nextColumnName;

        if (isLastRow && isLastColumn) {
            nextRowKey = rowKey;
            nextColumnName = columnName;
        } else if (isLastColumn) {
            nextRowKey = this.nextRowKey();
            nextColumnName = this.firstColumnName();
        } else {
            nextRowKey = rowKey;
            nextColumnName = this.nextColumnName();
        }

        return {
            rowKey: nextRowKey,
            columnName: nextColumnName
        };
    }
});

module.exports = Focus;
