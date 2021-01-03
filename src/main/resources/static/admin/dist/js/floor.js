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

    new AjaxUpload('#uploadFloorImage', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#floorImage").attr("src", r.data);
                $("#floorImage").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
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
    reset();
    $('.modal-title').html('楼层添加');
    $('#floorModal').modal('show')
}


/**
 * 编辑楼层
 */
function editFloor() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    //请求数据
    $.get("/admin/floor/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#floorImage").attr("src", r.data.floorTitleImage);
            $("#floorImage").attr("style", "height: 64px;width: 64px;display:block;");
            $("#floorName").val(r.data.floorName);
            $("#floorKeyword").val(r.data.floorKeyword);
        }
    });
    $('.modal-title').html('轮播图编辑');
    $('#floorModal').modal('show');
}







//绑定modal上的保存按钮
$('#saveButton').click(function () {
    // var redirectUrl = $("#redirectUrl").val();
    var imageSrc = $('#floorImage')[0].src;
    var floorName = $('#floorName').val();
    var floorKeyword = $('#floorKeyword').val();
    var data = {
        "floorTitleImage": imageSrc,
        "floorName": floorName,
        "floorKeyword": floorKeyword
    };
    var url = '/admin/floor/save';
    var id = getSelectedRowWithoutAlert();
    if (id != null) {
        url = '/admin/floor/update';
        data = {
            "floorId": id,
            "floorTitleImage": imageSrc,
            "floorName": floorName,
            "floorKeyword": floorKeyword
        };
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                $('#floorModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#floorModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});


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

function reset() {
    $("#redirectUrl").val('##');
    $("#floorImage").attr("src", '/admin/dist/img/img-upload.png');
    $("#floorImage").attr("style", "height: 64px;width: 64px;display:block;");
    $('#edit-error-msg').css("display", "none");
}