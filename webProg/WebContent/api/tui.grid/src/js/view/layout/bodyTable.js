/**
 * @fileoverview Class for the table layout in the body(data) area
 * @author NHN. FE Development Lab <dl_javascript@nhn.com>
 */

'use strict';

var _ = require('underscore');

var View = require('../../base/view');
var constMap = require('../../common/constMap');
var classNameConst = require('../../common/classNameConst');

var CELL_BORDER_WIDTH = constMap.dimension.CELL_BORDER_WIDTH;
var ATTR_COLUMN_NAME = constMap.attrName.COLUMN_NAME;

/**
 * Class for the table layout in the body(data) area
 * @module view/layout/bodyTable
 * @extends module:base/view
 * @param {Object} options - Options
 * @param {String} [options.whichSide='R'] L or R (which side)
 * @ignore
 */
var BodyTable = View.extend(/** @lends module:view/layout/bodyTable.prototype */{
    initialize: function(options) {
        View.prototype.initialize.call(this);

        _.assign(this, {
            dimensionModel: options.dimensionModel,
            coordColumnModel: options.coordColumnModel,
            renderModel: options.renderModel,
            columnModel: options.columnModel,
            viewFactory: options.viewFactory,
            painterManager: options.painterManager,
            whichSide: options.whichSide || 'R'
        });

        this.listenTo(this.coordColumnModel, 'columnWidthChanged', this._onColumnWidthChanged);

        // To prevent issue of appearing vertical scrollbar when dummy rows exist
        this.listenTo(this.renderModel, 'change:dummyRowCount', this._onChangeDummyRowCount);
        this.listenTo(this.dimensionModel, 'change:bodyHeight', this._resetHeight);

        this._attachAllTableEventHandlers();
    },

    className: classNameConst.BODY_TABLE_CONTAINER,

    template: _.template(
        '<table class="' + classNameConst.TABLE + '">' +
        '   <colgroup><%=colGroup%></colgroup>' +
        '   <tbody><%=tbody%></tbody>' +
        '</table>'),

    templateCol: _.template(
        '<col <%=attrColumnName%>="<%=columnName%>" style="width:<%=width%>px">'
    ),

    /**
     * Event handler for 'columnWidthChanged' event on a dimension model.
     * @private
     */
    _onColumnWidthChanged: function() {
        var columnWidths = this.coordColumnModel.getWidths(this.whichSide);
        var $colList = this.$el.find('col');

        _.each(columnWidths, function(width, index) {
            $colList.eq(index).css('width', width + CELL_BORDER_WIDTH);
        }, this);
    },

    /**
     * Event handler for 'change:dummyRowCount' event on the renderModel.
     * @private
     */
    _onChangeDummyRowCount: function() {
        this._resetOverflow();
        this._resetHeight();
    },

    /**
     * Resets the overflow of element based on the dummyRowCount in renderModel.
     * @private
     */
    _resetOverflow: function() {
        var overflow = 'visible';

        if (this.renderModel.get('dummyRowCount') > 0) {
            overflow = 'hidden';
        }
        this.$el.css('overflow', overflow);
    },

    /**
     * Resets the height of element based on the dummyRowCount in renderModel
     * @private
     */
    _resetHeight: function() {
        var dimensionModel = this.dimensionModel;

        if (this.renderModel.get('dummyRowCount') > 0) {
            this.$el.height(dimensionModel.get('bodyHeight') - dimensionModel.getScrollXHeight());
        } else {
            this.$el.css('height', '');
        }
    },

    /**
     * Reset position of a table container
     * @param {number} top  ????????? top ?????? ???
     */
    resetTablePosition: function() {
        this.$el.css('top', this.renderModel.get('top'));
    },

    /**
     * Renders elements
     * @returns {View.Layout.Body} This object
     */
    render: function() {
        this._destroyChildren();

        this.$el.html(this.template({
            colGroup: this._getColGroupMarkup(),
            tbody: ''
        }));

        this._addChildren(this.viewFactory.createRowList({
            bodyTableView: this,
            el: this.$el.find('tbody'),
            whichSide: this.whichSide
        }));
        this._renderChildren();

        // To prevent issue of appearing vertical scrollbar when dummy rows exists
        this._resetHeight();
        this._resetOverflow();

        return this;
    },

    /**
     * ????????? ??????(TR,TD)?????? ???????????? ???????????? this.el??? ?????? ?????? ??????????????? ??????????????? ????????????.
     * @private
     */
    _attachAllTableEventHandlers: function() {
        var cellPainters = this.painterManager.getCellPainters();

        _.each(cellPainters, function(painter) {
            painter.attachEventHandlers(this.$el, '');
        }, this);
    },

    /**
     * table ????????? ?????? ????????????.
     * (IE8-9?????? tbody??? innerHTML ????????? ??? ?????? ????????? ???????????? ??????????????? ?????? ?????? ??????)
     * @param {string} tbodyHtml - tbody??? innerHTML ?????????
     * @returns {jquery} - ?????? ????????? table??? tbody ??????
     */
    redrawTable: function(tbodyHtml) {
        this.$el[0].innerHTML = this.template({
            colGroup: this._getColGroupMarkup(),
            tbody: tbodyHtml
        });

        return this.$el.find('tbody');
    },

    /**
     * Table ??? ????????? width ????????? ?????? columnGroup ???????????? ????????????.
     * @returns {string} <colgroup> ?????? ????????? ????????? ?????????
     * @private
     */
    _getColGroupMarkup: function() {
        var whichSide = this.whichSide;
        var columnWidths = this.coordColumnModel.getWidths(whichSide);
        var columns = this.columnModel.getVisibleColumns(whichSide, true);
        var html = '';

        _.each(columns, function(column, index) {
            html += this.templateCol({
                attrColumnName: ATTR_COLUMN_NAME,
                columnName: column.name,
                width: columnWidths[index] + CELL_BORDER_WIDTH
            });
        }, this);

        return html;
    }
});

module.exports = BodyTable;
