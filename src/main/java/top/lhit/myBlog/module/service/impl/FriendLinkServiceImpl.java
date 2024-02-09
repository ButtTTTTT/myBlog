package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.FriendLink;
import top.lhit.myBlog.module.mapper.FriendLinkMapper;
import top.lhit.myBlog.module.service.IFriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private IFriendLinkService linkService;

    /**
     * 添加或修改友链
     * @param link
     * @return
     */
    @Override
    public CompletionStage<CommonResult> addLink(FriendLink link) {
        return CompletableFuture.supplyAsync(()->{
            servletContext.removeAttribute("linkList");
            String linkId = link.getLinkId();
            if (StrUtil.isBlank(linkId)) {
                //添加友联
                link.setLinkAddTime(DateUtil.date());
                if (linkService.save(link)) {
                    return CommonResult.success("添加成功");
                }
                return CommonResult.failed("添加失败");
            }

            if (linkService.updateById(link)) {
                return CommonResult.success("更新成功");
            }
            return CommonResult.failed("更新失败");
        });
    }

    @Override
    public CompletionStage<CommonResult> delLink(String linkId) {
        return CompletableFuture.supplyAsync(()->{
            if (linkService.removeById(linkId)) {
                servletContext.removeAttribute("linkList");
                return CommonResult.success("删除成功");
            }
            return CommonResult.failed("删除失败");
        });
    }
}
