package com.jhzz.demo.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhzz.demo.biz.SignatureService;
import com.jhzz.demo.common.util.Result;

/**
 * 
 * @author sunjian 2016/12/01 获取微信token，签名信息等的控制器
 *
 */
@Controller
public class WeixinController {
	private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);
	@Autowired
	private SignatureService signatureService;

	/**
	 * 获取微信token，签名信息
	 * 
	 * @return
	 */
	@RequestMapping("/getSign")
	@ResponseBody
	public Result<Map<String, String>> getSign() {
		try {
			Map<String, String> result = signatureService.getSignatureMap();

			//logger.info("map:{}", result.toString());

			return Result.generateSuccess(result);
		}

		catch (Exception e) {
			logger.error("获取微信的token出现异常", e);

			return Result.generateFail(null);
		}

	}

	/**
	 * 测试
	 * 
	 * @return
	 */

	@RequestMapping("/helloabc")
	@ResponseBody
	public String hello() {
		return "welcome";

	}

}
