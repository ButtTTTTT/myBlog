package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.ArticleTag;
import top.lhit.myBlog.module.entity.ArticleTagList;
import top.lhit.myBlog.module.mapper.ArticleTagMapper;
import top.lhit.myBlog.module.service.IArticleTagListService;
import top.lhit.myBlog.module.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private IArticleTagListService articleTagListService;
    @Override
    public CompletionStage<CommonResult> articleTagAdd(ArticleTag articleTag) {
        return CompletableFuture.supplyAsync(()->{

            servletContext.removeAttribute("articleTagList");

            String articleTagId = articleTag.getArticleTagId();

            if (StrUtil.isNotBlank(articleTagId)) {

                if (articleTagService.updateById(articleTag)) {

                    return CommonResult.success("修改成功");

                }

                return CommonResult.failed("修改失败");

            }

            articleTag.setArticleTagAddTime(DateUtil.date());

            if (articleTagService.save(articleTag)) {

                return CommonResult.success("文章标签添加成功");

            }

            return CommonResult.failed("文章标签添加失败");

        });
    }

    @Override
    public CompletionStage<CommonResult> articleTagDel(String articleTagId) {
        return CompletableFuture.supplyAsync(()->{
            if (StrUtil.isBlank(articleTagId)) {
                return CommonResult.failed("删除失败，没有获取到文章标签id");
            }

            if (articleTagListService.count(Wrappers.<ArticleTagList>lambdaQuery()
                    .eq(ArticleTagList::getArticleTagId, articleTagId)) > 0) {
                return CommonResult.failed("该问标签已经被使用，无法删除，请先删除和文章的关联");
            }

            if (articleTagService.removeById(articleTagId)) {
                servletContext.removeAttribute("articleTagList");
                return CommonResult.success("删除标签成功");
            }
            return CommonResult.failed("删除标签失败");
        });
    }
}
