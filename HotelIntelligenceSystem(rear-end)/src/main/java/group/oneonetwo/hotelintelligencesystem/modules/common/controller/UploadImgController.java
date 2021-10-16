package group.oneonetwo.hotelintelligencesystem.modules.common.controller;

import group.oneonetwo.hotelintelligencesystem.tools.QiNiuUtils;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 文
 * @description 上传图片操作层
 */
@RestController
@RequestMapping("upload/img")
public class UploadImgController {

    @PostMapping()
    public Reply upload(MultipartFile img,String key) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(img.getBytes());
        String url = QiNiuUtils.uploadQNImg(inputStream, key);
        return Reply.success(url);
    }

}
