package top.lhit.myBlog.module.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import top.lhit.myBlog.module.entity.Comment;
import top.lhit.myBlog.module.mapper.CommentMapper;
import top.lhit.myBlog.module.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.module.vo.CommentVo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 文章评论列表
     * @param articleId
     * @return
     */
    @Override
    public IPage<CommentVo> getArticleCommentList(Page<CommentVo> commentVoPage, String articleId) {
        return commentMapper.getArticleCommentList(commentVoPage,articleId);
    }
}
