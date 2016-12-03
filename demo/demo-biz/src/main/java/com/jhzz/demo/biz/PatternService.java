package com.jhzz.demo.biz;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jhzz.demo.biz.vo.PatternVo;
import com.jhzz.demo.dal.model.Pattern;
import com.jhzz.demo.dal.model.PatternDetail;

public interface PatternService {
	String uploadLocal(List<String> filePaths, String patternName) throws Exception;// 上传文件到七牛云
	
	String uploadByFilePath(List<File> fileList, String patternName) throws Exception;// 上传文件到七牛云
	
	String upload(MultipartFile file, String patternName) throws Exception;// 上传文件到七牛云
	
	String uploadByBytes(List<MultipartFile> fileList, String patternName) throws Exception;// 上传文件到七牛云

	Pattern selectPatternByPatternName(String patternName) throws Exception;// 根据款式名称查询款式

	List<PatternVo> selectPatternList() throws Exception;// 查询所有款式信息

	PatternVo selectPatternDetailById(Long id) throws Exception;// 查询

	PatternDetail selectPatternDetailByPatternIdAndUrl(Long patternId, String url);// 根据款式ID和某一个款式图片名称查询某一款具体信息
}