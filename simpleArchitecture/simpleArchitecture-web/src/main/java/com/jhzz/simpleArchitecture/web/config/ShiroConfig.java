package com.jhzz.simpleArchitecture.web.config;

/**
 * 
 * @author sunjian 2016/11/30
 * @desc 基于java的方式配置shiro
 */
//@Configuration
public class ShiroConfig {
	/**
	@Bean(name = "ShiroRealmImpl")
	public ShiroRealmImpl getShiroRealm() {
		return new ShiroRealmImpl();
	}

	@Bean(name = "shiroEhcacheManager")
	public EhCacheManager ehCacheManager() {
		EhCacheManager em = new EhCacheManager();

		return em;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(getShiroRealm());
		manager.setCacheManager(ehCacheManager());
		return manager;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/sa/index");
		filterChainDefinitionMap.put("/sa/**", "authc");
		filterChainDefinitionMap.put("/**", "anon");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	*/

}
