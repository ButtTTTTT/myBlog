package top.lhit.myBlog.module.service;

import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.CompletionStage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
public interface IArticleTagService extends IService<ArticleTag> {

    CompletionStage<CommonResult> articleTagAdd(ArticleTag articleTag);

    CompletionStage<CommonResult> articleTagDel(String articleTagId);
}
