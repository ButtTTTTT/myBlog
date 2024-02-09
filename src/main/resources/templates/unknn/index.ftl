<#include "../import/unknnTop.ftl">
<div class="panel col-xs-12">
    <div class="panel-body">
        <div class="col-xs-6">
            <div class="panel">
                <div class="panel-body">
                    <h5><i class="icon-desktop"></i> 系统类型：${osName!}</h5>
                    <h5><i class="icon-server"></i> 服务器IP：${hostAddress!}</h5>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="panel">
                <div class="panel-body">
                    <h5><i class="icon-th"></i> 文章类型数：${articleTypeCount!}</h5>
                    <h5><i class="icon-th-list"></i> 文章标签数：${articleTagCount!}</h5>
                    <h5><i class="icon-stack"></i> 文章数：${articleCount!}</h5>
                    <h5><i class="icon-user"></i> 用户数：${userCount!}</h5>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../import/bottom.ftl">