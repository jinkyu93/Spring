package com.jkpark.study.global.util.bean;

import org.springframework.context.ApplicationContext;

/** 특정 타입의 bean 객체를 가져오는 유틸 **/
/** @Autowired 를 사용하기 어려운 경우 쓸 수 있다. **/
public class BeanUtil {
	public static<T> T getBean(Class<T> beanType) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		return applicationContext.getBean(beanType);
	}
}