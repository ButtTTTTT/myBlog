<#include "../import/top.ftl">
<#include "../import/navbar.ftl">
<div class="col-xs-12">
    <#include "../import/userManagerNav.ftl">
    <div class="panel col-xs-12 col-sm-9" style="padding: 1px;">
        <div class="panel-heading">
            <i class="icon-paper-clip"></i>我的资料
        </div>
        <div class="panel-body">
            <span class="label"><i class="icon-time"></i> 注册时间：</span> <span
                    class="label label-success">${(user.userRegisterTime)?string("yyyy-MM-dd HH:mm:ss")}</span>
            <br/>
            <span class="label"><i class="icon-user"></i> 用户名&emsp;：</span> <span
                    class="label label-success">${(user.userName)!}</span>
            <br/>
            <span class="label"><i class="icon-leaf"></i> 状态&emsp;&emsp;：</span>
            <#if (user.userFrozen)?? && (user.userFrozen)==1>
                <span class="label label-danger">冻结</span>
            <#else >
                <span class="label label-success">正常</span>
            </#if>
            <br/>
        </div>
    </div>
</div>


<#include "../import/viewBottom.ftl">
