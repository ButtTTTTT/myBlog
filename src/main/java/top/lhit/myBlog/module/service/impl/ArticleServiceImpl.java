package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.common.utils.CommonUtils;
import top.lhit.myBlog.framework.exception.CommonException;
import top.lhit.myBlog.module.dto.PublishArticleActionDto;
import top.lhit.myBlog.module.dto.aticle.ArticleDto;
import top.lhit.myBlog.module.entity.*;
import top.lhit.myBlog.module.mapper.ArticleMapper;
import top.lhit.myBlog.module.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.module.vo.ArticleVo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author unknn
 * @since 2023-11-29
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private IArticleTagListService articleTagListService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ICommentReplyService commentReplyService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private IUserCollectionArticleService userCollectionArticleService;
    @Autowired
    private IUploadFileListService uploadFileListService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleTagService articleTagService;

    /**
     * 文章列表
     *
     * @param articlePage
     * @param articleTitle
     * @return
     */
    @Override
    public IPage<ArticleVo> articleList(IPage<ArticleVo> articlePage, String articleTitle, String userId) {
        return articleMapper.articleList(articlePage, articleTitle, userId);
    }

    /**
     * 删除文章
     *
     * @param articleId
     * @return
     */
    @Override
    public CompletionStage<CommonResult> delArticle(String articleId) {
        return CompletableFuture.supplyAsync(() -> {
            if (StrUtil.isBlank(articleId)) {
                return CommonResult.failed("删除失败，参数不正确，请刷新页面重试");
            }
            Article article = getById(articleId);
            if (Objects.isNull(article)) {
                return CommonResult.failed("删除失败，可能该文章已经被删除");
            }

            //删除文章封面图片
            String articleCoverUrl = article.getArticleCoverUrl();
            //检查该图片是否被其他文章使用
            if (count(Wrappers.<Article>lambdaQuery().eq(Article::getArticleCoverUrl, articleCoverUrl)) <= 1) {
                File file = new File(CommonUtils.getClasspath(), "static" + article.getArticleCoverUrl());
                FileUtil.del(file);
                //删除文件对应的数据库记录
                uploadFileListService.remove(Wrappers.<UploadFileList>lambdaQuery().eq(UploadFileList::getFileUrl, article.getArticleCoverUrl()));
            }

            //删除文章
            if (!removeById(articleId)) {
                return CommonResult.failed("删除失败，可能该文章已经被删除");
            }
            //删除文章评论
            List<Comment> commentList = commentService.list(Wrappers.<Comment>lambdaQuery()
                    .eq(Comment::getArticleId, articleId)
                    .select(Comment::getCommentId));
            if (CollUtil.isNotEmpty(commentList)) {
                List<String> commentIdList = commentList.stream().map(Comment::getCommentId).collect(Collectors.toList());
                commentService.removeByIds(commentIdList);
                commentReplyService.remove(Wrappers.<CommentReply>lambdaQuery().in(CommentReply::getCommentId, commentIdList));
            }
            //删除文章对应的标签
            articleTagListService.remove(Wrappers.<ArticleTagList>lambdaQuery().eq(ArticleTagList::getArticleId, articleId));

            //删除用户收藏的文章
            userCollectionArticleService.remove(Wrappers.<UserCollectionArticle>lambdaQuery().eq(UserCollectionArticle::getArticleId, articleId));

            servletContext.removeAttribute("articleTypeList");
            return CommonResult.success("删除成功！");
        });
    }

    @Override
    public CompletionStage<CommonResult> setArticleHot(String articleId) {
        return CompletableFuture.supplyAsync(() -> {
            if (articleService.update(Wrappers.<Article>lambdaUpdate().eq(Article::getArticleId, articleId).set(Article::getArticleHot, 1))) {
                servletContext.removeAttribute("articleHotList");
                return CommonResult.success("设置成功");
            }
            return CommonResult.failed("设置失败");
        });
    }

    /**
     * 文章列表 前端
     *
     * @param articlePage
     * @param articleTitle
     * @param articleTypeId
     * @return
     */
    @Override
    public IPage<ArticleVo> articleListView(Page<ArticleVo> articlePage, String articleTitle, String articleTypeId) {
        return articleMapper.articleListView(articlePage, articleTitle, articleTypeId);
    }

    /**
     * 发布文章方法
     *
     * @param publishArticleActionDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult publishArticleAction(HttpServletRequest request, PublishArticleActionDto publishArticleActionDto) {
        //保存文章
        User user = (User) request.getSession().getAttribute("user");
        Article article = new Article();
        article.setArticleId(publishArticleActionDto.getArticleId());
        article.setArticleTypeId(publishArticleActionDto.getArticleTypeId());
        article.setArticleCoverUrl(publishArticleActionDto.getArticleCoverUrl());
        article.setUserId(user.getUserId());
        article.setArticleTitle(publishArticleActionDto.getArticleTitle());
        if (StrUtil.isBlank(article.getArticleId())) {
            article.setArticleAddTime(DateUtil.date());
        }
        article.setArticleContext(publishArticleActionDto.getArticleContext());
        article.setArticleFaveriteNo(0);
        article.setArticleWatchTimes(0);
        article.setArticleHot(0);
        article.setArticleFaverRate(0);

        if (!saveOrUpdate(article)) {
            return CommonResult.failed("操作失败，请刷新页面重试!");
        }

        //保持文章的标签
        String[] articleTagIds = publishArticleActionDto.getArticleTagIds();
        if (Objects.nonNull(articleTagIds) && articleTagIds.length > 0) {
            //删除原先的标签数据
            articleTagListService.remove(Wrappers.<ArticleTagList>lambdaQuery().eq(ArticleTagList::getArticleId, article.getArticleId()));
        }

        ArrayList<ArticleTagList> articleTagLists = new ArrayList<>();
        for (String articleTagId : articleTagIds) {
            ArticleTagList articleTagList = new ArticleTagList();
            articleTagList.setArticleId(article.getArticleId());
            articleTagList.setArticleTagId(articleTagId);
            articleTagLists.add(articleTagList);
        }
        if (!articleTagListService.saveBatch(articleTagLists, 50)) {
            throw new CommonException("操作文章失败，保存文章标签失败");
        }

        servletContext.removeAttribute("articleTypeList");

        return CommonResult.success("操作成功");
    }


    /**
     * 首页最新文章
     *
     * @return
     */
    @Override
    public List<ArticleVo> getIndexArticleList() {
        return articleMapper.getIndexArticleList();
    }

    /**
     * 根据文章id获取文章信息
     *
     * @param articleId
     * @return
     */
    @Override
    public ArticleVo getArticle(String articleId) {
        return articleMapper.getArticle(articleId);
    }

    /**
     * 收藏文章
     *
     * @param user
     * @param articleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult articleCollection(User user, String articleId) {

        if (userCollectionArticleService.count(Wrappers.<UserCollectionArticle>lambdaQuery()
                .eq(UserCollectionArticle::getUserId, user.getUserId())
                .eq(UserCollectionArticle::getArticleId, articleId)) > 0) {
            return CommonResult.failed("客官！该文章您已经收藏了，请到个人中心查看哦");
        }

        UserCollectionArticle userCollectionArticle = new UserCollectionArticle();
        userCollectionArticle.setUserId(user.getUserId());
        userCollectionArticle.setArticleId(articleId);
        userCollectionArticle.setUserCollectionArticleTime(DateUtil.date());
        if (!userCollectionArticleService.save(userCollectionArticle)) {
            return CommonResult.failed("收藏失败啦，刷新页面重试");
        }

        //添加收藏次数
        Article article = getById(articleId);
        if (Objects.nonNull(article)) {
            Integer articleCollectionNumber = article.getArticleFaverRate();
            ++articleCollectionNumber;
            article.setArticleFaverRate(articleCollectionNumber);
            if (!updateById(article)) {
                throw new CommonException("收藏失败");
            }
        }
        return CommonResult.success("恭喜，收藏成功，客官可以到个人中心查看");
    }

    /**
     * 获取标签对应的文章列表
     *
     * @param articlePage
     * @param articleTagId
     * @return
     */
    @Override
    public IPage<ArticleVo> tagArticleList(Page<ArticleVo> articlePage, String articleTagId) {
        return articleMapper.tagArticleList(articlePage, articleTagId);
    }

    @Override
    public CompletionStage<CommonResult> articleGoodIncrease(HttpServletRequest request, String articleId) {
        return CompletableFuture.supplyAsync(() -> {
            HttpSession session = request.getSession();
            if (Objects.nonNull(session.getAttribute("articleGoodTime"))) {
                return CommonResult.failed("客官！您已经点过啦");
            }

            Article article = articleService.getById(articleId);
            Integer articleGoodNumber = article.getArticleWatchTimes();
            ++articleGoodNumber;
            article.setArticleWatchTimes(articleGoodNumber);
            if (articleService.updateById(article)) {
                session.setAttribute("articleGoodTime", true);
                return CommonResult.success("点赞成功！");
            }
            return CommonResult.failed("点赞失败");
        });
    }

    @Override
    public CompletionStage<String> addNewArticle(User user) {
        return CompletableFuture.supplyAsync(() -> {


            /**
             * 校验user
             */

            Boolean flag = false;

            try {

                flag = userService

                        .confirmUserArticleRight(user)

                        // 使用toCompletableFuture方法来将CompletionStage转换为CompletableFuture。
                        .toCompletableFuture()

                        // 使用get方法来获取异步计算的结果
                        .get();

            } catch (InterruptedException | ExecutionException e) {

                // 异常处理
                e.printStackTrace();

            }

            //校验user成功后

            //todo false user 无权限page

            return flag == true ? "/user/editNewArticle" : "/error/4xx";

        });
    }

    @Override
    public CompletionStage<CommonResult> articlePublicsh(User user, ArticleDto articleDto) {

        return CompletableFuture.supplyAsync(() -> {
            if (ObjectUtil.isNull(user)) {
                return CommonResult.failed("用户登录过期,请重新登陆");
            }
            /**
             * 校验user
             */

            Boolean flag = false;

            try {

                flag = userService

                        .confirmUserArticleRight(user)

                        // 使用toCompletableFuture方法来将CompletionStage转换为CompletableFuture。
                        .toCompletableFuture()

                        // 使用get方法来获取异步计算的结果
                        .get();

            } catch (InterruptedException | ExecutionException e) {

                // 异常处理
                e.printStackTrace();

            }
            if (flag) {
                /**
                 *  校验user成功后
                 *  2、进行标签处理
                 *  1、生成 Article po对象
                 *  3、校验并添加到数据库中
                 */

                //标签处理
                String articleTagStr = articleDto.getArticleTagStr();
                if (StrUtil.isBlank(articleTagStr)) {

                    return CommonResult.failed("文章标签数据有误");

                } else {

                    List<String> tagList = new ArrayList<>(); // 准备一个List来保存分割后的字符串

                    // 使用 split 方法分割字符串，并遍历添加到 List中
                    for (String tag : articleTagStr.split(",")) {

                        tagList.add(tag);

                    }

                    QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();

                    // 迭代List的元素
                    for (String tag : tagList) {
                        /**
                         * 遍历查询是否已经存在
                         */
                        ArticleTag result = articleTagService.getOne(queryWrapper.eq("article_tag_name", tag));

                        if (result != null && !result.getArticleTagName().equals(tag)) {
                            // 如果标签名为空 ，则进行 新增标签 、 acticleid 关联处理

                            ArticleTag articleTag = new ArticleTag();

                            articleTag.setArticleTagId(IdUtil.simpleUUID());

                            articleTag.setArticleTagAddTime(new Date());

                            articleTag.setArticleTagName(tag);

                            boolean save = articleTagService.save(articleTag);

                            if (save) {

                                ArticleTagList articleTagList = new ArticleTagList();

                                articleTagList.setArticleId(IdUtil.simpleUUID());

                                articleTagList.setArticleId(articleDto.getArticleId());

                                articleTagList.setArticleTagId(articleTag.getArticleTagId());

                            } else {

                                return CommonResult.failed("错误处理");

                            }

                        }

                    }

                }

                Article article = new Article();

                BeanUtils.copyProperties(articleDto, article);

                article.setArticleHot(0);

                article.setArticleWatchTimes(0);

                article.setArticleFaveriteNo(0);

                article.setArticleFaverRate(0);

                article.setUserId(user.getUserId());

                boolean saveArticle = articleService.save(article);


                if (saveArticle){

                    return CommonResult.success("添加成功");

                }

                return CommonResult.failed("添加文章失败");

            }

            return CommonResult.failed("用户无权限");

        });

    }

}
