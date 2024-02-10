package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.lhit.myBlog.common.utils.CommonPage;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.common.utils.CommonUtils;
import top.lhit.myBlog.module.entity.Comment;
import top.lhit.myBlog.module.entity.User;
import top.lhit.myBlog.module.mapper.CommentMapper;
import top.lhit.myBlog.module.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.module.service.IUserService;
import top.lhit.myBlog.module.vo.CommentVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;
    /**
     * 文章评论列表
     * @param articleId
     * @return
     */
    @Override
    public IPage<CommentVo> getArticleCommentList(Page<CommentVo> commentVoPage, String articleId) {
        return commentMapper.getArticleCommentList(commentVoPage,articleId);
    }

    @Override
    public CompletionStage<CommonResult> goodComment(HttpServletRequest request, String commentId) {
        return CompletableFuture.supplyAsync(()->{
            HttpSession session = request.getSession();


            if (StrUtil.isBlank(commentId)) {
                return CommonResult.failed("未获取到需要的数据，请刷新页面重试");
            }

            //一个小时只能给一个评论点赞
            HashMap<String, Long> goodCommentMap = (HashMap<String, Long>) session.getAttribute("goodCommentMap");
            if (CollUtil.isEmpty(goodCommentMap)) {
                goodCommentMap = new HashMap<>();
            } else {
                if (Objects.nonNull(goodCommentMap.get(commentId))) {
                    Long goodCommentTime = goodCommentMap.get(commentId);
                    if ((goodCommentTime + 3600) >= DateUtil.currentSeconds()) {
                        return CommonResult.failed("客官，这个评论您已经点过赞了哦");
                    }
                }
            }

            Comment comment = commentService.getById(commentId);
            if (Objects.isNull(comment)) {
                return CommonResult.failed("点赞失败，未获取到需要的数据，请刷新页面重试");
            }
            Integer commentGoodNumber = comment.getCommentGoodNumber();
            ++commentGoodNumber;
            if (commentService.updateById(comment.setCommentGoodNumber(commentGoodNumber))) {
                goodCommentMap.put(commentId, DateUtil.currentSeconds());
                session.setAttribute("goodCommentMap", goodCommentMap);
                return CommonResult.success("点赞成功");
            }
            return CommonResult.failed("点赞失败");
        });
    }

    @Override
    public CompletionStage<CommonResult> commentList(HttpServletRequest request, String articleId, Integer pageNumber) {
//        return CompletableFuture.supplyAsync(()->{
//        if (StrUtil.isBlank(articleId)) {
//            return CommonResult.failed("程序出现错误，请刷新页面重试");
//        }
//        if (Objects.isNull(pageNumber) || pageNumber < 1) {
//            pageNumber = 1;
//        }
//        Page<CommentVo> commentVoPage = new Page<>(pageNumber, 5);
//        IPage<CommentVo> commentVoIPage = commentService.getArticleCommentList(commentVoPage, articleId);
//        commentVoIPage.getRecords().stream().forEach(commentVo -> {
//            commentVo.setUserName(CommonUtils.getHideMiddleStr(commentVo.getUserName()));
//        });
//
//        //已经点过赞的评论
//        HashMap<String, Long> goodCommentMap = (HashMap<String, Long>) request.getSession().getAttribute("goodCommentMap");
//        if (CollUtil.isNotEmpty(goodCommentMap)) {
//            List<String> commentIds = goodCommentMap.keySet().stream().collect(Collectors.toList());
//            commentVoIPage.getRecords().stream().forEach(commentVo -> {
//                if (commentIds.contains(commentVo.getCommentId())) {
//                    commentVo.setIsGoodComment(1);
//                }
//            });
//        }
//
//        return CommonResult.success(CommonPage.restPage(commentVoIPage));
//        });
        final Integer finalPageNumber;
        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            finalPageNumber = 1;
        } else {
            finalPageNumber = pageNumber;
        }
        return CompletableFuture.supplyAsync(()->{
            if (StrUtil.isBlank(articleId)) {
                return CommonResult.failed("程序出现错误，请刷新页面重试");
            }
            Page<CommentVo> commentVoPage = new Page<>(finalPageNumber, 5);
            IPage<CommentVo> commentVoIPage = commentService.getArticleCommentList(commentVoPage, articleId);
            commentVoIPage.getRecords().stream().forEach(commentVo -> {
                commentVo.setUserName(CommonUtils.getHideMiddleStr(commentVo.getUserName()));
            });

            //已经点过赞的评论
            HashMap<String, Long> goodCommentMap = (HashMap<String, Long>) request.getSession().getAttribute("goodCommentMap");
            if (CollUtil.isNotEmpty(goodCommentMap)) {
                List<String> commentIds = goodCommentMap.keySet().stream().collect(Collectors.toList());
                commentVoIPage.getRecords().stream().forEach(commentVo -> {
                    if (commentIds.contains(commentVo.getCommentId())) {
                        commentVo.setIsGoodComment(1);
                    }
                });
            }

            return CommonResult.success(CommonPage.restPage(commentVoIPage));
        });
    }

    @Override
    public CompletionStage<CommonResult> userSaveComment(HttpServletRequest request, String articleId, String commentContent, String commentId) {
        return CompletableFuture.supplyAsync(()->{
            if (StrUtil.isBlank(articleId) || StrUtil.isBlank(commentContent)) {
                return CommonResult.failed("评论失败，请刷新页面重试");
            }
            if (commentContent.length() < 1 || commentContent.length() > 800) {
                return CommonResult.failed("评论内容在1-800个字符之间！");
            }
            User user = (User) request.getSession().getAttribute("user");
            if (Objects.isNull(user)) {
                return CommonResult.failed("客官！您的登录过期，请从新登录哦");
            }
            String userId = user.getUserId();


            Comment comment = commentService.getOne(Wrappers.<Comment>lambdaQuery().eq(Comment::getUserId, userId).select(Comment::getCommentTime).orderByDesc(Comment::getCommentTime), false);
            if (Objects.nonNull(comment) && Objects.nonNull(comment.getCommentTime())) {
                if ((comment.getCommentTime().getTime() + 10000) > System.currentTimeMillis()) {
                    return CommonResult.failed("客官您评论太快啦~~，休息一下吧");
                }
            }

            Comment comment1 = new Comment();
            comment1.setArticleId(articleId);
            comment1.setUserId(userId);
            comment1.setCommentContent(commentContent);
            comment1.setCommentTime(DateUtil.date());
            comment1.setCommentGoodNumber(0);

            if (commentService.save(comment1)) {
                CommentVo commentVo = new CommentVo();
                BeanUtils.copyProperties(comment1, commentVo);
                commentVo.setUserName(
                        userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserId, commentVo.getUserId()).select(User::getUserName)).getUserName()
                );
                commentVo.setCommentTime(DateUtil.format(comment1.getCommentTime(),"yyyy-MM-dd HH:mm:ss"));
                return CommonResult.success(commentVo);
            }
            return CommonResult.failed("评论失败");
        });
    }
}
