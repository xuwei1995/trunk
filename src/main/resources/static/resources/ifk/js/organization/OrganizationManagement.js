/**
 * 画面加载完毕
 */
$(function () {
    //tab2点击事件( 选取第二个标签页（从 0 开始索引）)
    $(".nav-tabs li:eq(1) a").on('click', function(e) {
        switchTab2(e);
    });
    //tab3点击事件( 选取第三个标签页（从 0 开始索引）)
    $(".nav-tabs li:eq(2) a").on('click', function(e) {
        switchTab3(e)
    });
    //初始化table
    initDataTable();
});

/**
 * 初始化table
 */
function initDataTable() {
    var formJson = $(".searchForm").serializeJson();
    $('#dtOrganization').createDataTable({
        "allowCheck":true,
        "ajax" : {
            url:ctx+"/getAllOrganization",
            data: formJson
        },//通过ajax实现分页的url路径。
        "columns": [
            {
                "data": "orgId", "bSearchable": false, "bSortable": false, "bVisible": false
            },
            {
                "data": "orgName", "bSearchable": true, "bSortable": true

            },
            {
                "data": "orgAbbrname", "bSearchable": true, "bSortable": true
            },
            {
                "data": "statusLabel", "bSearchable": true, "bSortable": true

            },
            {
                "data": "count", "bSearchable": true, "bSortable": true

            },
            {
                "data": "applyDate", "bSearchable": true, "bSortable": true,
                "render": function (data) {
                    return formatDateStr(data);
                }
            },
            {
                "data": "auditDate", "bSearchable": true, "bSortable": true,
                "render": function (data) {
                    return formatDateStr(data);
                }
            },
            {
                "data": "auditBy", "bSearchable": true, "bSortable": true
            }
        ]
    });
}
/** 数据查询，重载画面 **/
function searchData() {
    var formJson = $(".searchForm").serializeJson();
    var table = $('#dtOrganization').DataTable();
    reloadTable(table, formJson);
}
//切到tab2
function switchTab2(e){
    if(e) {
        e.preventDefault();
    }
    $(".nav-tabs li:eq(1) a").tab('show')
    var addHtml=$.ajax({url:ctx+"/addOrganizationRequest",async:false});
    $("#tab_2").html(addHtml.responseText);
    /** 绑定保存按钮点击事件**/
    $("#btnSave").on("click", function (event) {
        saveData();
    });
}
//切到tab3
function switchTab3(e){
    if(e) {
        e.preventDefault();
    }
    $(".nav-tabs li:eq(2) a").tab('show')
    var addHtml=$.ajax({url:ctx+"/editOrganizationRequest?orgId=1",async:false});
    $("#tab_3").html(addHtml.responseText);
    /**绑定保存按钮点击事件**/
    $("#btnSave").on("click", function (event) {
        saveData();
    });
}