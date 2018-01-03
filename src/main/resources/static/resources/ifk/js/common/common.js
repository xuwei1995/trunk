;(function($,window,document,undefined){
    var _web = function () {};

    $.fn.createDataTable = function (options) {
        var _self = this, _dataTable;
        var defaultOptions = {
            'allowCheck':false,
            'paging': true,//分页工具条显示
            'lengthChange': false,//每页显示的记录数
            'searching': false,//搜索栏
            'ordering': true,//是否支持排序功能
            'info': true,//显示表格信息
            'autoWidth': false,//自适应宽度
            // "scrollX": true,//水平新增滚动轴
            "processing" : true, //开启读取服务器数据时显示正在加载中……
            "serverSide" : true,//服务器处理分页，默认是false，需要服务器处理，必须true
            "ajaxDataProp" : "aData",//是服务器分页的标志，必须有
        };
        var getSelectRows = function () {
            var table = _self.dataTable();
            var nTrs = table.fnGetNodes();//fnGetNodes获取表格所有行，nTrs[i]表示第i行tr对象
            var checkbox, results = [];
            for(var i = 0; i < nTrs.length; i++){
                checkbox = $(nTrs[i]).find("input[type='checkbox']");
                if(checkbox && checkbox.is(":checked")){
                    results.push(table.fnGetData(nTrs[i]));
                }
            }
            return results;
        };
        var tableFinish = function () {
            //iCheck for checkbox and radio inputs
            var wrapper = _self.parents(".dataTables_wrapper");
            wrapper.find('input[type="checkbox"]').iCheck({
                checkboxClass: 'icheckbox_flat-blue',
                radioClass: 'iradio_flat-blue'
            });
            var check_all = wrapper.find("thead .checkbox-toggle input[type='checkbox']");
            check_all.iCheck("uncheck");
            //Enable check and uncheck all functionality
            check_all.on("ifChanged",function (event) {
                var clicks = $(this).data('clicks');
                if (clicks) {
                    //Uncheck all checkboxes
                    wrapper.find("tr input[type='checkbox']").iCheck("uncheck");
                } else {
                    //Check all checkboxes
                    wrapper.find("tr input[type='checkbox']").iCheck("check");
                }
                $(this).data("clicks", !clicks);
            });
        };
        // 行点击事件
        if(options.onRowClick){
            _self.on( 'click', 'tr', function () {
                options.onRowClick(_dataTable.row( this ).data());
            });
        }
        if(options.drawCallback){
            options.drawCallback = function (settings) {
                tableFinish();
                options.drawCallback(settings);
            }
        }else {
            options.drawCallback = tableFinish;
        }
        var _options = $.extend({}, defaultOptions, options);
        if(_options.allowCheck && _options.columns){
            _options.columns.unshift({
                "data": "checkbox", "bSearchable": false, "bSortable": false,
                "render":function () {
                    return '<input type="checkbox">';
                }
            });
            _self.find("tr").prepend('<th class="checkbox-toggle"><input type="checkbox"></th>');
        }
        _dataTable = this.DataTable(_options);
        $.extend(_dataTable, {
            getSelectRows:getSelectRows,
        });
        return _dataTable;
    };
})($,window,document);

/**
 * 画面加载完后触发事件
 */
$(function () {
    //绑定菜单工具项事件
     toolbar();
});
/**
 * 初始化dataTable
 * @constructor
 */
function InitOverviewDataTable(tableId,url,columns,params)
{
    var descTable =$(tableId).dataTable(
    {
        'paging': true,//分页工具条显示
        'lengthChange': false,//每页显示的记录数
        'searching': false,//搜索栏
        'ordering': true,//是否支持排序功能
        'info': true,//显示表格信息
        'autoWidth': false,//自适应宽度
        "scrollX": true,//水平新增滚动轴
        "scrollCollapse": true,
        "processing" : true, //开启读取服务器数据时显示正在加载中……
        "serverSide" : true,//服务器处理分页，默认是false，需要服务器处理，必须true
        "ajax" : {
            "url":ctx+"/getAllOrganization",//通过ajax实现分页的url路径。
            "data": params,
             "dataSrc" : "aData",//是服务器分页的标志，必须有
            },
        "columns":columns
    });
    return descTable;
}

/**
 * 给工具栏工具项绑定事件
 */
function toolbar() {
    //搜索项----显示或隐藏查询
    $(".searchItem").on('click', function() {
        $(".searchForm").toggle("display");
    });
    //查询项----查询数据，重载dataTable
    $(".queryItem").on('click', function() {
        searchData();
    });
    //新增项----显示新增画面
    $(".addItem").on('click', function() {
        switchTab2();
    });
    //修改项----显示修改画面
    $(".editItem").on('click', function() {
        switchTab3();
    });
}
/**
* 格式化时间(yyyy-MM-dd hh:mm:ss)
* @param date
* @returns {string}
*/
function formatDateStr(value) {
    if(value==null)
        return "";
    var t, y, m, d, h, i, s;
    t = new Date(value);
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
}
/*DataGrid 行背景的设置，根据单 / 双行号的设置不同背景颜色 */
function paint_grid_row(){
    var index=0;
    $(".datagrid-btable").each(function(i,item){
        var index=0;
        $(item).children().first().children().each(
            function(){
                index++;
                if((index%2)==0){
                    //$(this).css({"background-color":"#CCC"});
                    $(this).addClass("trBg");
                }
            }
        );
    });
}

/**
 * 重新加载dataTable
 */
function reloadTable(table,param) {
    table.settings()[0].ajax.data = param;
    table.ajax.reload();
}