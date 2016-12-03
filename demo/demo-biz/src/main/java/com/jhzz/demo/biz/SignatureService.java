package com.jhzz.demo.biz;

import java.util.Map;
/**
 * 
 * @author sunjian 2016/12/1
 * 
 *         负责获取来自微信服务器的签名等信息
 *
 */
public interface SignatureService {
	/**
	 * 获取微信的签名信息等，系统将会自动缓存token和ticket的值
	 * 
	 * @return
	 */
	Map<String, String> getSignatureMap();

}
