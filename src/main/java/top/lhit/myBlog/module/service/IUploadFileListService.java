package top.lhit.myBlog.module.service;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import top.lhit.myBlog.module.entity.UploadFileList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-01-03
 */
public interface IUploadFileListService extends IService<UploadFileList> {

    /**
     * 文件上传后获取文件路径
     * @param file
     * @return
     */
    String getUploadFileUrl(MultipartFile file);
}
