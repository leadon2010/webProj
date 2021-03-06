'use strict';

var _ = require('underscore');
var $ = require('jquery');

var ModelManager = require('model/manager');
var RowListModel = require('model/rowList');
var RowListView = require('view/rowList');
var PainterManager = require('painter/manager');
var DomState = require('domState');
var SmartRenderModel = require('model/renderer-smart');
var frameConst = require('common/constMap').frame;

describe('View.RowList', function() {
    var grid, rowListView, $container, $tableContainer;

    function redrawTable(html) {
        $tableContainer[0].innerHTML = '<table><tbody>' + html + '</tbody></table>';

        return $tableContainer.find('tbody');
    }

    function createRowListView(whichSide) {
        return new RowListView({
            whichSide: whichSide,
            el: setFixtures('<table><tbody></tbody></table>').find('tbody'),
            renderModel: grid.renderModel,
            focusModel: grid.focusModel,
            dataModel: grid.dataModel,
            columnModel: grid.columnModel,
            selectionModel: grid.selectionModel,
            painterManager: new PainterManager({
                modelManager: grid
            }),
            bodyTableView: {
                resetTablePosition: function() {},
                attachTableEventHandler: function() {},
                redrawTable: redrawTable
            }
        });
    }

    beforeEach(function() {
        $container = setFixtures('<div />');
        grid = new ModelManager(null, new DomState($container));
        grid.columnModel.set('columns', [{
            columnName: 'c1',
            editOption: {
                type: 'normal'
            }
        }, {
            columnName: 'c2',
            editOption: {
                type: 'text'
            }
        }, {
            columnName: 'c3',
            editOption: {
                type: 'select',
                list: [{
                    text: 's1',
                    value: 's1'
                }, {
                    text: 's2',
                    vlaue: 's2'
                }]
            }
        }]);

        grid.dataModel.set([
            {
                c1: '0-1',
                c2: '0-2',
                c3: 's1'
            }, {
                c1: '1-1',
                c2: '1-2',
                c3: 's2'
            }
        ], {parse: true});

        grid.renderModel.refresh();
        $tableContainer = $('<div/>').appendTo($container);
        redrawTable('');

        rowListView = createRowListView(frameConst.L);
        rowListView.render();
    });

    describe('View.Painter.Row', function() {
        var rowPainter;

        beforeEach(function() {
            rowPainter = rowListView.painterManager.getRowPainter();
        });

        describe('_getEditType', function() {
            it('_number ??? ?????? isEditable ??? ???????????? ????????? _number ??? ????????????.', function() {
                expect(rowPainter._getEditType('_number', {isEditable: false})).toEqual('_number');
                expect(rowPainter._getEditType('_number', {isEditable: true})).toEqual('_number');
            });

            it('isEditable??? false ?????? _number??? ?????? ?????? ????????? normal??? ????????????.', function() {
                expect(rowPainter._getEditType('c1', {isEditable: false})).toEqual('normal');
                expect(rowPainter._getEditType('c2', {isEditable: false})).toEqual('normal');
                expect(rowPainter._getEditType('c3', {isEditable: false})).toEqual('normal');
            });

            it('??? ?????? ?????? ????????? editType ??? ????????????.', function() {
                expect(rowPainter._getEditType('c1', {isEditable: true})).toEqual('normal');
                expect(rowPainter._getEditType('c2', {isEditable: true})).toEqual('text');
                expect(rowPainter._getEditType('c3', {isEditable: true})).toEqual('select');
            });
        });
    });

    describe('SmartRender ????????? RowList.render()', function() {
        var $trs;

        function init(sampleData) {
            grid.renderModel = new SmartRenderModel(null, {
                columnModel: grid.columnModel,
                dataModel: grid.dataModel,
                dimensionModel: grid.dimensionModel
            });
            grid.dataModel.lastRowKey = -1;
            grid.dataModel.reset(sampleData, {parse: true});
        }

        function setCollectionRange(from, to) {
            var collection;

            grid.renderModel.refresh();
            collection = grid.renderModel.getCollection(frameConst.L);
            rowListView.collection = new RowListModel(collection.slice(from, to), {
                dataModel: grid.dataModel,
                columnModel: grid.columnModel
            });
            rowListView.render();
            $trs = rowListView.$el.children('tr');
        }

        describe('rowKey ????????? ???????????? ?????? ???', function() {
            var sampleData = (function() {
                var i, data = [];
                for (i = 0; i < 50; i += 1) {
                    data.push({c1: i});
                }

                return data;
            })();

            beforeEach(function() {
                grid.columnModel.set('columns', [{columnName: 'c1'}]);
                init(sampleData);
            });

            it('?????? ????????? ??????', function() {
                setCollectionRange(0, 10);

                expect($trs.length).toBe(10);
                expect($trs.first().attr('key')).toBe('0');
                expect($trs.last().attr('key')).toBe('9');
            });

            it('first??? last ?????? ????????? ??? (????????? ?????? ???)', function() {
                setCollectionRange(0, 10);
                setCollectionRange(5, 16);

                expect($trs.length).toBe(11);
                expect($trs.first().attr('key')).toBe('5');
                expect($trs.last().attr('key')).toBe('15');
            });

            it('first??? last ?????? ????????? ??? (????????? ?????? ???)', function() {
                setCollectionRange(20, 30);
                setCollectionRange(15, 22);

                expect($trs.length).toBe(7);
                expect($trs.first().attr('key')).toBe('15');
                expect($trs.last().attr('key')).toBe('21');
            });

            it('first??? ???????????? last??? ????????? ???(???????????? ????????? ???)', function() {
                setCollectionRange(0, 10);
                setCollectionRange(0, 20);

                expect($trs.length).toBe(20);
                expect($trs.first().attr('key')).toBe('0');
                expect($trs.last().attr('key')).toBe('19');
            });

            it('first??? ???????????? last??? ????????? ???(???????????? ????????? ???)', function() {
                setCollectionRange(10, 20);
                setCollectionRange(10, 15);

                expect($trs.length).toBe(5);
                expect($trs.first().attr('key')).toBe('10');
                expect($trs.last().attr('key')).toBe('14');
            });
        });

        describe('keyColumnName??? ???????????? ?????? ???????????? ???????????? ?????? ???', function() {
            var sampleData = (function() {
                var data = [];

                _.each([1, 3, 5, 6, 9, 10, 12, 13, 18, 20], function(num) {
                    data.push({c1: num});
                });

                return data;
            })();

            beforeEach(function() {
                grid.columnModel.set('columns', [{columnName: 'c1'}]);
                grid.columnModel.set('keyColumnName', 'c1');
                init(sampleData);
            });

            it('?????? ????????? ??????', function() {
                setCollectionRange(0, 5);

                expect($trs.length).toBe(5);
                expect($trs.first().attr('key')).toBe('1');
                expect($trs.last().attr('key')).toBe('9');
            });

            it('first??? last ?????? ????????? ??? (????????? ?????? ???)', function() {
                setCollectionRange(0, 5);
                setCollectionRange(3, 9);

                expect($trs.length).toBe(6);
                expect($trs.first().attr('key')).toBe('6');
                expect($trs.last().attr('key')).toBe('18');
            });
        });

        describe('????????? ????????? ???', function() {
            var sampleData = (function() {
                var i, data = [];
                for (i = 0; i < 15; i += 1) {
                    data.push({
                        c1: i,
                        c2: 15 - i
                    });
                }

                return data;
            })();

            beforeEach(function() {
                grid.columnModel.set('columns', [
                    {columnName: 'c1'},
                    {columnName: 'c2'}
                ]);
                grid.columnModel.set('keyColumnName', 'c1');
                init(sampleData);
            });

            it('?????? ?????? ????????? ????????? ???', function() {
                setCollectionRange(0, 10);
                grid.dataModel.sortByField('c2', true);
                setCollectionRange(0, 10);

                expect($trs.length).toBe(10);
                expect($trs.first().attr('key')).toBe('14');
                expect($trs.last().attr('key')).toBe('5');
            });

            it('??????????????? ????????? ???', function() {
                grid.dataModel.sortByField('c2', true);
                setCollectionRange(0, 10);
                grid.dataModel.sortByField('c2', false);
                setCollectionRange(0, 10);

                expect($trs.length).toBe(10);
                expect($trs.first().attr('key')).toBe('0');
                expect($trs.last().attr('key')).toBe('9');
            });
        });
    });

    describe('RowList', function() {
        describe('_getRowElement', function() {
            it('?????? rendering??? ???????????? ???, rowKey??? ???????????? ??????????????? ????????????.', function() {
                expect(rowListView._getRowElement(0).length).toEqual(1);
                expect(rowListView._getRowElement(0).attr('key')).toEqual('0');
                expect(rowListView._getRowElement(1).length).toEqual(1);
                expect(rowListView._getRowElement(1).attr('key')).toEqual('1');
                expect(rowListView._getRowElement(2).length).toEqual(0);
            });
        });

        describe('_onFocus, _onBlur', function() {
            beforeEach(function() {
                $tableContainer.append(rowListView.$el);
            });

            it('rendering ??? ???????????? ??? ???????????? ??????????????? focus, blur ????????? ???????????? ????????????.', function() {
                var $firstCell = rowListView.$el.find('tr:first').find('td').eq(0);

                rowListView._onFocus(0, 'c1');
                expect($firstCell).toHaveClass('focused');

                rowListView._onBlur(0, 'c1');
                expect($firstCell).not.toHaveClass('focused');
            });
        });

        describe('render', function() {
            it('dataModel??? rowList??? ????????? ??????, ????????? ????????? ?????? rendering ??????.', function() {
                var trList = rowListView.$el.find('tr'),
                    tdList = rowListView.$el.find('td');

                expect(trList.length).toBe(2);
                expect(tdList.length).toBe(6);
            });
        });

        describe('if the instance is left-side', function() {
            var $trs;

            function isMetaCellsSelected(rowKey) {
                var $metaCells = $trs.filter('[data-row-key=' + rowKey + ']').find('td.meta_column');

                return $metaCells.not('.selected').length === 0;
            }

            beforeEach(function() {
                rowListView = createRowListView(frameConst.L);
                rowListView.render();
                $trs = rowListView.$el.find('tr');
            });

            describe('and focused row is set', function() {
                it('add "selected" class to the meta cells of focused row', function() {
                    rowListView.focusModel.set('rowKey', 0);

                    expect(isMetaCellsSelected(0)).toBe(true);
                });
            });

            describe('and focused row has changed', function() {
                it('reset "selected" class to the meta cells of focused row', function() {
                    rowListView.focusModel.set('rowKey', 0, {silent: true});
                    rowListView.focusModel.set('rowKey', 1);

                    expect(isMetaCellsSelected(0)).toBe(false);
                    expect(isMetaCellsSelected(1)).toBe(true);
                });
            });

            describe('and the row-range of selection has changed', function() {
                it('add "selected" class to the meta cells in the range', function() {
                    rowListView.selectionModel.set('range', {
                        row: [0, 1],
                        column: [0, 0]
                    });

                    expect(isMetaCellsSelected(0)).toBe(true);
                    expect(isMetaCellsSelected(1)).toBe(true);
                });

                it('remove "selected" class from the meta cells in the privious range', function() {
                    rowListView.selectionModel.set('range', {
                        row: [0, 1],
                        column: [0, 0]
                    });
                    rowListView.selectionModel.set('range', {
                        row: [1, 1],
                        column: [0, 0]
                    });
                    expect(isMetaCellsSelected(0)).toBe(false);
                });

                it('refresh "selected" class from the meta cells ', function() {
                    rowListView.focusModel.set('rowKey', 1);
                    rowListView.selectionModel.set('range', {
                        row: [0, 0],
                        column: [0, 0]
                    });
                    expect(isMetaCellsSelected(1)).toBe(false);
                });
            });
        });
    });
});
