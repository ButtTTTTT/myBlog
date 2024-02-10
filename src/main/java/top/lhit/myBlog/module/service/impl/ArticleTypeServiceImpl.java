package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.aticle.ArticleTypeUpdateDto;
import top.lhit.myBlog.module.entity.Article;
import top.lhit.myBlog.module.entity.ArticleType;
import top.lhit.myBlog.module.mapper.ArticleTypeMapper;
import top.lhit.myBlog.module.service.IArticleService;
import top.lhit.myBlog.module.service.IArticleTypeService;
import top.lhit.myBlog.module.vo.ArticleTypeTreeVo;
import top.lhit.myBlog.module.vo.ArticleTypeVo;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * <p>
 * 文章分类i 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-12-21
 */
@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements IArticleTypeService {

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ArticleTypeMapper articleTypeMapper;
    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleTypeService articleTypeService;


    /**
     * 文章类型列表，包含文章数量
     *
     * @return
     */
    @Override
    public List<ArticleTypeVo> articleTypeList() {
        return articleTypeMapper.articleTypeList();
    }

    /**
     * 文章类型dml操作
     *
     * @param articleType
     * @return
     */
    @Override
    public CompletionStage<CommonResult> articleTypeAdd(ArticleType articleType) {
        return CompletableFuture.supplyAsync(() -> {
            if (StrUtil.isNotBlank(articleType.getArticleTypeParentId()) && StrUtil.isNotBlank(articleType.getArticleTypeId()) && articleType.getArticleTypeParentId().equals(articleType.getArticleTypeId())) {
                return CommonResult.failed("不能将自己分配到自己的目录下");
            }

            if (StrUtil.isBlank(articleType.getArticleTypeId())) {
                articleType.setArticleTypeAddTime(DateUtil.date());
                if (articleTypeService.save(articleType)) {

                    return CommonResult.success("添加成功");
                }
            }
            if (articleTypeService.updateById(articleType)) {
                return CommonResult.success("修改成功");
            }

            return CommonResult.failed("操作失败");
        });
    }

    /**
     * 文章类型删除
     *
     * @param articleTypeId
     * @return
     */
    @Override
    public CompletionStage<CommonResult> articleTypeDel(String articleTypeId) {

        return CompletableFuture.supplyAsync(() -> {
            if (articleService.count(Wrappers.<Article>lambdaQuery()
                    .eq(Article::getArticleTypeId, articleTypeId)) > 0) {
                return CommonResult.failed("请先删除该分类下的文章");
            }

            if (articleTypeService.count(Wrappers.<ArticleType>lambdaQuery().eq(ArticleType::getArticleTypeParentId, articleTypeId)) > 0) {
                return CommonResult.failed("请先删除下级分类");
            }

            if (articleTypeService.removeById(articleTypeId)) {
                servletContext.removeAttribute("articleTypeList");
                return CommonResult.success("删除成功");
            }
            return CommonResult.failed("删除失败");
        });
    }

    @Override
    public CompletionStage<CommonResult> articleTypeUpdate(ArticleTypeUpdateDto articleTypeUpdateDto) {
        return CompletableFuture.supplyAsync(() -> {
            ArticleType articleType = new ArticleType();
            BeanUtils.copyProperties(articleTypeUpdateDto, articleType);

            String articleTypeName = articleType.getArticleTypeName();
            Integer articleTypeSort = articleType.getArticleTypeSort();
            if (StrUtil.isBlank(articleTypeName)) {
                articleType.setArticleTypeName(null);
            }
            if (Objects.isNull(articleTypeSort)) {
                articleType.setArticleTypeSort(null);
            }
            if (StrUtil.isNotBlank(articleType.getArticleTypeParentId()) && StrUtil.isNotBlank(articleType.getArticleTypeId()) && articleType.getArticleTypeParentId().equals(articleType.getArticleTypeId())) {
                return CommonResult.failed("不能将自己分配到自己的目录下");
            }

            if (articleTypeService.updateById(articleType)) {
                servletContext.removeAttribute("articleTypeList");
                return CommonResult.success("添加成功");
            }
            return CommonResult.failed("添加失败");
        });
    }

    /**
     * 获取首页文章类型树形目录
     *
     * @param articleTypeParentId
     * @return
     */
    @Override
    public List<ArticleTypeTreeVo> getIndexArticleTypeList(String articleTypeParentId) {
        return articleTypeMapper.getIndexArticleTypeList(articleTypeParentId);
    }

    @Override
    public CompletionStage<CommonResult> getArticleTypeChild(String articleTypeId) {
        return CompletableFuture.supplyAsync(() -> {
            if (StrUtil.isBlank(articleTypeId)) {
                return CommonResult.failed("请选择一级分类");
            }

            List<ArticleType> articleTypeList = articleTypeService.list(Wrappers.<ArticleType>lambdaQuery()
                    .eq(ArticleType::getArticleTypeParentId, articleTypeId)
                    .select(ArticleType::getArticleTypeId, ArticleType::getArticleTypeName));

            return CommonResult.success(articleTypeList);
        });
    }
}
