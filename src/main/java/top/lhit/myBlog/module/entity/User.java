package top.lhit.myBlog.module.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 注册时间
     */
    private Date userRegisterTime;
    /**
     * 是否冻结，1正常，0冻结（冻结后无法登陆）
     */
    private Integer userStatus;
    /**
     * 是否可以发布文章 0不能，1可以发布
     */
    private Integer userPublishable;
}
