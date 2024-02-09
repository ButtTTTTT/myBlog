<#include "../import/top.ftl">
<#include "../import/navbar.ftl">
<style>
    .cardimg {
        width: 100%;
    }
</style>
<div class = "col-xs-12">
    <ol class = "breadcrumb" style = "margin-bottom: 5px;">
        <li><a href = "/"><i class = "icon-home"></i> 首页</a></li>
        <li class = "active">${articleTypeName!}</li>
    </ol>
    <div class = "panel">
        <div class = "panel-body">
            <#if articleVoIPage?? && (articleVoIPage.list)?? && (articleVoIPage.list)?size gt 0 >
                <#list  articleVoIPage.list as articleVo>
                    <div class = "col-xs-6 col-sm-3" style = "padding: 2px;">
                        <div class = "card">
                            <a href = "/article?articleId=${(articleVo.articleId)!}" style = "text-decoration:none">
                                <img class = "cardimg" src = "${(articleVo.articleCoverUrl)!}"
                                     alt = "${(articleVo.articleTitle)!}">
                                <div class = "card-heading"><strong>${(articleVo.articleTitle)!}</strong></div>
                                <div class = "card-content text-muted">
                                    <i class = "icon-time"></i>
                                    ${(articleVo.articleAddTime)?string("yyyy-MM-dd HH:mm:ss")}
                                </div>

                                <div class = "card-actions">
                                    <span class = "label label-info">
                                        <i class = "icon-eye-open"></i> ${(articleVo.articleLookNumber)!}
                                    </span> |
                                        <span class = "label label-success">
                                        <i class = "icon-thumbs-up"></i> ${(articleVo.articleGoodNumber)!}
                                    </span> |
                                        <span class = "label label-primary">
                                        <i class = "icon-star"></i> ${(articleVo.articleCollectionNumber)!}
                                    </span>
                                </div>
                            </a>
                        </div>
                    </div>
                </#list>
                <div class = "col-xs-12" style = "padding:5px;">
                    <div class = "col-sm-12" style = "padding: 0;text-align: center;">
                        <ul class = "pager" style = "margin-top: 10px;margin-bottom: 10px;">
                            <li class = "previous" onclick = "getNewData(1)">
                                <a href = "javascript:void(0)"><i class = "icon-step-backward"></i></a>
                            </li>

                            <#if (articleVoIPage.pageNumber)?? && (articleVoIPage.pageNumber) lte 1>
                                <li class = "previous disabled">
                                    <a href = "javascript:void(0)"><i class = "icon-chevron-left"></i></a>
                                </li>
                            <#else>
                                <li class = "previous" onclick = "getNewData('${articleVoIPage.pageNumber-1}')">
                                    <a href = "javascript:void(0)"><i class = "icon-chevron-left"></i></a>
                                </li>
                            </#if>
                            <li>
                                <a href = "javascript:void(0)" class = "btn">
                                    ${articleVoIPage.pageNumber}页/共${articleVoIPage.totalPage}</a>
                            </li>
                            <#if articleVoIPage.pageNumber gte articleVoIPage.totalPage>
                                <li class = "next disabled">
                                    <a href = "javascript:void(0)"><i class = "icon-chevron-right"></i></a>
                                </li>
                            <#else>
                                <li class = "next" onclick = "getNewData('${articleVoIPage.pageNumber+1}')">
                                    <a href = "javascript:void(0)"><i class = "icon-chevron-right"></i></a>
                                </li>
                            </#if>
                            <li class = "previous" onclick = "getNewData('${articleVoIPage.totalPage}')">
                                <a href = "javascript:void(0)"><i class = "icon-step-forward"></i></a>
                            </li>

<#--                            <li class = "next">-->
<#--                                <a href = "javascript:void(0)">-->
<#--                                    <input type = "number" id = "renderPageNumber" maxlength = "5"-->
<#--                                           style = "width:40px;height: 20px;margin: 0;"-->
<#--                                           oninput = "value=value.replace(/[^\d]/g,'')">-->
<#--                                </a>-->
<#--                            </li>-->
<#--                            <li class = "next">-->
<#--                                <a href = "javascript:void(0)" onclick = "renderPage()"-->
<#--                                   style = "padding-left: 2px;padding-right: 2px;">-->
<#--                                    跳转-->
<#--                                </a>-->
<#--                            </li>-->
                        </ul>
                    </div>
                </div>

            <#else >
                <#include "../import/nullData.ftl">
            </#if>
        </div>
    </div>
</div>


<script>
    function getNewData(pageNumber) {
        if (!checkNotNull(pageNumber)) {
            pageNumber = 1;
        }
        window.location.href = "/article/list?pageNumber=" + pageNumber + "<#if articleTypeId??>&articleTypeId=${articleTypeId!}</#if>";
    }

    function renderPage() {
        let renderPageNumber = $("#renderPageNumber").val();
        if (!checkNotNull(renderPageNumber)) {
            new $.zui.Messager("请输入跳转的页码！", {
                type: 'warning',
                placement: 'center'
            }).show();
            return;
        }
        let totalPage = '${articleVoIPage.totalPage}';
        if (parseInt(renderPageNumber) > parseInt(totalPage)) {
            renderPageNumber = totalPage;
        }
        getNewData(renderPageNumber);
    }
</script>
<#include "../import/viewBottom.ftl">
