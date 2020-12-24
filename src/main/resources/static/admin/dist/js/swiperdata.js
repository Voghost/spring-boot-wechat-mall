$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/swiperdatas/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'swiperId', index: 'swiperId', width: 50, key: true, hidden: true},
            {label: '轮播图', name: 'imageSrc', index: 'imageSrc', width: 180, formatter: coverImageFormatter},
            {label: '商品Id', name: 'goodsId', index: 'goodsId', width: 120},
            {label: '跳转链接', name: 'navigatorUrl', index: 'gnavigatorUrl', width: 120},
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

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"120\" width=\"160\" alt='coverImage'/>";
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    new AjaxUpload('#uploadSwiperImage', {
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
                $("#swiperImg").attr("src", r.data);
                $("#swiperImg").attr("style", "width: 128px;height: 128px;display:block;");
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
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function swiperAdd() {
    reset();
    $('.modal-title').html('轮播图添加');
    $('#swiperModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    // var redirectUrl = $("#redirectUrl").val();
    var imageSrc = $('#swiperImg')[0].src;
    var goodsId = $('#goodsId').val();
    var data = {
        "imageSrc": imageSrc,
        "goodsId": goodsId
    };
    var url = '/admin/swiperdatas/save';
    var id = getSelectedRowWithoutAlert();
    if (id != null) {
        url = '/admin/swiperdatas/update';
        data = {
            "swiperId": id,
            "goodsId": goodsId,
            "imageSrc": imageSrc
        };
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                $('#swiperModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#swiperModal').modal('hide');
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

function swiperEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    //请求数据
    $.get("/admin/swiperdatas/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#swiperImg").attr("src", r.data.imageSrc);
            $("#swiperImg").attr("style", "height: 64px;width: 64px;display:block;");
            $("#goodsId").val(r.data.goodsId);
        }
    });
    $('.modal-title').html('轮播图编辑');
    $('#swiperModal').modal('show');
}

function deleteSwiper() {
    var id = getSelectedRows();
    if (id == null) {
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
                    url: "/admin/swiperdatas/delete",
                    contentType: "application/json",
                    data: JSON.stringify(id),
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
    )
    ;
}


function reset() {
    $("#redirectUrl").val('##');
    $("#swiperImg").attr("src", '/admin/dist/img/img-upload.png');
    $("#swiperImg").attr("style", "height: 64px;width: 64px;display:block;");
    $('#edit-error-msg').css("display", "none");
}