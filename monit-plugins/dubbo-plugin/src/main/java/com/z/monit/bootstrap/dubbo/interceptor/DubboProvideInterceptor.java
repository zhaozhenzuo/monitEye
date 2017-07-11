package com.z.monit.bootstrap.dubbo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Invocation;
import com.z.monit.bootstrap.core.InvokeOperFactory;
import com.z.monit.bootstrap.core.InvokeOperInf;
import com.z.monit.bootstrap.core.InvokeParam;
import com.z.monit.bootstrap.core.intercept.Interceptor;

public class DubboProvideInterceptor implements Interceptor{
	
	private static final Logger logger=LoggerFactory.getLogger(DubboProvideInterceptor.class);

	public void before(Object target, Object[] args) {
		InvokeOperInf invokeOperInf=InvokeOperFactory.getInvokeOper();
		
		InvokeParam invokeParam=new InvokeParam();
		invokeParam.setInvokeUniqueKey("a1");
		invokeParam.setInvokeSeq(1);
		invokeParam.setParentId("0");
		
		invokeOperInf.getInvokerInfoCurThreadForAcceptor(invokeParam);
		
		logger.info(">DubboProvideInterceptor before method");
		
	}

	public void after(Object target, Object[] args, Object result, Throwable throwable) {
		logger.info(">DubboProvideInterceptor after method");
	}

	public void before() {
		// TODO Auto-generated method stub
		
	}

}
