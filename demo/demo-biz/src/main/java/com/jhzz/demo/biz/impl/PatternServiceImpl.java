package com.jhzz.demo.biz.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jhzz.demo.biz.PatternService;
import com.jhzz.demo.biz.vo.PatternVo;
import com.jhzz.demo.common.util.DownloadUtils;
import com.jhzz.demo.common.util.UploadUtils;
import com.jhzz.demo.dal.mapper.PatternDetailMapper;
import com.jhzz.demo.dal.mapper.PatternMapper;
import com.jhzz.demo.dal.model.Pattern;
import com.jhzz.demo.dal.model.PatternDetail;
import com.jhzz.demo.dal.model.PatternDetailExample;
import com.jhzz.demo.dal.model.PatternExample;

@Service
public class PatternServiceImpl implements PatternService {

	@Autowired
	private PatternMapper patternMapper;
	@Autowired
	private PatternDetailMapper patternDetailMapper;

	/**
	 * TODO 上传款式到七牛云
	 * 
	 * @see com.jhzz.demo.biz.PatternService#upload(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String uploadLocal(List<String> filePaths, String patternName) throws Exception {
		StringBuffer resultSb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(filePaths)) {
			Pattern checkPattern = this.selectPatternByPatternName(patternName);
			for (String filePath : filePaths) {
				// 上传到七牛后保存的文件名
				String key = filePath.substring(filePath.lastIndexOf('\\') + 1);
				boolean boSuccess = UploadUtils.uploadByFilePath(filePath, key);

				// 上传成功，保存上传的款式信息到款式表和款式信息详情表
				if (boSuccess) {
					resultSb.append(filePath).append("上传成功").append("<br>");
					/* 保存之前先校验是否已经上传过 */
					if (checkPattern == null) {
						Pattern pattern = new Pattern();
						pattern.setName(patternName);
						pattern.setCreatedAt(new Date());
						pattern.setUpdatedAt(new Date());
						patternMapper.insert(pattern);

						this.savePatternDetail(this.selectPatternByPatternName(patternName), key);
					} else {
						/* 已经上传过的修改更新时间 */
						checkPattern.setUpdatedAt(new Date());
						patternMapper.updateByPrimaryKey(checkPattern);

						PatternDetail modifyPatternDetail = this
								.selectPatternDetailByPatternIdAndUrl(checkPattern.getId(), key);
						if (modifyPatternDetail != null) {
							modifyPatternDetail.setUpdatedAt(new Date());
							patternDetailMapper.updateByPrimaryKey(modifyPatternDetail);
						} else {
							this.savePatternDetail(checkPattern, key);
						}
					}
				} else {
					resultSb.append(filePath).append("上传失败").append("<br>");
				}
			}
		}

		return resultSb.toString();
	}
	
	/**
	 * TODO 上传款式到七牛云
	 * 
	 * @see com.jhzz.demo.biz.PatternService#upload(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String uploadByFilePath(List<File> fileList, String patternName) throws Exception {
		StringBuffer resultSb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(fileList)) {
			Pattern checkPattern = this.selectPatternByPatternName(patternName);
			for (File file : fileList) {
				// 上传到七牛后保存的文件名
				String key = file.getPath().substring(file.getPath().lastIndexOf('\\') + 1);
				boolean boSuccess = UploadUtils.uploadByFilePath(file.getPath(), key);

				// 上传成功，保存上传的款式信息到款式表和款式信息详情表
				if (boSuccess) {
					resultSb.append(file.getName()).append("上传成功").append("<br>");
					/* 保存之前先校验是否已经上传过 */
					if (checkPattern == null) {
						Pattern pattern = new Pattern();
						pattern.setName(patternName);
						pattern.setCreatedAt(new Date());
						pattern.setUpdatedAt(new Date());
						patternMapper.insert(pattern);

						this.savePatternDetail(this.selectPatternByPatternName(patternName), file.getName());
					} else {
						/* 已经上传过的修改更新时间 */
						checkPattern.setUpdatedAt(new Date());
						patternMapper.updateByPrimaryKey(checkPattern);

						PatternDetail modifyPatternDetail = this
								.selectPatternDetailByPatternIdAndUrl(checkPattern.getId(), file.getName());
						if (modifyPatternDetail != null) {
							modifyPatternDetail.setUpdatedAt(new Date());
							patternDetailMapper.updateByPrimaryKey(modifyPatternDetail);
						} else {
							this.savePatternDetail(checkPattern, file.getName());
						}
					}
				} else {
					resultSb.append(file.getName()).append("上传失败").append("<br>");
				}
			}
		}

		return resultSb.toString();
	}
	
	/**
	 * TODO 上传款式到七牛云
	 * 
	 * @see com.jhzz.demo.biz.PatternService#upload(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String upload(MultipartFile file, String patternName) throws Exception {
		StringBuffer resultSb = new StringBuffer();

		Pattern checkPattern = this.selectPatternByPatternName(patternName);

		boolean boSuccess = UploadUtils.uploadByBytes(file.getBytes(),file.getOriginalFilename());

		// 上传成功，保存上传的款式信息到款式表和款式信息详情表
		if (boSuccess) {
			resultSb.append(file.getName()).append("上传成功").append("<br>");
			/* 保存之前先校验是否已经上传过 */
			if (checkPattern == null) {
				Pattern pattern = new Pattern();
				pattern.setName(patternName);
				pattern.setCreatedAt(new Date());
				pattern.setUpdatedAt(new Date());
				patternMapper.insert(pattern);

				this.savePatternDetail(this.selectPatternByPatternName(patternName), file.getName());
			} else {
				/* 已经上传过的修改更新时间 */
				checkPattern.setUpdatedAt(new Date());
				patternMapper.updateByPrimaryKey(checkPattern);

				PatternDetail modifyPatternDetail = this
						.selectPatternDetailByPatternIdAndUrl(checkPattern.getId(), file.getName());
				if (modifyPatternDetail != null) {
					modifyPatternDetail.setUpdatedAt(new Date());
					patternDetailMapper.updateByPrimaryKey(modifyPatternDetail);
				} else {
					this.savePatternDetail(checkPattern, file.getName());
				}
			}
		} else {
			resultSb.append(file.getName()).append("上传失败").append("<br>");
		}
	
	

		return resultSb.toString();
	}
	
	/**
	 * TODO 上传款式到七牛云
	 * 
	 * @see com.jhzz.demo.biz.PatternService#upload(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String uploadByBytes(List<MultipartFile> fileList, String patternName) throws Exception {
		StringBuffer resultSb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(fileList)) {
			Pattern checkPattern = this.selectPatternByPatternName(patternName);
			if (checkPattern == null) {
				Pattern pattern = new Pattern();
				pattern.setName(patternName);
				pattern.setCreatedAt(new Date());
				pattern.setUpdatedAt(new Date());
				patternMapper.insert(pattern);
			} else {
				/* 已经上传过的修改更新时间 */
				checkPattern.setUpdatedAt(new Date());
				patternMapper.updateByPrimaryKey(checkPattern);
			}
			
			for (MultipartFile file : fileList) {
				boolean boSuccess = UploadUtils.uploadByBytes(file.getBytes(), file.getOriginalFilename());

				// 上传成功，保存上传的款式信息到款式表和款式信息详情表
				if (boSuccess) {
					resultSb.append(file.getOriginalFilename()).append("上传成功").append("<br>");
					/* 保存之前先校验是否已经上传过 */
					PatternDetail modifyPatternDetail = this
							.selectPatternDetailByPatternIdAndUrl(checkPattern.getId(), file.getOriginalFilename());
					if (modifyPatternDetail != null) {
						modifyPatternDetail.setUpdatedAt(new Date());
						patternDetailMapper.updateByPrimaryKey(modifyPatternDetail);
					} else {
						this.savePatternDetail(checkPattern, file.getOriginalFilename());
					}
				} else {
					resultSb.append(file.getOriginalFilename()).append("上传失败").append("<br>");
				}
			}
		}

		return resultSb.toString();
	}

	@Override
	public Pattern selectPatternByPatternName(String patternName) throws Exception {
		Pattern pattern = null;
		PatternExample example = new PatternExample();
		PatternExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(patternName);
		List<Pattern> patternList = patternMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(patternList)) {
			pattern = patternList.get(0);
		}

		return pattern;
	}

	/**
	 * TODO 查询所有款式信息
	 * 
	 * @see com.jhzz.demo.biz.PatternService#selectPatternList()
	 */
	@Override
	public List<PatternVo> selectPatternList() throws Exception {
		List<PatternVo> patternVoList = new ArrayList<PatternVo>();
		// 查询所有款式
		List<Pattern> patternList = patternMapper.selectByExample(new PatternExample());
		if (CollectionUtils.isNotEmpty(patternList)) {
			for (Pattern tempPattern : patternList) {
				PatternVo patternVo = new PatternVo();
				BeanUtils.copyProperties(tempPattern, patternVo);
				patternVo.setPics(this.queryPatternDetailsByPatternId(tempPattern.getId()));
				patternVoList.add(patternVo);
			}
		}

		return patternVoList;
	}

	/**
	 * 
	 * TODO 查询一个具体款式的详情信息
	 * 
	 * @see com.jhzz.demo.biz.PatternService#selectPatternDetailById(java.lang.Long)
	 */
	@Override
	public PatternVo selectPatternDetailById(Long id) throws Exception {
		PatternVo patternVo = new PatternVo();
		// 查询一个具体款式信息
		Pattern pattern = patternMapper.selectByPrimaryKey(id);
		if (pattern != null) {
			BeanUtils.copyProperties(pattern, patternVo);
			patternVo.setPics(this.queryPatternDetailsByPatternId(pattern.getId()));
		}

		return patternVo;
	}

	/**
	 * queryPatternDetailsByPatternId:根据款式ID查询相关详情信息. <br/>
	 *
	 * @author Administrator
	 * @param patternId
	 * @return
	 * @since JDK 1.6
	 */
	private Object[] queryPatternDetailsByPatternId(Long patternId) {
		Object[] pics = null;
		List<PatternDetail> patternDetailList = new ArrayList<>();

		PatternDetailExample example = new PatternDetailExample();
		PatternDetailExample.Criteria criteria = example.createCriteria();
		criteria.andPatternIdEqualTo(patternId);
		patternDetailList = patternDetailMapper.selectByExample(example);

		if (CollectionUtils.isNotEmpty(patternDetailList)) {
			pics = new Object[patternDetailList.size()];
			for (int i = 0; i < patternDetailList.size(); i++) {
				PatternDetail patternDetail = patternDetailList.get(0);
				pics[i] = DownloadUtils.download(patternDetail.getPicUrl());
			}
		}

		return pics;
	}

	/**
	 * 
	 * TODO 根据款式ID和某一个款式图片名称查询某一款具体信息.
	 * 
	 * @see com.jhzz.demo.biz.PatternService#selectPatternDetailByPatternIdAndUrl(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public PatternDetail selectPatternDetailByPatternIdAndUrl(Long patternId, String url) {
		PatternDetail patternDetail = null;

		List<PatternDetail> patternDetailList = new ArrayList<>();

		PatternDetailExample example = new PatternDetailExample();
		PatternDetailExample.Criteria criteria = example.createCriteria();
		criteria.andPatternIdEqualTo(patternId);
		criteria.andPicUrlEqualTo(url);
		patternDetailList = patternDetailMapper.selectByExample(example);

		if (CollectionUtils.isNotEmpty(patternDetailList)) {
			patternDetail = patternDetailList.get(0);
		}
		return patternDetail;
	}
	
	/**
	 * savePatternDetail:保存款式详情 <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator
	 * @param pattern
	 * @param key
	 * @since JDK 1.6
	 */
	private void savePatternDetail(Pattern pattern, String key){
		PatternDetail patternDetail = new PatternDetail();
		patternDetail.setPatternId(pattern.getId());
		patternDetail.setPicUrl(key);
		patternDetail.setCreatedAt(new Date());
		patternDetail.setUpdatedAt(new Date());
		patternDetailMapper.insert(patternDetail);
	}
}