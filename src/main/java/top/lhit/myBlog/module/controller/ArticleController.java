package top.lhit.myBlog.module.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.aticle.ArticleDto;
import top.lhit.myBlog.module.entity.Article;
import top.lhit.myBlog.module.entity.User;
import top.lhit.myBlog.module.service.IArticleService;
import top.lhit.myBlog.module.service.IArticleTypeService;
import top.lhit.myBlog.module.vo.ArticleTypeTreeVo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Controller
@Slf4j
@CrossOrigin
public class ArticleController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleTypeService articleTypeService;

    @GetMapping("/article/test")
    public String testArticle() {

        return "/import/ArticleEditorDemo";

    }


    /**
     * 添加新文章
     * 业务逻辑：
     * 通过session 获取user 并校验是否有 权限 ？
     *
     * @param request
     * @return
     */
    @GetMapping("/acticle/editNewArticle")
    public CompletionStage<String> editNewArticle(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        CompletionStage<String> viewUrl = articleService.addNewArticle(user);

        return viewUrl;
    }

    @GetMapping("/acticletype/listAll")
    @ResponseBody
    public CompletionStage<CommonResult> articleTypelist() {


        return articleTypeService.aTypeListAll();


    }


    @PostMapping("/article/post/add")
    @ResponseBody
    public CompletionStage<CommonResult> articlePostAdd(HttpServletRequest request,  @Valid ArticleDto articleDto) {

        User user = (User) request.getSession().getAttribute("user");


        return  articleService.articlePublicsh(user,articleDto);

    }
}
