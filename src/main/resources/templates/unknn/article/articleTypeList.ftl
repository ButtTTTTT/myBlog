<#include "../../import/unknnTop.ftl">
<div class="panel col-sm-12">
    <div class="panel-body">
        <div class="panel col-sm-6">
            <div class="panel-body">
                <h4>
                    一级分类：
                </h4>
                <#if articleType0List?? && articleType0List?size gt 0 >
                    <div class="panel">
                        <div class="panel-body">
                            <button type="button" class="btn btn-success" onclick="addOrUpdateArticleType()">添加一级类型
                            </button>
                            <hr/>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>排序</th>
                                    <th>添加时间</th>
                                    <th>文章类型名称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list articleType0List as articleType>
                                    <tr>
                                        <td>${(articleType.articleTypeSort)!}</td>
                                        <td>
                                            ${(articleType.getArticleTypeAddTime()?string("yyyy-MM-dd HH:mm:ss"))}
                                        </td>
                                        <td>
                                            <a href="/unknn/article/type/list?articleTypeParentId=${(articleType.articleTypeId)}">
                                                ${(articleType.articleTypeName)!}
                                            </a>
                                        </td>
                                        <td>
                                            <button onclick="addOrUpdateArticleType('${(articleType.articleTypeId)!}','${(articleType.articleTypeName)!}','${(articleType.articleTypeSort)!}')"
                                                    type="button" class="btn btn-mini"><i class="icon-cog"></i> 修改
                                            </button>
                                            <button onclick="delArticleType('${(articleType.articleTypeId)!}')"
                                                    type="button" class="btn btn-mini"><i
                                                        class="icon-remove"></i> 删除
                                            </button>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                <#else >
                    <#include "../../import/nullData.ftl">
                </#if>
            </div>
        </div>
        <div class="panel col-sm-6">
            <div class="panel-body">
                <h4>
                    ${articleTypeName!'二级分类'}：
                </h4>
                <#if articleType0List?? && articleType0List?size gt 0 >
                    <div class="panel">
                        <div class="panel-body">
                            <button type="button" class="btn btn-success" onclick="addOrUpdateArticleType()">添加二级类型
                            </button>
                            <hr/>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>排序</th>
                                    <th>添加时间</th>
                                    <th>文章类型名称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list articleType1List as articleType>
                                    <tr>
                                        <td>${(articleType.articleTypeSort)!}</td>
                                        <td>
                                            ${(articleType.getArticleTypeAddTime()?string("yyyy-MM-dd HH:mm:ss"))}
                                        </td>
                                        <td>${(articleType.articleTypeName)!}</td>
                                        <td>
                                            <button onclick="addOrUpdateArticleType('${(articleType.articleTypeId)!}','${(articleType.articleTypeName)!}','${(articleType.articleTypeSort)!}','${(articleType.articleTypeParentId)!}')"
                                                    type="button" class="btn btn-mini"><i class="icon-cog"></i> 修改
                                            </button>
                                            <button onclick="delArticleType('${(articleType.articleTypeId)!}')"
                                                    type="button" class="btn btn-mini"><i
                                                        class="icon-remove"></i> 删除
                                            </button>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                <#else >
                    <#include "../../import/nullData.ftl">
                </#if>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="updateArticleTypeModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                            class="sr-only">关闭</span></button>
                <h4 class="modal-title" id="articleTypeTitle">修改二级目录</h4>
            </div>
            <input type="hidden" id="articleTypeId" name="articleTypeId1">
            <div class="modal-body">
                <#if articleType0List?? && articleType0List?size gt 0 >
                    <div class="form-group" id="articleTypeParentIdDiv">
                        <label for="articleTypeParentId">上级目录：</label>
                        <select class="form-control" id="articleTypeParentId" name="articleTypeParentId">
                            <option value="">--无--</option>
                            <#list articleType0List as articleType0>
                                <option value="${(articleType0.articleTypeId)!}">${(articleType0.articleTypeName)!}</option>
                            </#list>
                        </select>
                    </div>
                </#if>
                <div class="form-group">
                    <label for="articleTypeName">分类名称：</label>
                    <input type="text" class="form-control" id="articleTypeName" name="articleTypeName"
                           placeholder="分类名称">
                </div>
                <div class="form-group">
                    <label for="articleTypeSort">分类排序</label>
                    <input type="number" class="form-control" id="articleTypeSort" name="articleTypeSort"
                           placeholder="分类排序">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="articleUpdateAction()">提交</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    function articleUpdateAction() {
        let articleTypeId = $("#articleTypeId").val();
        let articleTypeName = $("#articleTypeName").val();
        let articleTypeSort = $("#articleTypeSort").val();
        let articleTypeParentId = $("#articleTypeParentId").val();

        $.post("/unknn/article/type/dml", {
                articleTypeId: articleTypeId,
                articleTypeName: articleTypeName,
                articleTypeSort: articleTypeSort,
                articleTypeParentId: articleTypeParentId
            },
            function (data) {
                if (data.code === 200) {
                    alert(data.message)
                    location.reload();
                    return;
                }

                zuiMsg(data.message);
            });

    }

    function addOrUpdateArticleType(articleTypeId, articleTypeName, articleTypeSort, articleTypeParentId) {
        $('#updateArticleTypeModal').modal('toggle', 'center');
        $("#articleTypeId").val(articleTypeId);
        $("#articleTypeName").val(articleTypeName);
        $("#articleTypeSort").val(articleTypeSort);
        $("#articleTypeParentId").val(articleTypeParentId);

        if (!checkNotNull(articleTypeId)) {
            $("#articleTypeTitle").text("添加类型");
        } else {
            $("#articleTypeTitle").text("修改类型");
        }
    }

    function delArticleType(articleTypeId) {
        if (confirm("是否删除")) {
            if (!checkNotNull(articleTypeId)) {
                zuiMsg("程序出错，请刷新页面重试");
                return;
            }
            $.post("/unknn/article/type/del", {
                    articleTypeId: articleTypeId
                },
                function (data) {
                    if (data.code == 200) {
                        alert(data.message)
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


</script>

<#include "../../import/bottom.ftl">