package com.z.monit.bootstrap.dubbo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.monit.bootstrap.core.intercept.Interceptor;

public class DubboProvideInterceptor implements Interceptor{
	
	private static final Logger logger=LoggerFactory.getLogger(DubboProvideInterceptor.class);

	public void before(Object target, Object[] args) {
		logger.info(">DubboProvideInterceptor before method");
		
	}

	public void after(Object target, Object[] args, Object result, Throwable throwable) {
		logger.info(">DubboProvideInterceptor after method");
	}

}
