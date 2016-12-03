package com.jhzz.simpleArchitecture.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author sunjian
 * @date 2016/11/30
 * 
 * @desc 这个自定义注解可以配合自定义的拦截器使用，用来实现权限验证等等功能
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminPermission {

}
