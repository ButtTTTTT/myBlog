package top.lhit.myBlog.module.service.impl;
import cn.hutool.system.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import top.lhit.myBlog.module.service.*;
@Service
public class SystemServiceImpl implements ISystemService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleTypeService iArticleTypeService;
    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private IArticleTagListService iArticleTagListService;
    @Override
    public String getSystemInfo(Model model) {

        return "/unknn/index";
    }
}
