package top.lhit.myBlog.module.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * 用户id
     */
    @NotBlank(message = "用户id 不能为空值")
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
     * 用户状态 ： 1正常 0停用 2 删除  停用无法登陆
     */
    private Integer userStatus;

    /**
     * 用户是否能发布文章 ： 1 能 0 不能
     */

    private Integer userPublishable ;




}
