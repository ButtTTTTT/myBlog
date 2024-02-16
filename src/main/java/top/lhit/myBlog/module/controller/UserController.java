package top.lhit.myBlog.module.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lhit.myBlog.common.utils.CommonPage;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.PublishArticleActionDto;
import top.lhit.myBlog.module.entity.*;
import top.lhit.myBlog.module.service.*;
import top.lhit.myBlog.module.vo.ArticleVo;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * @Title: UserController
 * @create 2021/12/21 22:19
 */
@Controller
@RequestMapping("/user")@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    private IUserCollectionArticleService userCollectionArticleService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private IArticleTagListService articleTagListService;
    @Autowired
    private IUploadFileListService uploadFileListService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommentReplyService commentReplyService;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request, MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        User user = (User) request.getSession().getAttribute("user");
        if (Objects.isNull(user.getUserPublishable()) || user.getUserPublishable() != 0) {
            return null;
        }

        return uploadFileListService.getUploadFileUrl(file);
    }


    /**
     * 用户管理页面
     *
     * @return
     */
    @GetMapping("/manager")
    public String userManager() {
        return "/user/userManager";
    }


    /**
     * 用户收藏
     *
     * @param pageNumber
     * @return
     */
    @GetMapping("/collection/list")
    public String collectionList(HttpServletRequest request, Integer pageNumber, Model model) {
        User user = (User) request.getSession().getAttribute("user");

        Page<Article> articlePage = new Page<>(pageNumber, 24);


        List<String> articleIdList = userCollectionArticleService.list(Wrappers.<UserCollectionArticle>lambdaQuery()
                        .eq(UserCollectionArticle::getUserId, user.getUserId())
                        .select(UserCollectionArticle::getArticleId)).stream()
                .map(UserCollectionArticle::getArticleId)
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(articleIdList)) {
            LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                    .in(Article::getArticleId, articleIdList)
                    .select(Article::getArticleId,
                            Article::getArticleAddTime,
                            Article::getArticleFaverRate,
                            Article::getArticleFaveriteNo,
                            Article::getArticleWatchTimes,
                            Article::getArticleCoverUrl,
                            Article::getArticleTitle);
            IPage<Article> articleIPage = articleService.page(articlePage, wrapper);
            model.addAttribute("articleIPage", CommonPage.restPage(articleIPage));
        }


        return "/user/collectionList";
    }

    /**
     * 用户收藏
     *
     * @param pageNumber
     * @return
     */
    @GetMapping("/article/list")
    public String articleList(HttpServletRequest request, Integer pageNumber, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        Page<ArticleVo> articlePage = new Page<>(pageNumber, 24);
        IPage<ArticleVo> articleVoIPage = articleService.articleList(articlePage, null, null);
        model.addAttribute("articleVoIPage", CommonPage.restPage(articleVoIPage));
        return "/user/articleList";
    }


    /**
     * 发布文章
     *
     * @return
     */
    @GetMapping("/publishArticle")
    public String releaseArticle(HttpServletRequest request, Model model, String articleId) {
        User user = (User) request.getSession().getAttribute("user");
        if (Objects.isNull(user) || Objects.isNull(user.getUserPublishable()) || user.getUserPublishable() != 0) {
            return "redirect:/";
        }

        Article article = articleService.getOne(Wrappers.<Article>lambdaQuery().eq(Article::getUserId, user.getUserId()).eq(Article::getArticleId, articleId));
        if (Objects.nonNull(article)) {
            model.addAttribute("article", article);

            //获取文章标签
            List<ArticleTagList> articleTagLists = articleTagListService.list(Wrappers.<ArticleTagList>lambdaQuery()
                    .eq(ArticleTagList::getArticleId, article.getArticleId())
                    .select(ArticleTagList::getArticleTagId));

            if (CollUtil.isNotEmpty(articleTagLists)) {
                List<String> articleTagIdList = articleTagLists.stream().map(ArticleTagList::getArticleTagId).collect(Collectors.toList());
                model.addAttribute("articleTagIdList", articleTagIdList);
            }
            //获取该文章类型的同级类型
            String articleTypeId = article.getArticleTypeId();
            List<ArticleType> articleSameTypeList = articleTypeService.list(Wrappers.<ArticleType>lambdaQuery().eq(ArticleType::getArticleTypeParentId, articleTypeService.getById(articleTypeId).getArticleTypeParentId()));
            model.addAttribute("articleSameTypeList", articleSameTypeList);

            //获取该文章上级类型
            model.addAttribute("articleTypeParentId", articleTypeService.getById(article.getArticleTypeId()).getArticleTypeParentId());
        }

        //获取类型
        List<ArticleType> articleType0List = articleTypeService.list(Wrappers.<ArticleType>lambdaQuery().isNull(ArticleType::getArticleTypeParentId).or().eq(ArticleType::getArticleTypeParentId, "").orderByAsc(ArticleType::getArticleTypeSort));
        model.addAttribute("articleType0List", articleType0List);

        //获取标签
        List<ArticleTag> articleTagList = articleTagService.list();
        model.addAttribute("articleTagList", articleTagList);

        return "/user/publishArticle";
    }

    /**
     * 获取二级文章分类
     *
     * @param articleTypeId
     * @return
     */
    @PostMapping("/getArticleTypeChild")
    @ResponseBody
    public CompletionStage<CommonResult> getArticleTypeChild(String articleTypeId) {
        return articleTypeService.getArticleTypeChild(articleTypeId);
    }


    /**
     * 发布文章方法
     *
     * @param publishArticleActionDto
     * @return
     */
    @PostMapping("/publishArticleAction")
    @ResponseBody
    public CommonResult publishArticleAction(HttpServletRequest request, @Valid PublishArticleActionDto publishArticleActionDto, MultipartFile articleCoverFile) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            return CommonResult.failed("登录过期，请重新登录");
        }
        User serviceById = userService.getById(user.getUserId());
        if (Objects.isNull(serviceById)) {
            session.setAttribute("user", serviceById);
        }
        if (Objects.isNull(serviceById.getUserPublishable()) || serviceById.getUserPublishable() != 0) {
            return CommonResult.failed("当前您还没有权限发布，请联系管理员");
        }
        if (Objects.nonNull(articleCoverFile)) {
            //判断是否上传的图片，是否是我们指定的像素
            BufferedImage read = ImageIO.read(articleCoverFile.getInputStream());
            if (Objects.isNull(read)) {
                return CommonResult.failed("请上传图片文件");
            }
            int width = read.getWidth();
            int height = read.getHeight();
            if (width != 300 || height != 150) {
                return CommonResult.failed("图片的像素为 300px * 150px");
            }


            publishArticleActionDto.setArticleCoverUrl(uploadFileListService.getUploadFileUrl(articleCoverFile));
        }
        return articleService.publishArticleAction(request, publishArticleActionDto);
    }

    /**
     * 个人中心，我的文章
     *
     * @param pageNumber
     * @param model
     * @return
     */
    @GetMapping("/myArticleList")
    public String myArticleList(HttpServletRequest request, Integer pageNumber, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1;
        }
        Page<ArticleVo> articlePage = new Page<>(pageNumber, 24);
        IPage<ArticleVo> articleVoIPage = articleService.articleList(articlePage, null, user.getUserId());
        model.addAttribute("articleVoIPage", CommonPage.restPage(articleVoIPage));

        return "/user/myArticleList";
    }

    /**
     * 删除文章
     *
     * @param articleId
     * @return
     */
    @PostMapping("/delArticle")
    @ResponseBody
    public CompletionStage<CommonResult> delArticle(String articleId) {
        return articleService.delArticle(articleId);
    }


    /**
     * 用户评论
     *
     * @param request
     * @param articleId
     * @param commentContent
     * @return
     */
    @PostMapping("/saveComment")
    @ResponseBody
    public CompletionStage<CommonResult> userSaveComment(HttpServletRequest request, String articleId, String commentContent, String commentId) {
        return commentService.userSaveComment(request, articleId, commentContent, commentId);
    }

    /**
     * 回复评论
     * @param commentId
     * @param articleId
     * @param commentContent
     * @return
     */
//    @PostMapping("/commentReply")
//    @ResponseBody
//    public CommonResult commentReply(HttpServletRequest request,String commentId,String articleId,String commentContent){
//        if (StrUtil.isBlank(commentId) || StrUtil.isBlank(articleId) || StrUtil.isBlank(commentContent)) {
//            return CommonResult.failed("评论失败，请刷新页面重试");
//        }
//        if (commentContent.length() < 1 || commentContent.length() > 800) {
//            return CommonResult.failed("评论内容在1-800个字符之间！");
//        }
//
//
//        User user = (User) request.getSession().getAttribute("user");
//        if (Objects.isNull(user)) {
//            return CommonResult.failed("客官！您的登录过期，请从新登录哦");
//        }
//        String userId = user.getUserId();
//
//
//        CommentReply commentReply = ICommentReplyService.getOne(Wrappers.<CommentReply>lambdaQuery().eq(CommentReply::getUserId, userId).select(Comment::getCommentTime).orderByDesc(Comment::getCommentTime), false);
//        if (Objects.nonNull(comment) && Objects.nonNull(comment.getCommentTime())) {
//            if ((comment.getCommentTime().getTime() + 10000) > System.currentTimeMillis()) {
//                return CommonResult.failed("客官您评论太快啦~~，休息一下吧");
//            }
//        }
//    }

}
