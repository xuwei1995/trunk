$(function () {
    /*清除机构选择框中的值*/
    $("#clearORG").on('click',function () {
        $("#searchORG").css('display','block');
        $("#clearORG").css('display','none');
        $("#orgId").val("");
        $("#orgAbbrname").val("");
        $(".selectSCHDialog").attr("data-value","");
        $(".selectSCHDialog").attr("data-schname","");
    });
    /*清除学校选择框中的值*/
    $("#clearSCH").on('click',function () {
        $("#searchSCH").css('display','block');
        $("#clearSCH").css('display','none');
        $("#schoolId").val("");
        $("#schoolName").val("");
    });
    /*初始化加载机构列表*/
    $(".selectORGDialog").click( function(event){
        paramId=this.id;
        $(".selectORGDialogModal").modal("toggle");
        $(".orgAbbrNameORG").focus();
        $(".orgAbbrNameORG").val("");
        var searchParams = $(".searchORGForm").serializeJson(); /*//序列化成json 参数*/
        $('#dg_org').dataTable().fnDestroy();
        $('#dg_org').createDataTable({
            "allowCheck":true,
            "ajax" : {
                url:ctx+"/getAllOrganization",
                data: searchParams
            },//通过ajax实现分页的url路径。
            "columns": [{
                "data": "orgId", "bSearchable": false, "bSortable": false, "bVisible": false
            }, {
                "data": "orgName", "bSearchable": true, "bSortable": true
            }, {
                "data": "orgAbbrname", "bSearchable": true, "bSortable": true
            }, {
                "data": "orgAddress", "bSearchable": true, "bSortable": true
            }
            ]
        });
       /* $('#dg_org').datagrid({
            loadMsg: '数据加载中...',
            singleSelect: true,
            pagination: true,
            pageSize: 10,
            url: ctx + "/getAllOrganization2",
            queryParams : {
                params : JSON.stringify(searchParams),
            },
            columns: [[
                {field: 'orgId', checkbox: true, title: '', halign: 'center', width: fixWidth(0.05)},
                {field: 'orgName', title: '机构名称', align: 'left', halign: 'center', width: fixWidth(0.2)},
                {field: 'orgAbbrname', title: '机构简称', align: 'left', halign: 'center', width: fixWidth(0.11)},
                {field: 'orgAddress', title: '机构地址', align: 'left', halign: 'center', width: fixWidth(0.2)}
            ]],
            onLoadSuccess: function (data) {
                paint_grid_row();
            },
            onClickRow: function (rowIndex, rowData) {},
            onDblClickCell:function () {/!*双击给机构选择框赋值*!/
                var row = $('#dg_org').datagrid('getSelected');
                inputvalForChooseORG(paramId,row.orgAbbrname,row.orgId);/!*!//给点击控件赋值*!/
                $(".selectORGDialogModal").modal("hide");  /!*!//关闭弹窗*!/
            }
        })*/
    });
    /*初始化加载学校列表*/
    $(".selectSCHDialog").click( function(event){
        orgId = $(this).attr("data-value");
        var orgAbbrNameSCH = $(this).attr("data-schname");
        if(orgId == "" || orgId == 0 || orgId == undefined){
            jAlert("error",Msg.common.pleaseSelectORG, Msg.common.alter);
            return
        }
        paramId=this.id;
        $(".selectSCHDialogModal").modal("toggle");
        $(".schoolNameSCH").focus();
        $(".schoolNameSCH").val("");
        $(".orgAbbrNameSCH").val(orgAbbrNameSCH);
        $(".orgIdSCH").val(orgId);
        var searchParams = $(".searchSCHForm").serializeJson(); /*//序列化成json 参数*/
        $('#dg_sch').dataTable().fnDestroy();
        $('#dg_sch').createDataTable({
            "allowCheck":true,
            "ajax" : {
                url:ctx+"/getAllSchool",
                data: searchParams
            },//通过ajax实现分页的url路径。
            "columns": [{
                "data": "schoolId", "bSearchable": false, "bSortable": false, "bVisible": false
            }, {
                "data": "schoolName", "bSearchable": true, "bSortable": true
            }, {
                "data": "schoolContact", "bSearchable": true, "bSortable": true
            }, {
                "data": "schoolAddress", "bSearchable": true, "bSortable": true
            }
            ]
        });
        /*$('#dg_sch').datagrid({
            loadMsg: '数据加载中...',
            singleSelect: true,
            pagination: true,
            pageSize: 10,
            url: ctx + "/getAllSchool",
            queryParams : {
                params : JSON.stringify(searchParams),
            },
            columns: [[
                {field: 'schoolId', checkbox: true, title: '', halign: 'center', width: fixWidth(0.05)},
                {field: 'schoolName', title: '学校名称', align: 'left', halign: 'center', width: fixWidth(0.2)},
                {field: 'schoolContact', title: '学校联系人', align: 'left', halign: 'center', width: fixWidth(0.11)},
                {field: 'schoolAddress', title: '学校地址', align: 'left', halign: 'center', width: fixWidth(0.2)}
            ]],
            onLoadSuccess: function (data) {
                paint_grid_row();
            },
            onClickRow: function (rowIndex, rowData) {},
            onDblClickCell:function () {/!*双击给机构选择框赋值*!/
                var row = $('#dg_sch').datagrid('getSelected');
                inputvalForChooseSCH(paramId,row.schoolName,row.schoolId);/!*!//给点击控件赋值*!/
                $(".selectSCHDialogModal").modal("hide"); /!* //关闭弹窗*!/
            }
        });*/
    });
    /*机构弹框查询文本框回车事件*/
    $(".searchORGForm").keydown(function (e) {
        if (e.keyCode == 13) {
            searchORGData();
        }
    });
    /*学校弹框查询文本框回车事件*/
    $(".searchSCHForm").keydown(function (e) {
        if (e.keyCode == 13) {
            searchSCHData();
        }
    });
});
var paramId;
var orgId;
/** 机构数据查询 **/
function searchORGData() {
    var searchParams = $(".searchORGForm").serializeJson(); //序列化成json 参数
    var table = $('#dg_org').DataTable();
    reloadTable(table, searchParams);
}
/*机构--点击确定按钮--给学校选择框赋值*/
function resultORGForInput(){
    var table = $('#dg_org').createDataTable({"onRowClick" : function (data) {}});
    var rows = table.getSelectRows(); /*!//拿到选中项*/
    if (rows != null && rows.length == 1) {
        inputvalForChooseORG(paramId,rows[0].orgAbbrname,rows[0].orgId);/*!//给点击控件赋值*/
        $(".selectORGDialogModal").modal("hide");  /*!//关闭弹窗*/
    } else {
        jAlert('message',Msg.common.line_information, Msg.common.alter); /*!//没有选中行 提示错误*/
        return;
    }
}
/*给机构选择框赋值*/
function inputvalForChooseORG(inputId,rowNmae,rowId) {
    $("#"+inputId).val(rowNmae)
    $("#orgId").val(rowId);
    if($("#"+paramId).val()!=null) {
        /*监听机构有值以后右侧换成X图标*/
        $("#searchORG").css('display','none');
        $("#clearORG").css('display','block');
        $(".selectSCHDialog").attr("data-value",rowId);
        $(".selectSCHDialog").attr("data-schname",rowNmae);
    }
}

/** 学校数据查询 **/
function searchSCHData() {
    var searchParams = $(".searchSCHForm").serializeJson(); //序列化成json 参数
    var table = $('#dg_sch').DataTable();
    reloadTable(table, searchParams);
}
/*学校--点击确定按钮--给学校选择框赋值*/
function resultSCHForInput(){
    var table = $('#dg_sch').createDataTable({"onRowClick" : function (data) {}});
    var rows = table.getSelectRows(); /*!//拿到选中项*/
    if (rows != null && rows.length == 1) {
        inputvalForChooseSCH(paramId,rows[0].schoolName,rows[0].schoolId);/*!//给点击控件赋值*/
        $(".selectSCHDialogModal").modal("hide");  /*!//关闭弹窗*/
    } else {
        jAlert('message',Msg.common.line_information, Msg.common.alter); /*!//没有选中行 提示错误*/
        return;
    }
}
/*给学校选择框赋值*/
function inputvalForChooseSCH(inputId,rowNmae,rowId) {
    $("#"+inputId).val(rowNmae)
    $("#schoolId").val(rowId);
    if($("#"+paramId).val()!=null) {
        /*监听学校有值以后右侧换成X图标*/
        $("#searchSCH").css('display','none');
        $("#clearSCH").css('display','block');
    }
}
