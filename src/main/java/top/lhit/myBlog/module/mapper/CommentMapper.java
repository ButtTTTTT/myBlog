package top.lhit.myBlog.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lhit.myBlog.module.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lhit.myBlog.module.vo.CommentVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<CommentVo> getArticleCommentList(Page<CommentVo> commentVoPage, @Param("articleId") String articleId);
}
