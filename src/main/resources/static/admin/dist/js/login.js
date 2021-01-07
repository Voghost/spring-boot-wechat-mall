let result;

window.callback = function (res) {
    console.log(res)
    // res（用户主动关闭验证码）= {ret: 2, ticket: null}
    // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
    if (res.ret === 0) {
        var data = {
            "ticket": res.ticket,
            "randStr": res.randstr,
        };
        var url = "/tc"
        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    document.myForm.submit();
                } else {
                    alert("验证失败");
                }
                ;
            },
            error: function () {
                alert("验证失败");
            }
        });
    }
}

