/**
 * @fileoverview Manage coordinates of rows
 * @author NHN. FE Development Lab <dl_javascript@nhn.com>
 */

'use strict';

var _ = require('underscore');
var snippet = require('tui-code-snippet');

var Model = require('../base/model');
var util = require('../common/util');
var constMap = require('../common/constMap');
var dimensionConst = constMap.dimension;
var frameConst = constMap.frame;

var CELL_BORDER_WIDTH = dimensionConst.CELL_BORDER_WIDTH;

/**
 * @module model/coordColumn
 * @param {Object} attrs - Attributes
 * @param {Object} options - Options
 * @extends module:base/model
 * @ignore
 */
var CoordColumn = Model.extend(/** @lends module:model/coordColumn.prototype */{
    initialize: function(attrs, options) {
        this.dimensionModel = options.dimensionModel;
        this.columnModel = options.columnModel;

        /**
         * An array of the fixed flags of the columns
         * @private
         * @type {boolean[]}
         */
        this._fixedWidthFlags = null;

        /**
         * An array of the minimum width of the columns
         * @private
         * @type {number[]}
         */
        this._minWidths = null;

        /**
         * Whether the column width is modified by user.
         * @type {boolean}
         */
        this._isModified = false;

        this.listenTo(this.columnModel, 'columnModelChange', this.resetColumnWidths);
        this.listenTo(this.dimensionModel, 'change:width', this._onDimensionWidthChange);

        if (options.domEventBus) {
            this.listenTo(options.domEventBus, 'dragmove:resizeColumn', this._onDragResize);
            this.listenTo(options.domEventBus, 'dblclick:resizeColumn', this._onDblClick);
        }
        this.resetColumnWidths();
    },

    defaults: {
        widths: [],
        resizable: true
    },

    /**
     * Reset the width of each column by using initial setting of column models.
     */
    resetColumnWidths: function() {
        var columns = this.columnModel.getVisibleColumns(null, true);
        var commonMinWidth = this.dimensionModel.get('minimumColumnWidth');
        var widths = [];
        var fixedFlags = [];
        var minWidths = [];

        _.each(columns, function(columnModel) {
            var columnWidth = columnModel.width || 'auto';
            var fixedWidth = !isNaN(columnWidth);
            var width, minWidth;

            // Meta columns are not affected by common 'minimumColumnWidth' value
            if (util.isMetaColumn(columnModel.name)) {
                minWidth = width;
            } else {
                minWidth = columnModel.minWidth || commonMinWidth;
            }

            width = fixedWidth ? columnWidth : minWidth;

            if (width < minWidth) {
                width = minWidth;
            }

            // If the width is not assigned (in other words, the width is not positive number),
            // set it to zero (no need to worry about minimum width at this point)
            // so that #_fillEmptyWidth() can detect which one is empty.
            // After then, minimum width will be applied by #_applyMinimumWidth().
            widths.push(width);
            minWidths.push(minWidth);
            fixedFlags.push(fixedWidth);
        }, this);

        this._fixedWidthFlags = fixedFlags;
        this._minWidths = minWidths;

        this._setColumnWidthVariables(this._calculateColumnWidth(widths), true);
    },

    /**
     * Event handler for dragmove event on domEventBus
     * @param {module:event/gridEvent} ev - GridEvent
     * @private
     */
    _onDragResize: function(ev) {
        this.setColumnWidth(ev.columnIndex, ev.width);
    },

    /**
     * Event handler for dblclick event on domEventBus
     * @param {module:event/gridEventd} ev - GridEvent
     * @private
     */
    _onDblClick: function(ev) {
        this.restoreColumnWidth(ev.columnIndex);
    },

    /**
     * widths ??? ??????, lside ??? rside ??? ?????? ????????? ???????????? ????????????.
     * @param {array} widths - ?????? ????????? ??????
     * @param {boolean} [saveWidths] - ?????? ??????. true?????? ????????? ????????? originalWidths??? ????????????.
     * @private
     */
    _setColumnWidthVariables: function(widths, saveWidths) {
        var totalWidth = this.dimensionModel.get('width');
        var frozenBorderWidth = this.dimensionModel.get('frozenBorderWidth');
        var maxLeftSideWidth = this.dimensionModel.getMaxLeftSideWidth();
        var frozenCount = this.columnModel.getVisibleFrozenCount(true);
        var rsideWidth, lsideWidth, lsideWidths, rsideWidths;

        lsideWidths = widths.slice(0, frozenCount);
        rsideWidths = widths.slice(frozenCount);

        lsideWidth = this._getFrameWidth(lsideWidths);
        if (maxLeftSideWidth && maxLeftSideWidth < lsideWidth) {
            lsideWidths = this._adjustLeftSideWidths(lsideWidths, maxLeftSideWidth);
            lsideWidth = this._getFrameWidth(lsideWidths);
            widths = lsideWidths.concat(rsideWidths);
        }
        rsideWidth = totalWidth - lsideWidth;

        this.set({
            widths: widths
        });
        this.dimensionModel.set({
            rsideWidth: rsideWidth,
            lsideWidth: lsideWidth - frozenBorderWidth
        });

        if (saveWidths) {
            this.set('originalWidths', _.clone(widths));
        }
        this.trigger('columnWidthChanged');
    },

    /**
     * columnFrozenCount ??? ??????????????? ???, window resize ??? left side ??? ????????? ????????????.
     * @param {Array} lsideWidths    ????????? ????????? ?????? ????????? ??????
     * @param {Number} totalWidth   grid ?????? ??????
     * @returns {Array} ????????? ????????? ?????? ?????????
     * @private
     */
    _adjustLeftSideWidths: function(lsideWidths, totalWidth) {
        var i = lsideWidths.length - 1;
        var minimumColumnWidth = this.dimensionModel.get('minimumColumnWidth');
        var currentWidth = this._getFrameWidth(lsideWidths);
        var diff = currentWidth - totalWidth;
        var changedWidth;

        if (diff > 0) {
            while (i >= 0 && diff > 0) {
                changedWidth = Math.max(minimumColumnWidth, lsideWidths[i] - diff);
                diff -= lsideWidths[i] - changedWidth;
                lsideWidths[i] = changedWidth;
                i -= 1;
            }
        } else if (diff < 0) {
            lsideWidths[i] += Math.abs(diff);
        }

        return lsideWidths;
    },

    /**
     * calculate column width list
     * @param {Array.<Number>} widths - widths
     * @returns {Array.<Number>}
     * @private
     */
    _calculateColumnWidth: function(widths) {
        widths = this._fillEmptyWidth(widths);
        widths = this._applyMinimumWidth(widths);
        widths = this._adjustWidths(widths);

        return widths;
    },

    /**
     * Sets the width of columns whose width is not assigned by distributing extra width to them equally.
     * @param {number[]} widths - An array of column widths
     * @returns {number[]} - A new array of column widths
     * @private
     */
    _fillEmptyWidth: function(widths) {
        var totalWidth = this.dimensionModel.getAvailableTotalWidth(widths.length);
        var remainTotalWidth = totalWidth - util.sum(widths);
        var emptyIndexes = [];

        _.each(widths, function(width, index) {
            if (!width) {
                emptyIndexes.push(index);
            }
        });

        return this._distributeExtraWidthEqually(widths, remainTotalWidth, emptyIndexes);
    },

    /**
     * widths ????????? ?????? ?????? ???????????? ????????? frameWidth ??? ?????????.
     * @param {Array} widths ?????? ????????? ??????
     * @returns {Number} ????????? frame ?????????
     * @private
     */
    _getFrameWidth: function(widths) {
        var frameWidth = 0;

        if (widths.length) {
            frameWidth = util.sum(widths) + ((widths.length + 1) * CELL_BORDER_WIDTH);
        }

        return frameWidth;
    },

    /**
     * Adds extra widths of the column equally.
     * @param {number[]} widths - An array of column widths
     * @param {number} totalExtraWidth - Total extra width
     * @returns {number[]} - A new array of column widths
     * @private
     */
    _addExtraColumnWidth: function(widths, totalExtraWidth) {
        var fixedFlags = this._fixedWidthFlags;
        var columnIndexes = [];

        _.each(fixedFlags, function(flag, index) {
            if (!flag) {
                columnIndexes.push(index);
            }
        });

        return this._distributeExtraWidthEqually(widths, totalExtraWidth, columnIndexes);
    },

    /**
     * Reduces excess widths of the column equally.
     * @param {number[]} widths - An array of column widths
     * @param {number} totalExcessWidth - Total excess width (negative number)
     * @returns {number[]} - A new array of column widths
     * @private
     */
    _reduceExcessColumnWidth: function(widths, totalExcessWidth) {
        var minWidths = this._minWidths;
        var fixedFlags = this._fixedWidthFlags;
        var availableList = [];

        _.each(widths, function(width, index) {
            if (!fixedFlags[index]) {
                availableList.push({
                    index: index,
                    width: width - minWidths[index]
                });
            }
        });

        return this._reduceExcessColumnWidthSub(_.clone(widths), totalExcessWidth, availableList);
    },

    /**
     * Reduce the (remaining) excess widths of the column.
     * This method will be called recursively by _reduceExcessColumnWidth.
     * @param {number[]} widths - An array of column Width
     * @param {number} totalRemainWidth - Remaining excess width (negative number)
     * @param {object[]} availableList - An array of infos about available column.
     *                                 Each item of the array has {index:number, width:number}.
     * @returns {number[]} - A new array of column widths
     * @private
     */
    _reduceExcessColumnWidthSub: function(widths, totalRemainWidth, availableList) {
        var avgValue = Math.round(totalRemainWidth / availableList.length);
        var newAvailableList = [];
        var columnIndexes;

        _.each(availableList, function(available) {
            // note that totalRemainWidth and avgValue are negative number.
            if (available.width < Math.abs(avgValue)) {
                totalRemainWidth += available.width;
                widths[available.index] -= available.width;
            } else {
                newAvailableList.push(available);
            }
        });
        // call recursively until all available width are less than average
        if (availableList.length > newAvailableList.length) {
            return this._reduceExcessColumnWidthSub(widths, totalRemainWidth, newAvailableList);
        }
        columnIndexes = _.pluck(availableList, 'index');

        return this._distributeExtraWidthEqually(widths, totalRemainWidth, columnIndexes);
    },

    /**
     * Distributes the extra width equally to each column at specified indexes.
     * @param {number[]} widths - An array of column width
     * @param {number} extraWidth - Extra width
     * @param {number[]} columnIndexes - An array of indexes of target columns
     * @returns {number[]} - A new array of column widths
     * @private
     */
    _distributeExtraWidthEqually: function(widths, extraWidth, columnIndexes) {
        var length = columnIndexes.length;
        var avgValue = Math.round(extraWidth / length);
        var errorValue = (avgValue * length) - extraWidth; // to correct total width
        var resultList = _.clone(widths);

        _.each(columnIndexes, function(columnIndex) {
            resultList[columnIndex] += avgValue;
        });

        if (columnIndexes.length) {
            resultList[_.last(columnIndexes)] -= errorValue;
        }

        return resultList;
    },

    /**
     * Makes all width of columns not less than minimumColumnWidth.
     * @param {number[]} widths - ?????? ????????? ??????
     * @returns {number[]} - ????????? ????????? ????????? ??????
     * @private
     */
    _applyMinimumWidth: function(widths) {
        var minWidths = this._minWidths;
        var appliedList = _.clone(widths);

        _.each(appliedList, function(width, index) {
            var minWidth = minWidths[index];
            if (width < minWidth) {
                appliedList[index] = minWidth;
            }
        });

        return appliedList;
    },

    /**
     * Adjust the column widths to make them fit into the dimension.
     * @param {number[]} widths - An array of column width
     * @param {boolean} [fitToReducedTotal] - If set to true and the total width is smaller than dimension(width),
     *                                    the column widths will be reduced.
     * @returns {number[]} - A new array of column widths
     * @private
     */
    _adjustWidths: function(widths, fitToReducedTotal) {
        var columnLength = widths.length;
        var availableWidth = this.dimensionModel.getAvailableTotalWidth(columnLength);
        var totalExtraWidth = availableWidth - util.sum(widths);
        var fixedCount = _.filter(this._fixedWidthFlags).length;
        var adjustedWidths;

        if (totalExtraWidth > 0 && (columnLength > fixedCount)) {
            adjustedWidths = this._addExtraColumnWidth(widths, totalExtraWidth);
        } else if (fitToReducedTotal && totalExtraWidth < 0) {
            adjustedWidths = this._reduceExcessColumnWidth(widths, totalExtraWidth);
        } else {
            adjustedWidths = widths;
        }

        return adjustedWidths;
    },

    /**
     * width ??? ????????? ??? column ??? ????????? ????????????.
     * @private
     */
    _onDimensionWidthChange: function() {
        var widths = this.get('widths');

        if (!this._isModified) {
            widths = this._adjustWidths(widths, true);
        }
        this._setColumnWidthVariables(widths);
    },

    /**
     * L side ??? R side ??? ?????? widths ??? ????????????.
     * @param {String} [whichSide] ?????? ???????????? ??????. L,R ??? ????????? ????????? ?????????. ????????? ?????? columnList ??????
     * @returns {Array}  ????????? ????????? widths
     */
    getWidths: function(whichSide) {
        var columnFrozenCount = this.columnModel.getVisibleFrozenCount(true);
        var widths = [];

        switch (whichSide) {
            case frameConst.L:
                widths = this.get('widths').slice(0, columnFrozenCount);
                break;
            case frameConst.R:
                widths = this.get('widths').slice(columnFrozenCount);
                break;
            default:
                widths = this.get('widths');
                break;
        }

        return widths;
    },

    /**
     * L, R ??? ????????? ???????????? frame ??? ????????? ?????????.
     * @param {String} [whichSide]  ???????????? ?????? ?????? ?????? ??????.
     * @returns {Number} ?????? frame ??? ??????
     */
    getFrameWidth: function(whichSide) {
        var columnFrozenCount = this.columnModel.getVisibleFrozenCount(true);
        var widths = this.getWidths(whichSide);
        var frameWidth = this._getFrameWidth(widths);

        if (_.isUndefined(whichSide) && columnFrozenCount > 0) {
            frameWidth += CELL_BORDER_WIDTH;
        }

        return frameWidth;
    },

    /**
     * columnResize ?????? ??? index ??? ???????????? ????????? width ??? ???????????? ????????????.
     * @param {Number} index    ????????? ????????? ????????? ?????????
     * @param {Number} width    ????????? ?????? pixel???
     */
    setColumnWidth: function(index, width) {
        var widths = this.get('widths');
        var minWidth = this._minWidths[index];

        if (widths[index]) {
            widths[index] = Math.max(width, minWidth);
            this._setColumnWidthVariables(widths);
            this._isModified = true;
        }
    },

    /**
     * Returns column index from X-position relative to the body-area
     * @param {number} posX - X-position relative to the body-area
     * @param {boolean} withMeta - Whether the meta columns go with this calculation
     * @returns {number} Column index
     * @private
     */
    indexOf: function(posX, withMeta) {
        var widths = this.getWidths();
        var totalColumnWidth = this.getFrameWidth();
        var adjustableIndex = (withMeta) ? 0 : this.columnModel.getVisibleMetaColumnCount();
        var columnIndex = 0;

        if (posX >= totalColumnWidth) {
            columnIndex = widths.length - 1;
        } else {
            snippet.forEachArray(widths, function(width, index) { // eslint-disable-line consistent-return
                width += CELL_BORDER_WIDTH;
                columnIndex = index;

                if (posX > width) {
                    posX -= width;
                } else {
                    return false;
                }
            });
        }

        return Math.max(0, columnIndex - adjustableIndex);
    },

    /**
     * Restore a column to the default width.
     * @param {Number} index - target column index
     */
    restoreColumnWidth: function(index) {
        var orgWidth = this.get('originalWidths')[index];

        this.setColumnWidth(index, orgWidth);
    }
});

module.exports = CoordColumn;
