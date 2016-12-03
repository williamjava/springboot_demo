package com.jhzz.demo.common.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhzz.demo.common.constant.Constant;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class UploadUtils {
	public final static Logger logger = LoggerFactory.getLogger(UploadUtils.class);
	
	// 密钥配置
	public final static Auth auth = Auth.create(Constant.ACCESS_KEY, Constant.SECRET_KEY);

	// 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
	public final static Zone z = Zone.autoZone();
	public final static Configuration c = new Configuration(z);

	// 创建上传对象
	public final static UploadManager uploadManager = new UploadManager(c);

	// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
	public static String getUpToken() {
		return auth.uploadToken(Constant.BUCKET_NAME);
	}

	public static boolean uploadByFilePath(String filePath, String key) throws IOException {
		boolean boSuccess = true;
		try {
			// 调用put方法上传
			uploadManager.put(filePath, key, getUpToken());
		} catch (QiniuException e) {
			Response r = e.response;
			boSuccess = false;
			logger.info(" UploadDemo_upload_error:{},Response_body_text:{}",r.toString(),r.bodyString());
		}
		return boSuccess;
	}
	
	public static boolean uploadByBytes(byte [] fileTyes) throws IOException {
		boolean boSuccess = true;
		try {
			// 调用put方法上传
			uploadManager.put(fileTyes, null, getUpToken());
		} catch (QiniuException e) {
			Response r = e.response;
			boSuccess = false;
			logger.info(" UploadDemo_upload_error:{},Response_body_text:{}",r.toString(),r.bodyString());
		}
		return boSuccess;
	}
	
	public static boolean uploadByBytes(byte [] fileTyes,String key) throws IOException {
		boolean boSuccess = true;
		try {
			// 调用put方法上传
			uploadManager.put(fileTyes, key, getUpToken());
		} catch (QiniuException e) {
			Response r = e.response;
			boSuccess = false;
			logger.info(" UploadDemo_upload_error:{},Response_body_text:{}",r.toString(),r.bodyString());
		}
		return boSuccess;
	}
}
