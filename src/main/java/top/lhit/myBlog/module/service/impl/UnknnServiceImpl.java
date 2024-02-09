package top.lhit.myBlog.module.service.impl;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.Unknn;
import top.lhit.myBlog.module.mapper.UnknnMapper;
import top.lhit.myBlog.module.service.IUnknnService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class UnknnServiceImpl extends ServiceImpl<UnknnMapper, Unknn> implements IUnknnService {
    @Autowired
    private IUnknnService unknnService;
    @Override
    public CompletionStage<CommonResult> updatePassword(HttpServletRequest request, String newPassword) {
        return CompletableFuture.supplyAsync(()->{
            if (StrUtil.isNotBlank(newPassword)) {
                Unknn unKnn = unknnService.getOne(null, false);
                if (Objects.nonNull(unKnn)) {
                    unKnn.setUnknnPassword(SecureUtil.md5(unKnn.getUnknnName() + newPassword));
                    if (unknnService.updateById(unKnn)) {
                        request.getSession().setAttribute("admin", unKnn);
                        return CommonResult.success("修改成功");
                    }
                }
            }
            return CommonResult.failed("修改失败");
        });
    }

    @Override
    public CompletionStage<CommonResult> confirmUnknn(HttpServletRequest request, String unknnName, String unknnPassword, String verifyCode) {
        return CompletableFuture.supplyAsync(()->{
            HttpSession session = request.getSession();
            if (StrUtil.isBlank(verifyCode) || !verifyCode.equals(session.getAttribute("circleCaptchaCode"))) {
                session.removeAttribute("circleCaptchaCode");
                return CommonResult.failed("验证码不正确");
            }
            Unknn admin = unknnService.getOne(Wrappers.<Unknn>lambdaQuery()
                    .eq(Unknn::getUnknnName, unknnName)
                    .eq(Unknn::getUnknnPassword, SecureUtil.md5(unknnName + unknnPassword)), false);
            if (Objects.isNull(admin)) {
                session.removeAttribute("circleCaptchaCode");
                return CommonResult.failed("用户名或者密码不正确");
            }
            session.setAttribute("unknn", admin);
            return CommonResult.success("登录成功");
        });
    }
}
