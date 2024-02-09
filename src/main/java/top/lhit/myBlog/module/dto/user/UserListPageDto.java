package top.lhit.myBlog.module.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.lhit.myBlog.module.dto.base.BasePageDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserListPageDto extends BasePageDto {
    /**
     * 用户名
     */
    private String userName;



}
