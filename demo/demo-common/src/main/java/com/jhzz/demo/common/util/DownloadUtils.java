package com.jhzz.demo.common.util;
import com.jhzz.demo.common.constant.Constant;
import com.qiniu.util.Auth;

public class DownloadUtils {
    //密钥配置
    private static Auth auth = Auth.create(Constant.ACCESS_KEY, Constant.SECRET_KEY);

    public static String download(String fileName) {
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(Constant.DOWNLOAD_URL + fileName, 3600);
        return downloadRUL;
    }
}
