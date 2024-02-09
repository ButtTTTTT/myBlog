package top.lhit.myBlog.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.lhit.myBlog.module.entity.UserCollectionArticle;

/**
 * <p>
 * 用户收藏的文章 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-12-29
 */
@Mapper
public interface UserCollectionArticleMapper extends BaseMapper<UserCollectionArticle> {

}
