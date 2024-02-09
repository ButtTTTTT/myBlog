<#include "../import/unknnTop.ftl">
<div class="panel">
    <div class="panel-body">
        <form class="form-horizontal" action="/unknn/user/list" method="get">
            <div class="form-group">
                <label for="userName" class="col-sm-1">用户名:</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" name="userName" id="userName" placeholder="用户名">
                </div>
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-success">查询</button>
                </div>
                <div class="col-sm-1">
                    <a href="/unknn/user/list" class="btn btn-success">查询全部</a>
                </div>
            </div>
        </form>
    </div>
</div>
<#--    做判断 是否有数据回传 -->

<#if userPage?? && userPage.list?size gt 0>
    <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="icon icon-info-sign">提示: 停用的用户无法登陆</i></h4>
    <div class="panel">
        <div class="panel-body">
            <#--    表格-->

            <table class="table">
                <thead>
                <tr>
                    <th>注册时间</th>
                    <th>用户名</th>
                    <th>是否启用</th>
                    <th>文章权限</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list userPage.list as user>
                    <tr>
                        <td>${(user.userRegisterTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>${(user.userName)!}</td>
                        <td>
                            <#--            判断 用户是否启用了   -->
                            <#if (user.userStatus)?? && (user.userStatus)==0>
                                <span class="label label-danger">用户冻结</span>
                            <#else >
                                <span class="label label-success">正常用户</span>
                            </#if>

                        </td>
                        <td>
                            <#--            判断 用户是否启用了   -->
                            <#if (user.userPublishable)?? && (user.userPublishable)==0>
                                <span class="label label-danger">不能发布</span>
                            <#else >
                                <span class="label label-success">正常发布</span>
                            </#if>

                        </td>
                        <td>
                            <button onclick="userUpdate('${(user.userId)!}','${(user.userName)!}','${(user.userStatus)!}','${(user.userPublishable)!}')"
                                    type="button" class="btn btn-mini"><i class="icon-cog"></i> 修改
                            </button>
                            <button onclick="delUser('${(user.userId)!}')" type="button" class="btn btn-mini"><i
                                        class="icon-remove"></i> 删除
                            </button>
                            <button onclick="banUser('${(user.userId)!}')" type="button" class="btn btn-mini"><i
                                        class="icon icon-ban-circle"></i> 停用
                            </button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
<#--分页跳转-->
    <div class="panel">
        <div class="panel-body" style="padding: 0;">
            <div class="col-sm-12" style="padding: 0;text-align: center;">
                <ul class="pager" style="margin-top: 10px;margin-bottom: 10px;">
                    <li class="previous" onclick="getNewData(1)">
                        <a href="javascript:void(0)"><i class="icon-step-backward"></i></a>
                    </li>

                    <#if userPage.pageNumber lte 1>
                        <li class="previous disabled">
                            <a href="javascript:void(0)"><i class="icon-chevron-left"></i></a>
                        </li>
                    <#else>
                        <li class="previous" onclick="getNewData('${userPage.pageNumber-1}')">
                            <a href="javascript:void(0)"><i class="icon-chevron-left"></i></a>
                        </li>
                    </#if>
                    <li>
                        <a href="javascript:void(0)" class="btn">
                            ${userPage.pageNumber}页/共${userPage.totalPage}</a>
                    </li>
                    <#if userPage.pageNumber gte userPage.totalPage>
                        <li class="next disabled">
                            <a href="javascript:void(0)"><i class="icon-chevron-right"></i></a>
                        </li>
                    <#else>
                        <li class="next" onclick="getNewData('${userPage.pageNumber+1}')">
                            <a href="javascript:void(0)"><i class="icon-chevron-right"></i></a>
                        </li>
                    </#if>
                    <li class="previous" onclick="getNewData('${userPage.totalPage}')">
                        <a href="javascript:void(0)"><i class="icon-step-forward"></i></a>
                    </li>


                    <li class="next">
                        <a href="javascript:void(0)">
                            <input type="number" id="renderPageNumber" maxlength="5"
                                   style="width:50px;height: 20px;" oninput="value=value.replace(/[^\d]/g,'')">
                        </a>
                    </li>
                    <li class="next">
                        <a href="javascript:void(0)" onclick="renderPage()"
                           style="padding-left: 2px;padding-right: 2px;">
                            跳转
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

<#else>
    <div style="text-align: center;">
        <h3>
            <i class="icon icon-coffee"></i>
        </h3>
        <h3>没有数据了</h3>
    </div>
</#if>
<#--    模态框 -->
<div class="modal fade" id="userUpdateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/unknn/user/update" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                                class="sr-only">关闭</span></button>
                    <h4 class="modal-title">修改用户</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="userId" id="userId">
                    <div class="form-group">
                        <label for="userNameUpdate">用户名：</label>
                        <input type="text" class="form-control" disabled="disabled" id="userNameUpdate"
                               placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <label for="userPassword">用户密码：</label>
                        <input type="password" class="form-control" name="userPassword" id="userPassword"
                               placeholder="用户密码">
                    </div>
                    <div class="form-group">
                        <label for="userStatus">用户状态 1为正常 0 为停用</label>
                        <div class="input-group">
                            <label class="radio-inline">
                                <input type="radio" name="userStatus" value="1" checked="checked"> 正常
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="userStatus" value="0"> 停用
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userPublishable">发布文章</label>
                        <div class="input-group">
                            <label class="radio-inline">
                                <input type="radio" name="userPublishable" value="1"> 允许
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="userPublishable" value="0" checked="checked"> 禁止
                            </label>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="userUpdateAction()">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
<#--    用户修改 按钮请求 -->
    function userUpdateAction() {
        let userId = $("#userId").val();
        let userPassword = $("#userPassword").val();
        let userStatus = $("input[name='userStatus']:checked").val();
        let userPublishable = $("input[name='userPublishable']:checked").val();

        if (!checkNotNull(userId)) {
            zuiMsg("程序出错，刷新重试");
            return;
        }
        if (!checkNotNull(userStatus)) {
            zuiMsg("请选择是否冻结用户");
            return;
        }
        if (!checkNotNull(userPublishable)) {
            zuiMsg("请选择是否运行发布文章");
            return;
        }
        $.post("/unknn/user/update", {
                userId: userId,
                userPassword: userPassword,
                userStatus: userStatus,
                userPublishable: userPublishable
            },
            function (data) {
                if (data.code == 200) {
                    location.reload();
                    return;
                }

                zuiMsg(data.message);
            });
    }


    function userUpdate(userId, userName, userStatus, userPublishable) {
        $('#userUpdateModal').modal('toggle', 'center');
        $("#userId").val(userId);
        $("#userNameUpdate").val(userName);
        $(":radio[name='userStatus'][value='" + userStatus + "']").prop("checked", "checked");
        $(":radio[name='userPublishable'][value='" + userPublishable + "']").prop("checked", "checked");
    }


    function delUser(userId) {
        if (confirm("是否删除")) {
            if (!checkNotNull(userId)) {
                zuiMsg('程序出错，请刷新页面重试');
                return;
            }
            $.post("/unknn/user/del", {
                    userId: userId
                },
                function (data) {
                    if (data.code == 200) {
                        location.reload();
                        return;
                    }
                    new $.zui.Messager(data.message, {
                        type: 'warning',
                        placement: 'center'
                    }).show();
                });
        }
    }


    function banUser(userId) {
        if (confirm("是否停用")) {
            if (!checkNotNull(userId)) {
                zuiMsg('程序出错，请刷新页面重试');
                return;
            }
            $.post("/unknn/user/ban", {
                    userId: userId
                },
                function (data) {
                    if (data.code == 200) {
                        location.reload();
                        return;
                    }
                    new $.zui.Messager(data.message, {
                        type: 'warning',
                        placement: 'center'
                    }).show();
                });
        }
    }


    function getNewData(pageNumber) {
        if (!checkNotNull(pageNumber)) {
            pageNumber = 1;
        }
        window.location.href = "/unknn/user/list?pageNumber=" + pageNumber + "<#if (userName?? && userName?length>0)>&userName=${userName!}</#if>";
    }

    function renderPage() {
        let renderPageNumber = $("#renderPageNumber").val();
        if (!checkNotNull(renderPageNumber)) {
            zuiMsg("请输入跳转的页码！");
            return;
        }
        let totalPage = '${userPage.totalPage}';
        if (parseInt(renderPageNumber) > parseInt(totalPage)) {
            renderPageNumber = totalPage;
        }
        getNewData(renderPageNumber);
    }


</script>
    <style>
        th {
            text-align: center;
        }
        td {
            text-align: center;
        }
    </style>
<#include "../import/bottom.ftl">