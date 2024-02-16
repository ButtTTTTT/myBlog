package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
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
import top.lhit.myBlog.module.dto.user.UserInfoDto;
import top.lhit.myBlog.module.dto.user.UserListPageDto;
import top.lhit.myBlog.module.entity.Article;
import top.lhit.myBlog.module.entity.Unknn;
import top.lhit.myBlog.module.entity.User;
import top.lhit.myBlog.module.mapper.UserMapper;
import top.lhit.myBlog.module.service.IArticleService;
import top.lhit.myBlog.module.service.IUnknnService;
import top.lhit.myBlog.module.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
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

    @Autowired
    private IUnknnService unknnService;

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
        return CompletableFuture.supplyAsync(() -> {
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

    @Override
    public CompletionStage<CommonResult> userRegist(HttpServletRequest request, UserInfoDto userInfoDto) {
        return CompletableFuture.supplyAsync(() -> {
            HttpSession session = request.getSession();
            String verifyCode = userInfoDto.getVerifyCode();
            if (StrUtil.isBlank(verifyCode) || !verifyCode.equals(session.getAttribute("circleCaptchaCode"))) {
                session.removeAttribute("circleCaptchaCode");
                return CommonResult.failed("验证码不正确");
            }
            //用户名和密码是否相同
            if (userInfoDto.getUserName().equals(userInfoDto.getUserPassword())) {
                session.removeAttribute("circleCaptchaCode");
                return CommonResult.failed("用户名和密码不能相同哦");
            }

            //用户名是否已经被使用
            if (userService.count(Wrappers.<User>lambdaQuery().eq(User::getUserName, userInfoDto.getUserName())) > 0) {
                session.removeAttribute("circleCaptchaCode");
                return CommonResult.failed("客官！该用户名已经存在哦");
            }

            User user = new User();
            BeanUtils.copyProperties(userInfoDto, user);
            user.setUserId(IdUtil.simpleUUID());
            user.setUserRegisterTime(DateUtil.date());
            user.setUserPassword(SecureUtil.md5(user.getUserId() + user.getUserPassword()));
            user.setUserStatus(1);
            user.setUserPublishable(1);
            if (userService.save(user)) {
                return CommonResult.success("注册成功");
            }

            return CommonResult.failed("哎呀！注册失败啦，刷新页面再试一次哦~");
        });
    }

    @Override
    public CompletionStage<CommonResult> userLogin(HttpServletRequest request, UserInfoDto userInfoDto) {
        return CompletableFuture.supplyAsync(() -> {

            /**
             * 验证是否是管理员账号：
             * 1、获取用户用户名
             * 2、获取管理员用户名 查找是否匹配
             * 3、验证密码 是否和该管理员匹配
             * 4、正确 重定向到管理页面 不正确 再判断 是否匹配普通用户 匹配重定向到普通用户
             */

            String userName = userInfoDto.getUserName();

            String userPassword = userInfoDto.getUserPassword();

            String verifyCode = userInfoDto.getVerifyCode();

            boolean flag = unknnService
                    .list()
                    .stream()
                    .map(Unknn::getUnknnName)
                    .anyMatch(u -> u.equals(userName));

            HttpSession session = request.getSession();

            if (flag) {
                /**
                 * 执行管理员登陆逻辑
                 */

            } else {

                if (StrUtil.isBlank(verifyCode) || !verifyCode.equals(session.getAttribute("circleCaptchaCode"))) {
                    session.removeAttribute("circleCaptchaCode");
                    return CommonResult.failed("验证码不正确");
                }

                //用户名和密码是否相同
                if (userInfoDto.getUserName().equals(userInfoDto.getUserPassword())) {
                    session.removeAttribute("circleCaptchaCode");
                    return CommonResult.failed("用户名和密码不能相同哦");
                }

                //获取用户
                User userDb = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, userInfoDto.getUserName()), false);
                if (Objects.isNull(userDb)) {
                    session.removeAttribute("circleCaptchaCode");
                    return CommonResult.failed("用户名错误");
                }
                if (Objects.nonNull(userDb.getUserStatus()) && userDb.getUserStatus() == 0) {
                    session.removeAttribute("circleCaptchaCode");
                    return CommonResult.failed("您的账户已经被冻结，暂无法登录，请联系管理员");
                }

                if (!SecureUtil.md5(userDb.getUserId() + userInfoDto.getUserPassword()).equals(userDb.getUserPassword())) {
                    session.removeAttribute("circleCaptchaCode");
                    return CommonResult.failed("密码错误");
                }
                session.setAttribute("user", userDb);
                return CommonResult.success("登录成功");
            }
            return CommonResult.failed("错误Error");
        });
    }
}
