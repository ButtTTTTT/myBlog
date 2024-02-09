package top.lhit.myBlog.module.service;

import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.FriendLink;
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
public interface IFriendLinkService extends IService<FriendLink> {

    CompletionStage<CommonResult> addLink(FriendLink friendLink);

    CompletionStage<CommonResult> delLink(String linkId);
}
