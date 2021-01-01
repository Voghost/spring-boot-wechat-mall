$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/users/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'userId', index: 'userId', width: 50, key: true},
            // {label: '昵称', name: 'userOpenId', index: 'userOpenId', width: 180},
            {label: '创建时间', name: 'userCreateTime', index: 'userCreateTime', width: 100},
            {label: '上次更新时间', name: 'userUpdateTime', index: 'userUpdateTime', width: 100},
            {label: '上次登录时间', name: 'userLastLoginTime', index: 'userLastLoginTime', width: 100},
            {label: '当前状态', name: 'userIsActive', index: 'userIsActive', width: 80, formatter: lockedFormatter}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function lockedFormatter(cellvalue) {
        if (cellvalue == false) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">锁定</button>";
        } else if (cellvalue == true) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">正常</button>";
        }
    }

    function deletedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">注销</button>";
        } else if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">正常</button>";
        }
    }
});

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}


function lockUser(lockStatus) {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    if (lockStatus != 0 && lockStatus != 1) {
        swal('非法操作', {
            icon: "error",
        });
    }
    swal({
        title: "确认弹框",
        text: "确认要修改账号状态吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/users/lock/" + lockStatus,
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("操作成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}