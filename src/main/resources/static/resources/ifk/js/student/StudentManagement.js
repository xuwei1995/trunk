var $table , $table2,
    $stu_li_1, $stu_li_2, $stu_li_3, $stu_li_4, $stu_li_5,
    $tab_1, $tab_2, $tab_3, $tab_4, $tab_5;
$(function () {
    setAttribute();
    /*学生修改-->tab_3点击事件( 选取第三个标签页（从 0 开始索引）)*/
    $(".nav-tabs li:eq(2) a").on('click', function(e) {
        switchTab3();
    });
    /*学生批量充值-->tab_4点击事件( 选取第三个标签页（从 0 开始索引）)*/
    $(".nav-tabs li:eq(3) a").on('click', function(e) {
        batchRecharge();
    });
    /*充值历史-->tab_5点击事件( 选取第三个标签页（从 0 开始索引）)*/
    $(".nav-tabs li:eq(4) a").on('click', function(e) {
        lookHR();
    });
    /*学生管理-->给工具按钮绑定事件 查询区域显示/隐藏按钮*/
    /*$(".searchItem").on('click', function() {
        $(".searchForm").toggle("display");
    });*/
    /*充值历史-->给工具按钮绑定事件 查询区域显示/隐藏按钮*/
    $(".searchItem-CZH").on('click', function() {
        $(".searchForm-CZH").toggle("display");
    });
    //Date picker
    $('.datepicker').datepicker({
        autoclose: true
    });
    /*修改学生*/
    /*$(".editItem").on('click', function() {
        updateStudent();
    });*/
    /*批量充值*/
    $(".batchRechargeItem").on('click', function() {
        batchRecharge();
    });
    /*查看充值历史*/
    $(".lookHRItem").on('click', function() {
        lookHR();
    });
    initGrid();
});
function setAttribute(){
    $table = $('#dg_sub');
    $table2 =  $('#dg_sub2');
    $stu_li_1 = $(".stu-li-1");
    $stu_li_2 = $(".stu-li-2");
    $stu_li_3 = $(".stu-li-3");
    $stu_li_4 = $(".stu-li-4");
    $stu_li_5 = $(".stu-li-5");
    $tab_1 = $("#tab_1");
    $tab_2 = $("#tab_2");
    $tab_3 = $("#tab_3");
    $tab_4 = $("#tab_4");
    $tab_5 = $("#tab_5");
}
/**初始化界面中的列表数据 **/
function initGrid(){
    var searchParams = $(".searchForm").serializeJson();
    $table.createDataTable({
        "allowCheck":true,
        "ajax" : {
            url:ctx+"/getAllStudent",
            data: searchParams
        },//通过ajax实现分页的url路径。
        "columns": [{
                "data": "studentId", "bSearchable": false, "bSortable": false, "bVisible": false
            }, {
                "data": "ifkCode", "bSearchable": true, "bSortable": true
            }, {
                "data": "userName", "bSearchable": true, "bSortable": true
            }, {
                "data": "dan", "bSearchable": true, "bSortable": true
            }, {
                "data": "statusValue", "bSearchable": true, "bSortable": true
            }, {
                "data": "createDate", "bSearchable": true, "bSortable": true,
                "render": function (data) {
                    return formatDateStr(data);
                }
            }, {
                "data": "validDate", "bSearchable": true, "bSortable": true,
                "render": function (data) {
                    return formatDateStr(data);
                }
            }
        ],
        "onRowClick" : function (data) {
            if(data != null){
                $stu_li_3.removeClass("disabled");
                $stu_li_4.removeClass("disabled");
                $stu_li_5.removeClass("disabled");
            }
        }
    });
    /*$('#dg_sub').datagrid({
        singleSelect: false,
        pagination: true,
        pageSize: 20,
        url: ctx +"/getAllStudent",
        queryParams : {},
        columns:[
            [
                {field: 'studentId',checkbox: true, title:'',halign: 'center',width:fixWidth(0.05)},
                {field:'ifkCode',title:'账号',align:'left',halign: 'center', width:fixWidth(0.15)},
                {field:'userName',title:'姓名',align:'left',halign: 'center',width:fixWidth(0.15)},
                {field:'dan',title:'段位',align:'left',halign: 'center',width:fixWidth(0.15)},
                {field:'statusValue',title:'状态',align:'right',halign: 'center',width:fixWidth(0.15)},
                {field:'createDate',title:'创建时间',align:'right',halign: 'center',width:fixWidth(0.17),formatter:formatDateStr},
                {field:'validDate',title:'有效期',align:'right',halign: 'center',width:fixWidth(0.20),formatter:formatDateStr}
            ]],
        onLoadSuccess: function (data) {
            paint_grid_row();
            /!*if(data.total == 0) {
                jAlert("message",language.cMessages.data_nonentity,language.cMessages.alter);
            }*!/
        },
        onClickRow: function (rowIndex, rowData) {
            $('.stu-li-3').removeClass("disabled");
            $('.stu-li-4').removeClass("disabled");
            $('.stu-li-5').removeClass("disabled");
        }
    });*/
}
/*学生管理列表查询*/
function searchData(){
    var searchParams = $(".searchForm").serializeJson();
    var table = $table.DataTable();
    reloadTable(table, searchParams);
}
/**初始化界学生充值面中的列表数据 **/
function initGrid2(){
    $table2.createDataTable({
        "allowCheck":true,
        "ajax" : {
            // url:ctx+"/getAllStudent"
        },//通过ajax实现分页的url路径。
        "columns": [{
            "data": "studentId", "bSearchable": false, "bSortable": false, "bVisible": false
        }, {
            "data": "ifkCode", "bSearchable": true, "bSortable": true
        }, {
            "data": "userName", "bSearchable": true, "bSortable": true
        }, {
            "data": "dan", "bSearchable": true, "bSortable": true
        }, {
            "data": "statusValue", "bSearchable": true, "bSortable": true
        }, {
            "data": "createDate", "bSearchable": true, "bSortable": true,
            "render": function (data) {
                return formatDateStr(data);
            }
        }, {
            "data": "validDate", "bSearchable": true, "bSortable": true,
            "render": function (data) {
                return formatDateStr(data);
            }
        }, {
            "data": "cardNo", "bSearchable": true, "bSortable": true
        }
        ]
    });
    /*$('#dg_sub2').datagrid({
        singleSelect: false,
        pagination: true,
        pageSize: 20,
        url: ctx +"/getAllStudent",
        queryParams : {},
        columns:[
            [
                {field: 'studentId',checkbox: true, title:'',halign: 'center',width:fixWidth(0.05)},
                {field:'ifkCode',title:'账号',align:'left',halign: 'center', width:fixWidth(0.15)},
                {field:'userName',title:'姓名',align:'left',halign: 'center',width:fixWidth(0.15)},
                {field:'dan',title:'段位',align:'left',halign: 'center',width:fixWidth(0.10)},
                {field:'statusValue',title:'状态',align:'right',halign: 'center',width:fixWidth(0.10)},
                {field:'createDate',title:'创建时间',align:'right',halign: 'center',width:fixWidth(0.15),formatter:formatDateStr},
                {field:'validDate',title:'有效期',align:'right',halign: 'center',width:fixWidth(0.15),formatter:formatDateStr},
                {field:'cardNo',title:'充值卡号',align:'right',halign: 'center',width:fixWidth(0.17)}
            ]],
        onLoadSuccess: function (data) {
            paint_grid_row();
        },
        onClickRow: function (rowIndex, rowData) {

        }
    });*/
}
/*修改学生判断*/
function switchTab3(){
    var table = $table.createDataTable({"onRowClick" : function (data) {}});
    var rows = table.getSelectRows();
    if(rows != null && rows.length == 1){
        $(".stu-a-3").css("data-toggle", "tab");
        removeClass();
        $stu_li_3.addClass('active');
        $tab_3.addClass('active');
    }else{
        jAlert('message',Msg.common.line_information, Msg.common.alter);
    }
}
/*学生批量充值*/
function batchRecharge(){
    var table = $table.createDataTable({"onRowClick" : function (data) {}});
    var rows = table.getSelectRows();
    if(rows != null && rows.length > 0){
        $(".stu-a-3").css("data-toggle", "tab");
        removeClass();
        $stu_li_4.addClass('active');
        $tab_4.addClass('active');
        initGrid2();
    }else{
        jAlert('message',Msg.common.line_information, Msg.common.alter);
    }
}
/*查看学生充值历史*/
function lookHR(){
    var table = $table.createDataTable({"onRowClick" : function (data) {}});
    var rows = table.getSelectRows();
    if(rows != null && rows.length == 1){
        $(".stu-a-3").css("data-toggle", "tab");
        removeClass();
        $stu_li_5.addClass('active');
        $tab_5.addClass('active');
    }else{
        jAlert('message',Msg.common.line_information, Msg.common.alter);
    }
}
/*控制tap事件*/
function removeClass(){
    $stu_li_1.removeClass('active');
    $tab_1.removeClass('active');
    $stu_li_2.removeClass('active');
    $tab_2.removeClass('active');
    $stu_li_3.removeClass('active');
    $tab_3.removeClass('active');
    $stu_li_4.removeClass('active');
    $tab_4.removeClass('active');
    $stu_li_5.removeClass('active');
    $tab_5.removeClass('active');
}