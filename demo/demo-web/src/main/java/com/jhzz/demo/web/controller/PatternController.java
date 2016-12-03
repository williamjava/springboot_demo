package com.jhzz.demo.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jhzz.demo.biz.PatternService;
import com.jhzz.demo.biz.vo.PatternVo;
import com.jhzz.demo.common.util.Result;
import com.jhzz.demo.dal.mapper.PatternMapper;

/**
 * 选款控制器
 * 
 * @author wuhoujian
 */
@Controller
public class PatternController {
	Logger logger = LoggerFactory.getLogger(PatternController.class);

	@Resource
	private PatternService patternService;
	@Resource
	private PatternMapper patternMapper;

	/**
	 * 
	 * uploadPattern:(上传款式). <br/>
	 * 
	 * @author wuhoujian
	 * @param map
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping("/uploadPattern")
	@ResponseBody
	public Result<String> uploadPattern(
			@RequestParam("file") List<MultipartFile> file, @RequestParam("patternName") String patternName, ModelMap map) {
		Result<String> result = null;
		String msg = "";
		try {
			msg = patternService.uploadByBytes(file, patternName);
		} catch (Exception e) {
			logger.info(" PatternController_uploadPattern_error:{}", e);
		} finally {
			result = Result.generateSuccess(msg);
		}

		return result;
	}

	/**
	 * 
	 * listPatterns:查看所有款式信息. <br/>
	 * 
	 * @author wuhoujian
	 * @param map
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping("/listPatterns")
	@ResponseBody
	public Result<List<PatternVo>> listPatterns(ModelMap map) {
		Result<List<PatternVo>> result = null;
		try {
			result = Result.generateSuccess(patternService.selectPatternList());
		} catch (Exception e) {
			logger.info(" PatternController_listPatterns_error:{}", e);
		}
		return result;
	}

	/**
	 * 
	 * showPatterndetail:查看一个具体款式详情信息 <br/>
	 * 
	 * @author wuhoujian
	 * @param id
	 * @param map
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping("/showPatternDetail")
	@ResponseBody
	public Result<PatternVo> showPatternDetail(@RequestParam("id") Long id, ModelMap map) {
		Result<PatternVo> result = null;
		try {
			result = Result.generateSuccess(patternService.selectPatternDetailById(id));
		} catch (Exception e) {
			logger.info(" PatternController_showPatterndetail_error:{}", e);
		}
		return result;
	}
}
