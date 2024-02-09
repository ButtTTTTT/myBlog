package top.lhit.myBlog.module.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVo {


    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章分类id
     */
    private String articleTypeId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章封面url
     */
    private String articleCoverUrl;

    /**
     * 文章添加时间
     */
    private Date articleAddTime;

    /**
     * 是否是热门文章 0否，1是
     */
    private Integer articleHot;

    /**
     * 点赞次数
     */
    private Integer articleFaveriteNo;

    /**
     * 观看次数
     */
    private Integer articleWatchTimes;

    /**
     * 收藏次数
     */
    private Integer articleFaverRate;

    /**
     * 文章类型名称
     */
    private String articleTypeName;

    /**
     * 文章内容
     */
    private String articleContext;


}
