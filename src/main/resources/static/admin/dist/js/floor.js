$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/floor/list',
        datatype: "json",
        colModel: [
            {label: '楼层编号', name: 'floorId', index: 'floorId', width: 50, key: true},
            {label: '楼层名称', name: 'floorName', index: 'floorName', width: 90},
            {
                label: '楼层主图片',
                name: 'floorTitleImage',
                index: 'floorTitleImage',
                width: 120,
                formatter: coverImageFormatter
            },
        ],
        height: 760,
        rowNum: 10,
        rowList: [5, 10, 15, 20],
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

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='商品主图'/>";
    }
});

/**
 * jqGrid重新加载
 */
function reload() {
    initFlatPickr();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}


/**
 * 添加楼层
 */
function addFloor() {
    window.location.href = "/admin/floor/edit";
}

/**
 * 修改楼层
 */
function editFloor() {
    var id = getSelectdRow();
    if (id = null) {
        return;
    }
    window.location.href = "/admin/floor/edit/" + id;
}

/**
 * 删除楼层
 */
function deleteFloor() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/floor/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
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
    );
}
