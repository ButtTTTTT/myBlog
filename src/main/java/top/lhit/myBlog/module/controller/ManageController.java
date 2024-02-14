package top.lhit.myBlog.module.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
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
import top.lhit.myBlog.module.dto.ArticlePageDto;
import top.lhit.myBlog.module.dto.ad.AdDto;
import top.lhit.myBlog.module.dto.aticle.ArticleTypeUpdateDto;
import top.lhit.myBlog.module.dto.user.UserDto;
import top.lhit.myBlog.module.dto.user.UserListPageDto;
import top.lhit.myBlog.module.entity.*;
import top.lhit.myBlog.module.service.*;
import top.lhit.myBlog.module.vo.AdVo;
import top.lhit.myBlog.module.vo.ArticleVo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;

@Controller
@Slf4j
public class ManageController {
    @Autowired
    private IUnknnService unknnService;
    @Autowired
    private IAdTypeService adTypeService;
    @Autowired
    private IAdService adService;
    @Autowired
    private IFriendLinkService linkService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleTypeService iArticleTypeService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICarouselService carouselService;

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("/unknn/login")
    public String adminLogin(HttpServletRequest request) {
        if (Objects.nonNull(request.getSession().getAttribute("unknn"))) {
            return "redirect:/unknn/index";
        }
        return "/unknn/unknnLogin";
    }

    /**
     * 管理员登录
     *
     * @param request
     * @param unknnName
     * @param unknnPassword
     * @param verifyCode
     * @return
     */
    @PostMapping("/unknn/unknnLogin")
    @ResponseBody
    public CompletionStage<CommonResult> unknnLogin(HttpServletRequest request,
                                                    String unknnName,
                                                    String unknnPassword,
                                                    String verifyCode) {
        return unknnService.confirmUnknn(request, unknnName, unknnPassword, verifyCode);
    }

    /**
     * 管理员退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("/unknn/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return "redirect:/unknn/login";
    }

    /**
     * 后台主页
     *
     * @param model
     * @return
     */
    @GetMapping("/unknn/index")
    public String manageIndex(Model model) {
        //系统信息
        model.addAttribute("osName", SystemUtil.getOsInfo().getName());
        model.addAttribute("hostAddress", SystemUtil.getHostInfo().getAddress());
        //文章数量
        int articleTypeCount = iArticleTypeService.count();
        model.addAttribute("articleTypeCount", articleTypeCount);
        model.addAttribute("articleCount", articleService.count());
        model.addAttribute("articleTagCount", articleTagService.count());
        //用户数量
        int userCount = userService.count();
        model.addAttribute("userCount", userCount);
        return "/unknn/index";
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @PostMapping("/unknn/user/del")
    @ResponseBody
    public CompletionStage<CommonResult> userDel(String userId) {
        return userService.delUserDel(userId);
    }

    /**
     * 修改用户
     *
     * @param userDto
     * @return
     */
    @PostMapping("/unknn/user/update")
    @ResponseBody
    public CompletionStage<CommonResult> userUpdate(@Valid UserDto userDto) {
        return userService.postUserUpdate(userDto);
    }

    /**
     * 冻结用户
     *
     * @param userId
     * @return
     */
    @PostMapping("/unknn/user/ban")
    @ResponseBody
    public CompletionStage<CommonResult> userBan(String userId) {
        return userService.postUserBan(userId);
    }

    /**
     * 后台 - 用户列表
     *
     * @param userListPageDto
     * @param model
     * @return 前端ftl页面
     */
    @GetMapping("/unknn/user/list")
    public CompletionStage<String> userList(@Valid UserListPageDto userListPageDto, Model model) {

        return userService.getUserList(userListPageDto, model);

    }
    //文章接口
//    文章类型接口

    /**
     * 文章类型列表接口：
     * 文章类型列表，包含文章数量
     *
     * @return
     */
    @GetMapping("/unknn/article/type/list")
    public String articleTypeList(Model model, String articleTypeParentId) {
        List<ArticleType> articleType0List = iArticleTypeService.list(Wrappers.<ArticleType>lambdaQuery().isNull(ArticleType::getArticleTypeParentId).or().eq(ArticleType::getArticleTypeParentId, "").orderByAsc(ArticleType::getArticleTypeSort));
        LambdaQueryWrapper<ArticleType> queryWrapper = Wrappers.<ArticleType>lambdaQuery()
                .isNotNull(ArticleType::getArticleTypeParentId)
                .ne(ArticleType::getArticleTypeParentId, "")
                .orderByAsc(ArticleType::getArticleTypeSort);
        if (StrUtil.isNotBlank(articleTypeParentId)) {
            queryWrapper.eq(ArticleType::getArticleTypeParentId, articleTypeParentId);
            model.addAttribute("articleTypeName", iArticleTypeService.getById(articleTypeParentId).getArticleTypeName());
        }
        List<ArticleType> articleType1List = iArticleTypeService.list(queryWrapper);
        model.addAttribute("articleType0List", articleType0List);
        model.addAttribute("articleType1List", articleType1List);
        return "/unknn/article/articleTypeList";
    }

    /**
     * 文章类型dml请求接口
     * 修改/添加文章类型 : 一级类型/二级类型
     *
     * @param articleType
     * @return
     */
    @PostMapping("/unknn/article/type/dml")
    @ResponseBody
    public CompletionStage<CommonResult> addOrUpdate(@Valid ArticleType articleType) {
        servletContext.removeAttribute("articleTypeList");
        return iArticleTypeService.articleTypeAdd(articleType);
    }


    @PostMapping("/unknn/article/type/del")
    @ResponseBody
    public CompletionStage<CommonResult> articleTypeDel(@NotBlank(message = "文章分类id 不能为空") String articleTypeId) {
        return iArticleTypeService.articleTypeDel(articleTypeId);
    }

    @PostMapping("/unknn/article/type/update")
    @ResponseBody
    public CompletionStage<CommonResult> articleTypeUpdate(@Valid ArticleTypeUpdateDto articleTypeUpdateDto) {
        return iArticleTypeService.articleTypeUpdate(articleTypeUpdateDto);
    }

    // 文章标签接口
    @GetMapping("/unknn/article/tag/list")
    public String articleTagList(Model model) {
        model.addAttribute("articleTagList", articleTagService.list(Wrappers.<ArticleTag>lambdaQuery().orderByDesc(ArticleTag::getArticleTagAddTime)));
        return "/unknn/article/articleTagList";
    }

    @PostMapping("/unknn/article/tag/addOrUpdate")
    @ResponseBody
    public CompletionStage<CommonResult> articleTagAdd(ArticleTag articleTag) {
        return articleTagService.articleTagAdd(articleTag);
    }

    @PostMapping("/unknn/article/tag/del")
    @ResponseBody
    public CompletionStage<CommonResult> articleTagDel(String articleTagId) {
        return articleTagService.articleTagDel(articleTagId);
    }
    //文章接口

    /**
     * 文章类型列表包含文章数量
     *
     * @param model
     * @return
     */
    @GetMapping("/unknn/article/list")
    public String articleList(Model model, @Valid ArticlePageDto articlePageDto) {

        IPage<ArticleVo> articleVoPage = new Page<>(articlePageDto.getPageNumber(), 24);

        IPage<ArticleVo> articleVoIPage = articleService.articleList(articleVoPage, articlePageDto.getArticleTitle(), null);

        model.addAttribute("articleVoIPage", CommonPage.restPage(articleVoIPage));

        if (StrUtil.isNotBlank(articlePageDto.getArticleTitle())) {

            model.addAttribute("articleTitle", articlePageDto.getArticleTitle());

        }

        return "/unknn/article/articleList";

    }

    /**
     * 设置为热门文章
     *
     * @param articleId
     * @return
     */
    @PostMapping("/unknn/article/hot")
    @ResponseBody
    public CompletionStage<CommonResult> articleHot(String articleId) {
        return articleService.setArticleHot(articleId);
    }


    @PostMapping("/unknn/article/del")
    @ResponseBody
    public CompletionStage<CommonResult> articleDel(String articleId) {
        return articleService.delArticle(articleId);
    }

    /**
     * 友链列表
     *
     * @param model
     * @return
     */
    @GetMapping("/unknn/link/list")
    public String linkList(Model model) {
        model.addAttribute("linkList", linkService.list(Wrappers.<FriendLink>lambdaQuery().orderByAsc(FriendLink::getLinkSort)));
        return "/unknn/linkList";
    }

    @GetMapping("/unknn/carousel/list")
    public String carouselList(Model model) {
        model.addAttribute("carouselList", carouselService.list(Wrappers.<Carousel>lambdaQuery().orderByAsc(Carousel::getCarouselSort)));
        return "/unknn/carouselList";
    }

    /**
     * 添加或修改友链
     *
     * @param friendLink
     * @return
     */
    @PostMapping("/unknn/link/addOrUpdate")
    @ResponseBody
    public CompletionStage<CommonResult> linkAdd(FriendLink friendLink) {
        return linkService.addLink(friendLink);
    }

    /**
     * 删除友链
     *
     * @param linkId
     * @return
     */
    @PostMapping("/unknn/link/del")
    @ResponseBody
    public CompletionStage<CommonResult> linkDel(String linkId) {
        return linkService.delLink(linkId);
    }

    /**
     * 广告列表
     *
     * @param model
     * @return
     */
    @GetMapping("/unknn/ad/list")
    public String getAdList(String adTypeId, Model model) {
        List<AdType> adTypeList = adTypeService.list(Wrappers.<AdType>lambdaQuery()
                .orderByAsc(AdType::getAdTypeSort));
        model.addAttribute("adTypeList", adTypeList);
        List<AdVo> adVoList = adService.adList(adTypeId);
        model.addAttribute("adVoList", adVoList);
        return "/unknn/adList";
    }

    /**
     * 广告类型管理
     *
     * @param adType
     * @return
     */
    @PostMapping("/unknn/ad/type/addOrUpdate")
    @ResponseBody
    public CompletionStage<CommonResult> adTypeDML(AdType adType) {
        return adTypeService.adTypeUpdate(adType);
    }

    /**
     * 广告管理
     *
     * @param adDto
     * @return
     */
    @PostMapping("/unknn/ad/addOrUpdate")
    @ResponseBody
    public CompletionStage<CommonResult> adAddOrUpdate(AdDto adDto, MultipartFile file) {
        return adService.adDml(adDto, file);
    }

    /**
     * 删除广告
     *
     * @param adId
     * @return
     */
    @PostMapping("/unknn/ad/del")
    @ResponseBody
    public CompletionStage<CommonResult> adDel(String adId) {
        return adService.adDel(adId);
    }

    /**
     * 修改unknn密码
     *
     * @param newPassword
     * @return
     */
    @PostMapping("/unknn/password/update")
    @ResponseBody
    public CompletionStage<CommonResult> passwordUpdate(HttpServletRequest request, String newPassword) {
        return unknnService.updatePassword(request, newPassword);
    }

    /**
     * 轮播图删除
     *
     * @param carouselId
     * @return
     */
    @PostMapping("/unknn/carousel/del")
    @ResponseBody
    public CompletionStage<CommonResult> delCarousel(String carouselId) {

        log.info("/unknn/carousel/del : post  ");

        return carouselService.delCarousel(carouselId);

    }

    /**
     * 修改或删除Carousel
     *
     * @param carousel
     * @return
     */
    @PostMapping("/unknn/carousel/addOrUpdate")
    @ResponseBody
    public CompletionStage<CommonResult> addOrUpdateCarousel(Carousel carousel) {

        log.info("/unknn/carousel/addOrUpdate : post   -> ");

        return carouselService.addOrUpdateCarousel(carousel);

    }

    /**
     * 禁用轮播图
     * @param carouselId
     * @return
     */
    @PostMapping("/unknn/carousel/ban")
    @ResponseBody
    public CompletionStage<CommonResult> banCarousel(String carouselId) {

        log.info("/unknn/carousel/ban : post   -> ");

        return carouselService.banCarousel(carouselId);

    }

}
