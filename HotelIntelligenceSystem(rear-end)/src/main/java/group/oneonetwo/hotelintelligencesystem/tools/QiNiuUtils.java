package group.oneonetwo.hotelintelligencesystem.tools;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;

public class QiNiuUtils {

    private static final Logger logger = LoggerFactory.getLogger(Object.class);

    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "XRe7_jvsIa4ufcG1tDJgTq17WHVVDA9lU8XtPUM4";
    private static final String SECRET_KEY = "gad061JM2XvnTc8r4dmSTfL0zTtHP80dat-2PwYD";

    // 要上传的空间名称
    private static final String BUCKETNAME = "oneonetwo-hotel";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    // 外链默认域名
    private static final String DOMAIN = "r0sxmzfq7.hn-bkt.clouddn.com";

    /**
     * 将图片上传到七牛云
     */
    public static String uploadQNImg(InputStream file, String key) {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Zone.zone2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            //    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(BUCKETNAME);
            try {
                Response response = uploadManager.put(file, createKey(key), upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = DOMAIN + "/" + putRet.key;
                // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                logger.error(r.toString());
//                System.err.println(r.toString());
                try {
                    logger.error(r.bodyString());
//                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    private static String createKey(String key) {
        String time = String.valueOf(System.currentTimeMillis());
        key += "_";
        key += time;
        return key;
    }
}

