package top.lhit.myBlog.module.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import top.lhit.myBlog.common.utils.*;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lhit.myBlog.module.dto.user.UserInfoDto;
import top.lhit.myBlog.module.entity.*;
import top.lhit.myBlog.module.service.*;
import top.lhit.myBlog.module.vo.ArticleVo;
import top.lhit.myBlog.module.vo.CommentVo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Controller@Slf4j@CrossOrigin
public class ViewController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private IArticleTagListService articleTagListService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ICommentReplyService commentReplyService;
    @Autowired
    private ServletContext servletContext;

    /**
     * 清除首页缓存
     *
     * @return
     */
    @GetMapping("/ci")
    public String clearCache() {
        servletContext.removeAttribute("articleTypeList");
        servletContext.removeAttribute("articleHotList");
        servletContext.removeAttribute("articleTagList");
        servletContext.removeAttribute("adIndexList");
        servletContext.removeAttribute("linkList");
        return "redirect:/";
    }

    /**
     * 获取图像验证码
     *
     * @throws IOException
     */
    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CircleCaptcha captcha = CommonUtils.getCaptcha(request);
        captcha.write(response.getOutputStream());
    }

    /**
     * 用户注册页面
     *
     * @param request
     * @return
     */
    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        if (Objects.nonNull(request.getSession().getAttribute("user"))) {
            return "redirect:/";
        }

        return "/view/register";
    }

    /**
     * 用户注册方法
     *
     * @param request
     * @param userInfoDto
     * @return
     */
    @PostMapping("/userRegister")
    @ResponseBody
    public CompletionStage<CommonResult> userRegister(HttpServletRequest request, UserInfoDto userInfoDto) {
       return userService.userRegist(request,userInfoDto);
    }


    /**
     * 用户登陆页面
     *
     * @param request
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        if (Objects.nonNull(request.getSession().getAttribute("user"))) {
            return "redirect:/";
        }
        model.addAttribute("referer", request.getHeader("referer"));
        return "/view/login";
    }


    /**
     * 用户登录方法
     *
     * @param request
     * @param userInfoDto
     * @return
     */
    @PostMapping("/userLogin")
    @ResponseBody
    public CompletionStage<CommonResult> userLogin(HttpServletRequest request, UserInfoDto userInfoDto) {

        log.info("/userLogin : post =======>  {}  ",userInfoDto);
        return userService.userLogin(request,userInfoDto);
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "/";
    }

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "/view/index";
    }

    /**
     * 文章列表
     *
     * @param pageNumber
     * @return
     */
    @GetMapping("/article/list")
    public String articleListView(Integer pageNumber, String articleTitle, String articleTypeId, Model model) {
        Page<ArticleVo> articlePage = new Page<>(Objects.isNull(pageNumber) ? 1 : pageNumber, 24);
        IPage<ArticleVo> articleVoIPage = articleService.articleListView(articlePage, articleTitle, articleTypeId);

        //文章列表
        model.addAttribute("articleVoIPage", CommonPage.restPage(articleVoIPage));

        //文章分类名称
        if (StrUtil.isNotBlank(articleTypeId)) {
            ArticleType articleType = articleTypeService.getOne(Wrappers.<ArticleType>lambdaQuery().eq(ArticleType::getArticleTypeId, articleTypeId).select(ArticleType::getArticleTypeName), false);
            model.addAttribute("articleTypeName", articleType.getArticleTypeName());
            model.addAttribute("articleTypeId", articleTypeId);
        }

        return "/view/articleList";
    }


    /**
     * 获取标签对应的文章列表
     *
     * @param articleTagId
     * @param pageNumber
     * @return
     */
    @GetMapping("/tag/article/list")
    public String tagArticleList(String articleTagId, Integer pageNumber, Model model) {
        if (StrUtil.isBlank(articleTagId)) {
            return "redirect:/";
        }
        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1;
        }
        Page<ArticleVo> articlePage = new Page<>(Objects.isNull(pageNumber) ? 1 : pageNumber, 24);
        IPage<ArticleVo> articleVoIPage = articleService.tagArticleList(articlePage, articleTagId);
        model.addAttribute("articleVoIPage", CommonPage.restPage(articleVoIPage));

        //获取标签类型
        ArticleTag articleTag = articleTagService.getOne(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleTagId, articleTagId));
        if (Objects.nonNull(articleTag)) {
            model.addAttribute("articleTagName", articleTag.getArticleTagName());
        }

        model.addAttribute("articleTagId", articleTagId);
        return "/view/tagArticleList";

    }

    /**
     * 文章
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article")
    public String articleView(HttpServletRequest request, String articleId, Model model) {
        ArticleVo articleVo = articleService.getArticle(articleId);
        if (Objects.isNull(articleVo)) {
            return "redirect:/";
        }
        Article article = articleService.getOne(Wrappers.<Article>lambdaQuery().eq(Article::getArticleId, articleVo.getArticleId()).select(Article::getArticleId, Article::getArticleWatchTimes), false);
        //添加查看次数
        Integer articleLookNumber = article.getArticleWatchTimes();
        if (Objects.isNull(articleLookNumber) || articleLookNumber < 0) {
            articleLookNumber = 0;
        }
        ++articleLookNumber;
        article.setArticleWatchTimes(articleLookNumber);
        articleService.updateById(article);
        //隐藏作者用户名
        String userName = articleVo.getUserName();
        if (StrUtil.isNotBlank(userName)) {
            articleVo.setUserName(CommonUtils.getHideMiddleStr(userName));
        }
        //文章
        model.addAttribute("article", articleVo);
        //文章类型
        if (Objects.nonNull(articleVo) && StrUtil.isNotBlank(articleVo.getArticleTypeId())) {
            ArticleType articleType = articleTypeService.getOne(Wrappers.<ArticleType>lambdaQuery().eq(ArticleType::getArticleTypeId, articleVo.getArticleTypeId()).select(ArticleType::getArticleTypeName, ArticleType::getArticleTypeId), false);
            model.addAttribute("articleType", articleType);
        }
        return "/view/article";
    }

    /**
     * 获取文章评论列表
     *
     * @param articleId
     * @param pageNumber
     * @return
     */
    @PostMapping("/comment/list")
    @ResponseBody
    public CompletionStage<CommonResult> commentList(HttpServletRequest request, String articleId, Integer pageNumber) {
       return commentService.commentList(request,articleId,pageNumber);
    }


    /**
     * 联系
     *
     * @return
     */
    @GetMapping("/contact")
    public String contact() {
        return "/view/contact";
    }

    /**
     * 捐赠
     *
     * @return
     */
    @GetMapping("/donation")
    public String donation(Model model) {
        return "/view/donation";
    }


    /**
     * 文章点赞
     *
     * @param request
     * @param articleId
     * @return
     */
    @PostMapping("/articleGood")
    @ResponseBody
    public CompletionStage<CommonResult> articleGood(HttpServletRequest request, String articleId) {
        return articleService.articleGoodIncrease(request,articleId);
    }


    /**
     * 收藏文章
     *
     * @param request
     * @param articleId
     * @return
     */
    @PostMapping("/articleCollection")
    @ResponseBody
    public CommonResult articleCollection(HttpServletRequest request, String articleId) {
        User user = (User) request.getSession().getAttribute("user");
        if (Objects.isNull(user)) {
            return CommonResult.failed("客官！您还没有登录呢");
        }
        return articleService.articleCollection(user, articleId);
    }

    /**
     * 搜索文章
     *
     * @param request
     * @param articleTitle
     * @return
     */
    @GetMapping("/article/search")
    public String articleSearch(HttpServletRequest request, Integer pageNumber, String articleTitle, Model model) {
        if (StrUtil.isBlank(articleTitle)) {
            return "/";
        }
        articleTitle = articleTitle.trim();
        model.addAttribute("articleTitle", articleTitle);
        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1;
        }
        String ipAddr = CommonUtils.getIpAddr(request);
        ServletContext servletContext = request.getServletContext();
        ConcurrentMap<String, Long> articleSearchMap = (ConcurrentMap<String, Long>) servletContext.getAttribute("articleSearchMap");
        if (CollUtil.isEmpty(articleSearchMap) || Objects.isNull(articleSearchMap.get(ipAddr))) {
            articleSearchMap = new ConcurrentHashMap<>();
            articleSearchMap.put(ipAddr, DateUtil.currentSeconds());
        } else {
            if ((articleSearchMap.get(ipAddr) + 1 > DateUtil.currentSeconds())) {
                return "/view/searchError";
            }
        }
        //查询到的文章列表
//        List<Article> articleList = new ArrayList<>();

        //拆分搜索词,查询标签
        List<Word> words = WordSegmenter.seg(articleTitle);
        List<String> titleList = words.stream().map(Word::getText).collect(Collectors.toList());
        titleList.add(articleTitle);
        List<String> articleTagIdList = articleTagService.list(Wrappers.<ArticleTag>lambdaQuery()
                .in(ArticleTag::getArticleTagName, titleList)
                .select(ArticleTag::getArticleTagId)).stream().map(ArticleTag::getArticleTagId).collect(Collectors.toList());
        List<String> articleIdList = new ArrayList<>();
        if (CollUtil.isNotEmpty(articleTagIdList)) {
            articleIdList = articleTagListService.list(Wrappers.<ArticleTagList>lambdaQuery()
                    .in(ArticleTagList::getArticleTagId, articleTagIdList)
                    .select(ArticleTagList::getArticleId)).stream()
                    .map(ArticleTagList::getArticleId).collect(Collectors.toList());

        }

        //分页查询
        IPage<Article> articlePage = new Page<>(pageNumber, 12);
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.<Article>lambdaQuery()
                .like(Article::getArticleTitle, articleTitle)
                .select(Article::getArticleId,
                        Article::getArticleCoverUrl,
                        Article::getArticleFaverRate,
                        Article::getArticleWatchTimes,
                        Article::getArticleAddTime,
                        Article::getArticleTitle);
        if (CollUtil.isNotEmpty(articleIdList)) {
            queryWrapper.or().in(Article::getArticleId, articleIdList);
        }

        IPage<Article> articleIPage = articleService.page(articlePage, queryWrapper);
        model.addAttribute("articleIPage", CommonPage.restPage(articleIPage));

        //保持搜索时间
        articleSearchMap.put(ipAddr, DateUtil.currentSeconds());
        servletContext.setAttribute("articleSearchMap", articleSearchMap);

        return "/view/articleSearch";
    }

    /**
     * 给评论点赞
     *
     * @param commentId
     * @return
     */
    @PostMapping("/goodComment")
    @ResponseBody
    public CompletionStage<CommonResult> goodComment(HttpServletRequest request, String commentId) {
        return commentService.goodComment(request,commentId);
    }

}
