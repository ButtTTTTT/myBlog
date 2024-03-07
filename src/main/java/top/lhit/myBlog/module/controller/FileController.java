package top.lhit.myBlog.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.service.IMinioTemplate;

import java.util.concurrent.CompletionStage;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IMinioTemplate minioTemplate;

    @GetMapping("/index")
    public String getUploadPage() {
        return "/import/uploadPage";
    }

    @PostMapping("/upload")
    @ResponseBody
    public CompletionStage<CommonResult> uploadFile(@RequestParam("file") MultipartFile file) {
        return minioTemplate.putObjectForFile(file);
    }

}
