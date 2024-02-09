package top.lhit.myBlog.module.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserInfoDto {
    @NotBlank(message = "客观 请填写用户名哦！")
    @Size(max = 25,min = 3,message = "用户名长度在 3-25之间")
    private String userName;
    @NotBlank(message = "客观 请填写密码哦！")
    @Size(max = 25,min = 3,message = "密码长度在 3-25之间")
    private String userPassword;
    @NotBlank(message = "客观 请填写验证码哦！")
    private String verifyCode;
}
