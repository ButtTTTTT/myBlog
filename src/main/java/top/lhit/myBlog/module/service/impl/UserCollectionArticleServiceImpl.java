package top.lhit.myBlog.module.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.module.entity.UserCollectionArticle;
import top.lhit.myBlog.module.mapper.UserCollectionArticleMapper;
import top.lhit.myBlog.module.service.IUserCollectionArticleService;

/**
 * <p>
 * 用户收藏的文章 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-12-29
 */
@Service
public class UserCollectionArticleServiceImpl extends ServiceImpl<UserCollectionArticleMapper, UserCollectionArticle> implements IUserCollectionArticleService {

}
