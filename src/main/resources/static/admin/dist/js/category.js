$(function () {
    var categoryLevel = $("#categoryLevel").val();
    var parentId = $("#parentId").val();

    $("#jqGrid").jqGrid({
        url: '/admin/categories/list?categoryLevel=' + categoryLevel + '&parentId=' + parentId,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'catId', index: 'catId', width: 50, key: true, hidden: true},
            {label: '分类名称', name: 'catName', index: 'catName', width: 240},
            {label: '分类图片', name: 'catIcon', index: 'catIcon', width: 240, formatter: coverImageFormatter},
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

    new AjaxUpload('#uploadCategoryImage', {
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
                $("#categoryImg").attr("src", r.data);
                $("#categoryImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

//图片加载函数
function coverImageFormatter(cellvalue) {
    return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='分类主图'/>";
}

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function categoryAdd() {
    reset();
    $('.modal-title').html('分类添加');
    $('#categoryModal').modal('show');
}

/**
 * 管理下级分类
 */
function categoryManage() {
    var categoryLevel = parseInt($("#categoryLevel").val());
    var parentId = $("#parentId").val();
    var id = getSelectedRow();
    if (id == undefined || id < 0) {
        return false;
    }
    if (categoryLevel == 1 || categoryLevel == 2) {
        categoryLevel = categoryLevel + 1;
        window.location.href = '/admin/categories?categoryLevel=' + categoryLevel + '&parentId=' + id + '&backParentId=' + parentId;
    } else {
        swal("无下级分类", {
            icon: "warning",
        });
    }
}

/**
 * 返回上一层级
 */
function categoryBack() {
    var categoryLevel = parseInt($("#categoryLevel").val());
    var backParentId = $("#backParentId").val();
    if (categoryLevel == 2 || categoryLevel == 3) {
        categoryLevel = categoryLevel - 1;
        window.location.href = '/admin/categories?categoryLevel=' + categoryLevel + '&parentId=' + backParentId + '&backParentId=0';
    } else {
        swal("无上级分类", {
            icon: "warning",
        });
    }
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var catName = $("#categoryName").val();
    var catLevel = $("#categoryLevel").val();
    var catPid = $("#parentId").val();
    // var catIcon = $("#categoryIcon").val();
    var catIcon = $("#categoryImg")[0].src;
    if (!validCN_ENString2_18(catName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的分类名称！");
    } else {
        var data = {
            "catName": catName,
            "catLevel": catLevel,
            "catPid": catPid,
            "catIcon": catIcon
        };
        var url = '/admin/categories/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/categories/update';
            data = {
                "catId": id,
                "catName": catName,
                "catLevel": catLevel,
                "catPid": catPid,
                "catIcon": catIcon
            };
        }
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#categoryModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#categoryModal').modal('hide');
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
    }
});

function categoryEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    console.log(id);

    $.get("/admin/categories/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            $("#categoryName").val(r.data.catName);
            $("#categoryImg").attr("src", r.data.catIcon);
            $("#categoryImg").attr("type", "height: 64px;width: 64px;display:block;");
        }
    });
    $('.modal-title').html('分类编辑');
    $('#categoryModal').modal('show');

    /*
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('.modal-title').html('分类编辑');
    $('#categoryModal').modal('show');
    $("#categoryId").val(id);
    $("#categoryName").val(rowData.catName);
    //获取图片<img src= "url"> 标签src内容
    $("#categoryIcon").val(rowData.catIcon.src);
    */
}

/**
 * 分类的删除会牵涉到多级分类的修改和商品数据的修改，请谨慎删除分类数据，
 * 如果在商城页面不想显示相关分类可以通过调整rank值来调整显示顺序，
 * 不过代码我也写了一部分，如果想保留删除功能的话可以在此代码的基础上进行修改。
 */
function deleteCagegory() {

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
                    url: "/admin/categories/delete",
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
    )
    ;
}


//初始化样式
function reset() {
    $("#categoryName").val('');
    $("#categoryIcon").val(0);
    //上传文件用到
    // $("#categoryIcon").attr("src", '/admin/dist/img/img-upload.png');
    // $("#categoryIcon").attr("style", "height: 64px;width: 64px;display:block;");
    $('#edit-error-msg').css("display", "none");
}