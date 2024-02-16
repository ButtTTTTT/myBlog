package top.lhit.myBlog.framework.schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.servlet.ServletContext;
@Component
public class FixedPrintTask {
    @Autowired
    private ServletContext servletContext;
    /**
     * 每天凌晨4点删除首页缓存
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void execute() {
        servletContext.removeAttribute("articleTypeList");
        servletContext.removeAttribute("articleHotList");
        servletContext.removeAttribute("articleTagList");
        servletContext.removeAttribute("adIndexList");
        servletContext.removeAttribute("friendLinkList");
    }
}