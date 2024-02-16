<div style="margin-bottom: 2px;" class="col-xs-12">
    <div class="col-xs-6" style="padding-left: 2px;">
        <h2 style="color: #C74743;padding: 0;margin: 2px;">热情分享</h2>
        <small style="color: #808080;">&emsp;- 生活 工作 -</small>
    </div>
    <div class="col-xs-6">
        <span class="btn btn-mini btn-danger pull-right" style="margin-top: 10px;">
            <a style="text-decoration:none;color: #ffffff;" href="/app/app.apk"><i class="icon-android"></i> APP下载</a>
        </span>
    </div>
</div>

</div>
<nav class=" navbar mb navbar-inverse  " role="navigation">
    <div class="container-fluid">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target=".navbar-collapse-example">
                    <span class="sr-only">切换导航</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Home</a>
            </div>
            <div class="collapse navbar-collapse navbar-collapse-example">
                <ul class="nav navbar-nav">
                    <#if articleTypeList?? && articleTypeList?size gt 0 >
                        <#list articleTypeList as articleType>
                            <li class="dropdown">
                                <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">${(articleType.articleTypeName)!}<b class="caret"></b></a>
                                <#if (articleType.articleTypeTreeVoList)?? && (articleType.articleTypeTreeVoList)?size gt 0>
                                    <ul class="dropdown-menu" role="menu">
                                        <#list (articleType.articleTypeTreeVoList) as articleTypeTreeVo>
                                            <li><a href="/article/list?articleTypeId=${(articleTypeTreeVo.articleTypeId)!}">${(articleTypeTreeVo.articleTypeName)!}</a></li>
                                        </#list>
                                    </ul>
                                </#if>
                            </li>
                        </#list>
                    </#if>
                    <li><a href="/contact"><i class="icon-comments"></i> 联系</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <form class="navbar-form navbar-left" role="search" action="/article/search" method="get">
                        <div class="form-group">
                            <input type="text" value="${articleTitle!}" name="articleTitle" maxlength="25" max="25" class="form-control" placeholder="搜索">
                        </div>
                        <button type="submit" class="btn btn-default"><i class="icon-search"></i> 搜索</button>
                    </form>
                    <#if user??>
                        <li>
                            <a href="/user/manager"><i class="icon-user"></i> 个人中心</a>
                        <li>
                        <#if (user.userPublishArticle)?? && (user.userPublishArticle)==1>
                            <li><a href="/user/publishArticle"><i class="icon-edit"></i> 发布文章</a></li>
                        </#if>
                        <li>
                            <a href="/logout"><i class="icon-signout"></i> 退出登录</a>
                        <li>
                    <#else >
                        <li><a href="/register"><i class="icon-user"></i> 注册</a></li>
                        <li><a href="/login"><i class="icon-signin"></i> 登陆</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</nav>
<div class="container">