package com.jhzz.demo.biz.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jhzz.demo.biz.SignatureService;
import com.jhzz.demo.common.dto.WeixinTicketDTO;
import com.jhzz.demo.common.dto.WeixinTokenDTO;
import com.jhzz.demo.common.util.HttpUtils;
import com.jhzz.demo.common.util.Sign;

/**
 * 
 * @author sunjian 2016/12/1
 * 
 *         负责获取来自微信服务器的签名等信息
 *
 */
@Service
public class SignatureServiceImpl implements SignatureService {
	private static final Logger logger = LoggerFactory.getLogger(SignatureServiceImpl.class);
	/**
	 * @Value 自动注入配置文件中关于微信公众号配置的属性值
	 */

	@Value("${grant_type}")
	private String grant_type;

	@Value("${appid}")
	private String appid;

	@Value("${secret}")
	private String secret;

	// 获取token请求的url
	private static final String COMPANY_URL = "http://jihezhizao.com/views/index.html";

	// 获取token请求的url
	private static final String WEIXIN_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

	// 获取jsapiticket请求的ur
	private static final String WEIXIN_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	private static long ORIGIN_TIME = System.currentTimeMillis();// 服务器启动时间
	private static final long EXPIRE_TIME = 7200 * 1000; // token两个小时有效期
	private static Map<String, String> CACHE_MAP = new HashMap<>();// 缓存token和ticket的值

	static {
		logger.info("系统启动时间为:{}", ORIGIN_TIME);
	}

	/**
	 * 获取微信的签名信息等，系统将会自动缓存token和ticket的值
	 * 
	 * @return
	 */
	@Override
	public Map<String, String> getSignatureMap() {
		long now = System.currentTimeMillis();

		// 说明token过期了，清除缓存
		if (now - ORIGIN_TIME >= EXPIRE_TIME) {
			CACHE_MAP.clear();

			doGetTokenAndJsapiTicket();
			ORIGIN_TIME = now;
		}

		// 说明缓存还没有过期
		else {
			// 如果缓存中没有ticket值，那就获取一次Token以及对应的ticket
			if (CACHE_MAP.get("ticket") == null || "".equals(CACHE_MAP.get("ticket"))) {
				CACHE_MAP.clear();

				doGetTokenAndJsapiTicket();
				ORIGIN_TIME = now;
			}

		}

		// 到了这一步，ticket一定有值
		return Sign.sign(CACHE_MAP.get("ticket"), COMPANY_URL);
	}

	/**
	 * 获取token和jsapi_ticket，同时放入缓存中
	 * 
	 * @return
	 */
	private void doGetTokenAndJsapiTicket() {
		logger.info("现在向微信请求获取token和ticket，时间是:{}", System.currentTimeMillis() / 1000);

		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("grant_type", grant_type);
		parameterMap.put("appid", appid);
		parameterMap.put("secret", secret);

		String result = HttpUtils.get(WEIXIN_TOKEN_URL, parameterMap);
		// logger.info("微信服务器获取token返回的结果为:{}", result);

		if (result.contains("access_token")) {
			WeixinTokenDTO resultDTO = JSONObject.parseObject(result, WeixinTokenDTO.class);

			// 这一步先把token放入缓存
			if (resultDTO != null) {
				String token = resultDTO.getAccess_token();
				CACHE_MAP.put("access_token", token);

				// 然后根据token获取ticket,并且把ticket放入缓存
				getTicket(token);
			}

		}
	}

	/**
	 * 根据token获取ticket,并且把ticket放入缓存
	 * 
	 * @param token
	 */

	private void getTicket(String token) {
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("access_token", token);
		parameterMap.put("type", "jsapi");

		String ticketResult = HttpUtils.get(WEIXIN_TICKET_URL, parameterMap);
		// logger.info("微信服务器获取ticket返回的结果为:{}", ticketResult);
		WeixinTicketDTO resultDTO = JSONObject.parseObject(ticketResult, WeixinTicketDTO.class);

		if (resultDTO != null) {
			// 把ticket的值放入缓存
			CACHE_MAP.put("ticket", resultDTO.getTicket());

			logger.info("完成请求后获取token和ticket，它们的值是:{}", CACHE_MAP.toString());
		}

	}

}
