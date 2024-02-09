package top.lhit.myBlog.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.lhit.myBlog.module.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lhit.myBlog.module.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
public interface ICommentService extends IService<Comment> {

    IPage<CommentVo> getArticleCommentList(Page<CommentVo> commentVoPage, String articleId);

}
