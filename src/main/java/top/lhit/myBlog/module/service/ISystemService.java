package top.lhit.myBlog.module.service;

import org.springframework.ui.Model;

import java.util.concurrent.CompletionStage;

public interface ISystemService {
    String getSystemInfo(Model model);
}
