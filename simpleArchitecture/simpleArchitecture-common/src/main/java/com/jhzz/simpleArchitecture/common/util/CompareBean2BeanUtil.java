package com.jhzz.simpleArchitecture.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName: CompareBean2BeanUtil
 * @Description: 比较两个对象属性是否一致
 * @author wb-fujin@alibaba-inc.com
 * @date 2015年8月5日 下午2:48:29
 */
public class CompareBean2BeanUtil {

    /**
     * 适用于没有继承的类比较
     * 
     * @param from
     * @param to
     * @return
     */
    public static boolean compareBean2Bean(Object from, Object to) {
        if (checkClass(from, to)) {
            Class<? extends Object> fClass = from.getClass();
            Field[] fFields = fClass.getDeclaredFields();

            try {
                for (Field field : fFields) {
                    String cKey = field.getName();
                    // 确定第一个字母大写
                    cKey = cKey.substring(0, 1).toUpperCase() + cKey.substring(1);
                    Method fMethod;
                    Object fValue;
                    Object tValue;
                    try {
                        fMethod = fClass.getMethod("get" + cKey);
                        fValue = fMethod.invoke(from);
                        tValue = fMethod.invoke(to);
                        ;
                    } catch (Exception e) {
                        try {
                            fMethod = fClass.getMethod("is" + cKey);
                            fValue = fMethod.invoke(from);
                            tValue = fMethod.invoke(to);
                        } catch (Exception e1) { // 如果from没有此属性的get方法或is方法，跳过
                            continue;
                        }
                    }
                    if (null == fValue) {
                        if (null != tValue) {
                            return false;
                        }
                    } else if (null != fValue) {
                        if (null != tValue && !fValue.equals(tValue)) {
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;

    }

    public static boolean checkClass(Object from, Object to) {
        return from.getClass().equals(to.getClass());
    }
}
