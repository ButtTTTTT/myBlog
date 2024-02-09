<#include "../import/top.ftl">
<#include "../import/navbar.ftl">
<style>
    .comments-list {
        margin-left: 25px;
    }

    .hoverPalm {
        cursor: pointer;
    }

    .loadMoreCommentBtn:hover {
        color: red;
    }

    .llBox {
        border-radius: 3px;
        border: 1px solid #D7D7D7;
        padding: 1px;
    }

    .goodCommentBgColor {
        background-color: #2c8931;
    }
</style>
<div class = "col-xs-12">
    <ol class = "breadcrumb">
        <li><a href = "/"><i class = "icon-home"></i> 首页</a></li>
        <li>
            <a href = "/article/list?articleTypeId=${(articleType.articleTypeId)!}">${(articleType.articleTypeName)!}</a>
        </li>
        <li class = "active">${(article.articleTitle)!}</li>
    </ol>
    <hr/>
</div>

<div class = "col-xs-12">
    <div class = "panel">
        <div class = "panel-body" style = "padding-top:0;">
            <article class = "article">
                <header>
                    <h1 class = "text-center" style = "margin-top: 5px;">
                        ${(article.articleTitle)!}
                        <br/>
                        <small>
                            发布时间: ${(article.articleAddTime)?string("yyyy-MM-dd HH:mm:ss")}
                        </small>
                    </h1>
                    <dl class = "dl-inline">
                        <dd><i class = "icon-user"></i>发布者:${(article.userName)!}</dd>
                        <dt>
                        <dd class = "pull-right">
                            <span class = "label label-info">
                                <i class = "icon-eye-open"></i> ${(article.articleLookNumber)!}
                            </span> |
                            <span class = "label label-success hoverPalm"
                                  onclick = "articleGood('${(article.articleId)!}')">
                                <i class = "icon-thumbs-up"></i> <span
                                        id = "articleGoodNumber">${(article.articleGoodNumber)!}</span>
                            </span> |
                            <span class = "label label-primary hoverPalm"
                                  onclick = "articleCollection('${(article.articleId)!}')">
                                <i class = "icon-star"></i> <span
                                        id = "articleCollectionNumber">${(article.articleCollectionNumber)!}</span>
                            </span>
                        </dd>
                    </dl>
                </header>
                <section class = "content">
                    <p>${(article.articleContext)!}</p>
                </section>
                <footer>
                    <div class = "col-xs-12" id = "commentListBox">

                    </div>


                    <div class = "form-group col-xs-12" style = "margin-top: 25px;padding: 2px;">
                        <span onclick = "loadMoreComment()" id = "loadMoreCommentBtn"
                              class = "pull-right hoverPalm loadMoreCommentBtn">更多评论<i
                                    class = "icon-double-angle-down"></i> </span>
                        <hr/>
                        <label for = "commentContent" id = "commentInfo"><#if user??>撰写评论:<#else>请先登录</#if></label>

                        <textarea id = "commentContent" name = "commentContent" maxlength = "1500"
                                  class = "form-control new-comment-text" rows = "2"
                                  placeholder = "<#if user??>撰写评论...<#else>请先登录..</#if>"
                                  <#if user??><#else>disabled = "disabled"</#if>></textarea>

                        <button type = "button" id="commentBtn" onclick = "saveComment('${(article.articleId)!}')"
                                class = "btn btn-success pull-right"
                                style = "margin-top: 10px;" <#if user??><#else>disabled = "disabled"</#if>>评论
                        </button>

                        <button type = "button" id="commentReplyBtn" onclick = "commentReplyAction()"
                                class = "btn btn-success pull-right"
                                style = "margin-top: 10px;" <#if user??><#else>disabled = "disabled"</#if>>回复
                        </button>

                    </div>
                </footer>
            </article>
        </div>
    </div>
</div>
<script>
    $("#commentReplyBtn").hide();
    let articleId = '${(article.articleId)!}';
    let pageNumber = 1;
    let totalPage = 1;
    let commentId ;

    function commentReply(commentId, userName) {
        $("#commentInfo").text("回复：" + userName);
        $("#commentContent").attr("placeholder", "回复：" + userName);
        this.commentId = commentId;

        $("#commentReplyBtn").show();
        $("#commentBtn").hide();

    }

    function commentReplyAction() {
        let commentContent = $('#commentContent').val();
        if (!checkNotNull(commentContent) || commentContent.length < 1) {
            zuiMsg("请填写评论");
            return;
        }
        if(!checkNotNull(commentId)){
            zuiMsg("程序出现错误，请刷新页面重试");
            return;
        }
        $.post("/user/commentReply", {
                commentId: commentId,
                articleId: articleId,
                commentContent: commentContent
            },
            function (data) {
                if (data.code == 200) {
                    zuiSuccessMsg("评论成功~");
                    $('#commentContent').val("");
                    return;
                }
                zuiMsg(data.message);
            });
    }

    function goodComment(obj, commentId) {
        $.post("/goodComment", {
                commentId: commentId
            },
            function (data) {
                if (data.code == 200) {
                    zuiSuccessMsg("点赞成功~");
                    $(obj).addClass("goodCommentBgColor");
                    let goodTxt = $(obj).text();
                    goodTxt = goodTxt.replace('点赞', '');
                    goodTxt.replace('次', '');
                    goodTxt = goodTxt.trim();
                    goodTxt = parseInt(goodTxt);
                    goodTxt = ++goodTxt;
                    $(obj).html('<i class="icon-thumbs-o-up"></i> 点赞 ' + goodTxt + '次');
                    return;
                }
                zuiMsg(data.message);
            });
    }

    function saveComment(articleId) {
        let commentContent = $('#commentContent').val();
        if (!checkNotNull(commentContent) || commentContent.length < 1) {
            zuiMsg("请填写评论");
            return;
        }
        $.post("/user/saveComment", {
                articleId: articleId,
                commentContent: commentContent
            },
            function (data) {
                if (data.code == 200) {
                    zuiSuccessMsg("评论成功~");
                    $('#commentContent').val("");
                    addCommentItem(data.data.commentTime, data.data.commentGoodNumber, data.data.userName, data.data.commentContent, data.data.commentId, 1, 0)
                    return;
                }
                zuiMsg(data.message);
            });
    }

    function articleCollection(articleId) {
        $.post("/articleCollection", {
                articleId: articleId
            },
            function (data) {
                if (data.code == 200) {
                    let articleGoodNumber = $("#articleCollectionNumber").text();
                    $("#articleCollectionNumber").text(++articleGoodNumber);
                    new $.zui.Messager(data.message, {
                        type: 'success',
                        placement: 'center'
                    }).show();
                    return;
                }
                zuiMsg(data.message);
            });
    }


    function articleGood(articleId) {
        $.post("/articleGood", {
                articleId: articleId
            },
            function (data) {
                if (data.code == 200) {
                    let articleGoodNumber = $("#articleGoodNumber").text();
                    $("#articleGoodNumber").text(++articleGoodNumber);
                    new $.zui.Messager(data.message, {
                        type: 'success',
                        placement: 'center'
                    }).show();
                    return;
                }
                zuiMsg(data.message);
            });
    }


    $(function () {
        $("#loadMoreCommentBtn").hide();
        $.post("/comment/list", {
                articleId: articleId
            },
            function (data) {
                if (data.code == 200) {
                    let commentList = data.data.list;
                    pageNumber = data.data.pageNumber;
                    totalPage = data.data.totalPage;
                    if (pageNumber < totalPage) {
                        $("#loadMoreCommentBtn").show();
                    } else {
                        $("#loadMoreCommentBtn").hide();
                    }
                    for (let i = 0; i < commentList.length; i++) {
                        addCommentItem(commentList[i].commentTime, commentList[i].commentGoodNumber, commentList[i].userName, commentList[i].commentContent, commentList[i].commentId, 0, commentList[i].isGoodComment)
                    }

                    return;
                }
                zuiMsg("获取评论失败！");
            });
    })


    function loadMoreComment() {
        if (pageNumber >= totalPage) {
            return;
        }
        $.post("/comment/list", {
                articleId: articleId,
                pageNumber: ++pageNumber
            },
            function (data) {
                if (data.code == 200) {
                    let commentList = data.data.list;
                    pageNumber = data.data.pageNumber;
                    totalPage = data.data.totalPage;
                    if (pageNumber < totalPage) {
                        $("#loadMoreCommentBtn").show();
                    } else {
                        $("#loadMoreCommentBtn").hide();
                    }
                    for (let i = 0; i < commentList.length; i++) {
                        addCommentItem(commentList[i].commentTime, commentList[i].commentGoodNumber, commentList[i].userName, commentList[i].commentContent, commentList[i].commentId, 0, commentList[i].isGoodComment)
                    }

                    return;
                }
                zuiMsg("获取评论失败！");
            });
    }

    function addCommentItem(commentTime, commentGoodNumber, userName, commentContent, commentId, isNewComment, isGoodComment) {
        let commentHtml =
            '<div class="content" style="border-bottom:1px dashed #D7D7D7;margin-bottom: 20px;margin-top: 10px;padding-bottom: 5px;">' +
            '<div class="pull-right text-muted"><i class="icon-time"></i> ' + commentTime + '</div> ' +
            '<div>' +
            '<strong>' +
            '<i class="icon-user"></i>' + userName + ' 说：' +
            '</strong>' +
            '</div>' +
            '<div class="text">&emsp;&emsp;' + commentContent + '</div>' +
            '<div class="actions">'
            // '<span class="hoverPalm llBox" onclick="commentReply(\'' + commentId + '\')"><i class="icon-edit"></i> 回复</span>';

        if (isGoodComment != null && isGoodComment == 1) {
            commentHtml += ' <span class="hoverPalm llBox goodCommentBgColor" onclick="goodComment(this,\'' + commentId + '\')"><i class="icon-thumbs-o-up"></i> 点赞 ' + commentGoodNumber + '次</span>';
        } else {
            commentHtml += ' <span class="hoverPalm llBox" onclick="goodComment(this,\'' + commentId + '\')"><i class="icon-thumbs-o-up"></i> 点赞 ' + commentGoodNumber + '次</span>';
        }
        commentHtml +=
            '</div>' +
            '<div class="comments-list" id="commentReplyBox"></div>' +
            '</div>';

        if (isNewComment === 1) {
            $("#commentListBox").prepend(commentHtml);
        } else {
            $("#commentListBox").append(commentHtml);
        }
    }

</script>
<#include "../import/viewBottom.ftl">
