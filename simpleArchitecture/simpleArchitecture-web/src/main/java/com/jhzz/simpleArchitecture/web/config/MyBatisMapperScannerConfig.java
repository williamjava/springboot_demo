package com.jhzz.simpleArchitecture.web.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author sunjian
 * @desc 配置spring应该扫描哪些包下面的*Mapper.java文件，从而决定为哪些Mapper生成动态代理
 * 
 * @date 2016/11/29
 * 
 */
@Configuration
public class MyBatisMapperScannerConfig {
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.jhzz.simpleArchitecture.dal.mapper");

		return mapperScannerConfigurer;
	}
}
