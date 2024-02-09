package top.lhit.myBlog.module.service.impl;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import top.lhit.myBlog.common.utils.CommonPage;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.user.UserDto;
import top.lhit.myBlog.module.dto.user.UserListPageDto;
import top.lhit.myBlog.module.entity.Article;
import top.lhit.myBlog.module.entity.User;
import top.lhit.myBlog.module.mapper.UserMapper;
import top.lhit.myBlog.module.service.IArticleService;
import top.lhit.myBlog.module.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IArticleService iArticleService;

    @Override
    public CompletionStage<CommonResult> postUserBan(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            if (StrUtil.isBlank(userId)) {
                return CommonResult.failed("参数有误，请重试");
            }
            User user = userService.getById(userId);
            if (user.getUserStatus() == 0 || ObjectUtil.isNull(user)) {
                return CommonResult.failed("账户不存在或已停用");
            } else {
                user.setUserStatus(0);
                if (userService.updateById(user)) {
                    return CommonResult.success("操作成功");
                }
                return CommonResult.success("操作失败");
            }
        });
    }
    @Override
    public CompletionStage<CommonResult> postUserUpdate(UserDto userDto) {
        return CompletableFuture.supplyAsync(() -> {

            User user = userService.getById(userDto.getUserId());
            if (!ObjectUtil.isNull(user)) {

                if (StrUtil.isBlank(userDto.getUserPassword())) {
                    userDto.setUserPassword(user.getUserPassword());
                    BeanUtils.copyProperties(userDto, user);
                } else {
                    //用户密码 = md5 注册时间+用户明文密码
                    BeanUtils.copyProperties(userDto, user);
                    user.setUserPassword(SecureUtil.md5(user.getUserRegisterTime() + userDto.getUserPassword()));
                }
                if (userService.updateById(user)) {
                    return CommonResult.success("操作成功");
                }
            }
            return CommonResult.failed("操作有误");
        });
    }

    @Override
    public CompletionStage<CommonResult> delUserDel(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            if (StrUtil.isBlank(userId)) {
                return CommonResult.failed("参数有误，请重试");
            }
            if (iArticleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getUserId, userId)) > 0) {
                return CommonResult.failed("发布过文章的用户 无法删除 ！！！ ");
            }
            if (userService.removeById(userId)) {
                return CommonResult.success("操作成功");
            }
            return CommonResult.failed("操作有误");
        });
    }

    @Override
    public CompletionStage<String> getUserList(UserListPageDto userListPageDto, Model model) {
        return CompletableFuture.supplyAsync(()->{
            /**
             *获取前端发送的参数
             */
            Integer pageNumber = userListPageDto.getPageNumber();

            Integer pageSize = userListPageDto.getPageSize();

            String userName = userListPageDto.getUserName();
            /**
             *1创建分页对象
             *2根据用户注册时间创建wrapper对象
             *3创建分页后对象
             *4将参数分页后对象传给前端
             */
            //1
            Page<User> userPage = new Page<>(pageNumber, pageSize);
            //2
            LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery().orderByDesc(User::getUserRegisterTime);
            //做模糊查询
            if (StrUtil.isNotBlank(userName)) {
                //查询条件为  column 1   like 2
                userLambdaQueryWrapper.like(User::getUserName, userName);

                //把查到的信息回传
                model.addAttribute("userName", userName);

            }
            //3
            IPage<User> page = userService.page(userPage, userLambdaQueryWrapper);
            //4
            model.addAttribute("userPage", CommonPage.restPage(page));
            System.out.println("接口被请求了");
            return "/unknn/userList";
        });
    }
}
