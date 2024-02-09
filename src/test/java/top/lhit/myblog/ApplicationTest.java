package top.lhit.myblog;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lhit.myBlog.module.entity.*;
import top.lhit.myBlog.module.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
class ApplicationTest {
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleTagListService articleTagListService;
    @Autowired
    private IAdTypeService adTypeService;
    @Autowired
    private IAdService adService;

/**
 * 添加文章相关的模拟数据
 */
        @Test
        public void testArticleTag(){
            System.out.println(articleTagListService.count());
        }



    @Test
    public void addArticleData() {
        List<User> users = userService.list();


        List<ArticleType> articleTypeList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArticleType articleType = new ArticleType();
            articleType.setArticleTypeName("文章分类" + i);
            articleType.setArticleTypeSort(10);
            articleType.setArticleTypeAddTime(new Date());
            articleTypeList.add(articleType);
        }
        articleTypeService.saveBatch(articleTypeList);

        ArrayList<Article> arrayList = new ArrayList<>();
        for (ArticleType articleType : articleTypeList) {
            for (int i = 0; i < 6; i++) {
                Article article = new Article();
                article.setArticleId(MD5.create().toString());
                article.setUserId(users.get(ThreadLocalRandom.current().nextInt(users.size())).getUserId());
                article.setArticleTitle("文章标题：" + i);
                article.setArticleAddTime(DateUtil.date());
                article.setArticleContext("文章内容：" + ThreadLocalRandom.current().nextInt(1000));
                article.setArticleFaverRate(0);
                article.setArticleWatchTimes(0);
                article.setArticleFaveriteNo(0);
                arrayList.add(article);
            }
        }
        articleService.saveBatch(arrayList, 50);


        ArrayList<ArticleTag> articleTags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleTagName("文章标签：" + i);
            articleTag.setArticleTagAddTime(DateUtil.date());
            articleTags.add(articleTag);
        }
        articleTagService.saveBatch(articleTags, 50);


        ArrayList<ArticleTagList> articleTagLists = new ArrayList<>();
        for (ArticleTag articleTag : articleTags) {
            for (int i = 0; i < 3; i++) {
                ArticleTagList articleTagList = new ArticleTagList();
                articleTagList.setArticleId(arrayList.get(ThreadLocalRandom.current().nextInt(arrayList.size())).getArticleId());
                articleTagList.setArticleTagId(articleTag.getArticleTagId());
                articleTagLists.add(articleTagList);
            }
        }
        articleTagListService.saveBatch(articleTagLists, 50);
    }
}
