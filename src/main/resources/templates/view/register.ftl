<#include "../import/top.ftl">
<#include "../import/navbar.ftl">
<div class="col-xs-12">
    <div class="panel">
        <div class="panel-heading">欢迎加入</div>
        <div class="panel-body">
            <form class="form-horizontal" action="/userRegister" method="post" id="RegisterUserForm">
                <div class="form-group">
                    <label for="userName" class="col-sm-2">账号：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="userName" maxlength="25" name="userName"
                               placeholder="账号">
                    </div>
                </div>
                <div class="form-group">
                    <label for="userPassword" class="col-sm-2">密码：</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" maxlength="25" id="userPassword"
                               name="userPassword" placeholder="密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="checkPassword" class="col-sm-2">重复密码：</label>
                    <div class="col-sm-10">
                        <input type="password" maxlength="25" class="form-control" id="checkPassword"
                               name="checkPassword" placeholder="重复密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="verifyCode" class="col-sm-2">验证码：</label>
                    <div class="col-sm-10">
                        <div class="col-sm-10 col-xs-9" style="padding-left:0; padding-right:0;">
                            <input type="text" class="form-control" id="verifyCode" maxlength="4"
                                   name="verifyCode" placeholder="验证码">
                        </div>
                        <div class="col-sm-2 col-xs-3" style="padding-left:0; padding-right:0;">
                            <img class="img-thumbnail" src="/getCaptcha" id="check_code_img"
                                 onclick="javascript:this.src='/getCaptcha?' + new Date().getTime();"
                                 style="cursor: pointer; height: 32px; width: 100px;" title="点击刷新验证码"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                    </div>
                    <div class="col-sm-2">
                        <button type="button" onclick="userRegister()" class="btn btn-default">
                            <i class="icon-user"></i> 注册
                        </button>
                    </div>
                    <div class="col-sm-8" align="right">
                        已有账号？<a href="/login">马上登录</a>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
<script type="text/javascript">
    function userRegister() {
        let userName = $("#userName").val();
        let userPassword = $("#userPassword").val();
        let checkPassword = $("#checkPassword").val();
        let verifyCode = $("#verifyCode").val();

        if (!checkNotNull(userName) || userName.length > 25 || userName.length < 3) {
            zuiMsg("用户名在3-25个字符之间")
            return;
        }

        if (!checkNotNull(userPassword) || userPassword.length > 25 || userPassword.length < 3) {
            zuiMsg("密码在3-25个字符之间")
            return;
        }
        if (userPassword !== checkPassword) {
            zuiMsg("两次密码不一致")
            return;
        }
        if (userName === userPassword) {
            zuiMsg("用户名和密码不能相同哦")
            return;
        }
        if (!checkNotNull(verifyCode) || verifyCode.length !== 4) {
            zuiMsg("验证码不正确")
            return;
        }

        $.post("/userRegister", {
                userName: userName,
                userPassword: userPassword,
                verifyCode: verifyCode
            },
            function (data) {
                if (data.code == 200) {
                    alert(data.message)
                    location.href="/login"
                    return;
                }
                zuiMsg(data.message);
                $("#check_code_img").click();
            });
    }
</script>

<#include "../import/viewBottom.ftl">
