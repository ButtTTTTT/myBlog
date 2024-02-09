package top.lhit.myBlog.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import top.lhit.myBlog.module.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lhit.myBlog.module.vo.ArticleVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<ArticleVo> articleList(IPage<ArticleVo> articlePage, String articleTitle, String userId);

    IPage<ArticleVo> articleListView(Page<ArticleVo> articlePage, String articleTitle, String articleTypeId);

    ArticleVo getArticle(String articleId);

    IPage<ArticleVo> tagArticleList(Page<ArticleVo> articlePage, String articleTagId);

    List<ArticleVo> getIndexArticleList();
}
