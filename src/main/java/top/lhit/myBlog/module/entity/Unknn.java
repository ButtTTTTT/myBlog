package top.lhit.myBlog.module.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class  Unknn {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId
    private String unknnId;

    /**
     * 管理员名称
     */
    private String unknnName;

    /**
     * 管理员密码
     */
    private String unknnPassword;
}
