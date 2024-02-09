package top.lhit.myBlog.module.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论回复
 * </p>
 *
 * @author jobob
 * @since 2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论回复id
     */
    @TableId(value = "comment_reply_id")
    private String commentReplyId;

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 回复评论的人id
     */
    private String replyUserId;

    /**
     * 评论回复内容
     */
    private String replyContent;

    /**
     * 继续回复的人id
     */
    private String secondlyUserId;

    /**
     * 评论回复的时间
     */
    private LocalDateTime commentReplyTime;


}
