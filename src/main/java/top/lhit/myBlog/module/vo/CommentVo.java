package top.lhit.myBlog.module.vo;

import lombok.Data;

@Data
public class CommentVo {
    /**
     * 文章评论id
     */
    private String commentId;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 用户id（评论人）
     */
    private String userId;

    /**
     * 文章评论内容
     */
    private String commentContent;

    /**
     * 评论时间
     */
    private String commentTime;

    /**
     * 点赞次数
     */
    private Integer commentGoodNumber;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 是否点过赞，0未点赞，1已经点赞
     */
    private Integer isGoodComment;

}
