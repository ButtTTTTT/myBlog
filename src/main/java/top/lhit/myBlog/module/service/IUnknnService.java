package top.lhit.myBlog.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.Unknn;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletionStage;

public interface IUnknnService extends IService<Unknn> {
    CompletionStage<CommonResult> updatePassword(HttpServletRequest request, String newPassword);

    CompletionStage<CommonResult> confirmUnknn(HttpServletRequest request, String unknnName, String unknnPassword, String verifyCode);
}
