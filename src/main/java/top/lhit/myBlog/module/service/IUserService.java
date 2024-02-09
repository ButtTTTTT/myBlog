package top.lhit.myBlog.module.service;
import org.springframework.ui.Model;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.user.UserDto;
import top.lhit.myBlog.module.dto.user.UserListPageDto;
import top.lhit.myBlog.module.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.concurrent.CompletionStage;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
public interface IUserService extends IService<User> {
    CompletionStage<CommonResult> postUserBan(String userId);
    CompletionStage<CommonResult> postUserUpdate(UserDto userDto);
    CompletionStage<CommonResult> delUserDel(String userId);

    CompletionStage<String> getUserList(UserListPageDto userListPageDto, Model model);
}
