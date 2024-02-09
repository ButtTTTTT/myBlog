<#include "../import/top.ftl">
<#include "../import/navbar.ftl">
<style>
    .col2Padding {
        padding-left: 2px;
        padding-right: 2px;
    }

    .articleTagStyle {
        color: #ffffff;
    }

    .articleTagStyle:hover {
        font-weight: bold;
    }
</style>

<div class="container">
    <#--    轮播图 开始  -->
    <div id="myNiceCarousel" class="carousel slide" data-ride="carousel">
        <!-- 圆点指示器 -->
        <ol class="carousel-indicators">
            <li data-target="#myNiceCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myNiceCarousel" data-slide-to="1"></li>
            <li data-target="#myNiceCarousel" data-slide-to="2"></li>
        </ol>

        <!-- 轮播项目 -->
        <div class="carousel-inner">
            <div class="item active">
                <img alt="First slide" src="http://openzui.com/docs/img/slide1.jpg">
                <div class="carousel-caption">
                    <h3>我是第一张幻灯片</h3>
                    <p>:)</p>
                </div>
            </div>
            <div class="item">
                <img alt="Second slide" src="http://openzui.com/docs/img/slide2.jpg">
                <div class="carousel-caption">
                    <h3>我是第二张幻灯片</h3>
                    <p>0.0</p>
                </div>
            </div>
            <div class="item">
                <img alt="Third slide" src="http://openzui.com/docs/img/slide3.jpg">
                <div class="carousel-caption">
                    <h3>我是第三张幻灯片</h3>
                    <p>最后一张咯~</p>
                </div>
            </div>
        </div>

        <!-- 项目切换按钮 -->
        <a class="left carousel-control" href="#myNiceCarousel" data-slide="prev">
            <span class="icon icon-chevron-left"></span>
        </a>
        <a class="right carousel-control" href="#myNiceCarousel" data-slide="next">
            <span class="icon icon-chevron-right"></span>
        </a>
    </div>
    <#--    轮播图 结束  -->
    <div class="col-xs-12  col-sm-9 col2Padding">
        <#--        广告开始  -->
        <#if adIndexList?? && adIndexList?size gt 0 >
            <div class="img-thumbnail" style="margin-bottom: 10px;">
                <div id="myNiceCarousel" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <#list adIndexList as adIndex>
                            <#if adIndex_index == 0>
                                <li data-target="#myNiceCarousel" data-slide-to="${adIndex_index!}" class="active"></li>
                            <#else >
                                <li data-target="#myNiceCarousel" data-slide-to="${adIndex_index!}"></li>
                            </#if>
                        </#list>
                    </ol>
                    <div class="carousel-inner">
                        <#list adIndexList as adIndex>
                            <#if adIndex_index == 0>
                                <div class="item active">
                                    <a target="_blank" href="${(adIndex.adLinkUrl)!}">
                                        <img alt="" src="${(adIndex.adImgUrl)!}">
                                    </a>
                                </div>
                            <#else >
                                <div class="item">
                                    <a target="_blank" href="${(adIndex.adLinkUrl)!}">
                                        <img alt="First slide" src="${(adIndex.adImgUrl)!}">
                                        <div class="carousel-caption">
                                            <h3>${(adIndex.adTitle)!}</h3>
                                        </div>
                                    </a>
                                </div>
                            </#if>
                        </#list>
                    </div>
                    <a class="left carousel-control" href="#myNiceCarousel" data-slide="prev">
                        <span class="icon icon-chevron-left"></span>
                    </a>
                    <a class="right carousel-control" href="#myNiceCarousel" data-slide="next">
                        <span class="icon icon-chevron-right"></span>
                    </a>
                </div>
            </div>
        </#if>
        <#--        广告结束  -->

        <#--        遍历文章开始 -->
        <#if articleTypeList??>
            <#list articleTypeList as articleType>
                <#if (articleType.articleList)?? && (articleType.articleList)?size gt 0>
                    <div class="panel">
                        <div class="panel-body">
                            <h4 style="margin-top: 0px;"><i
                                        class="icon-th-large"></i> ${(articleType.articleTypeName)!}</h4>
                            <#list (articleType.articleList) as indexArticle>
                                <div class="col-xs-6 col-sm-4" style="padding: 2px;">
                                    <div class="card">
                                        <a href="/article?articleId=${(indexArticle.articleId)!}"
                                           style="text-decoration:none">
                                            <img class="cardimg" src="${(indexArticle.articleCoverUrl)!}"
                                                 alt="${(indexArticle.articleTitle)!}">
                                            <div class="card-heading"><strong>${(indexArticle.articleTitle)!}</strong>
                                            </div>
                                            <div class="card-content text-muted">
                                                <i class="icon-time"></i>
                                                ${(indexArticle.articleAddTime)?string("yyyy-MM-dd")}
                                            </div>

                                            <div class="card-actions">
                                            <span class="label label-info">
                                                <i class="icon-eye-open"></i> ${(indexArticle.articleLookNumber)!}
                                            </span> |
                                                <span class="label label-success">
                                                <i class="icon-thumbs-up"></i> ${(indexArticle.articleGoodNumber)!}
                                            </span> |
                                                <span class="label label-primary">
                                                <i class="icon-star"></i> ${(indexArticle.articleCollectionNumber)!}
                                            </span>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </#if>
            </#list>
        </#if>
    </div>
    <#--        遍历文章结束 -->
</div>


<script>

    $(function () {
        let labelClassList = ["label-badge", "label-primary", "label-success", "label-info", "label-warning", "label-danger"];
        let articleTags = $("#articleTagListBox span");
        for (let i = 0; i < articleTags.length; i++) {
            $(articleTags[i]).addClass(labelClassList[Math.floor(Math.random() * labelClassList.length)]);
        }
    })

</script>

<#include "../import/viewBottom.ftl">
