package com.z.monit.bootstrap.dubbo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.RpcInvocation;
import com.z.monit.bootstrap.core.InvokeInfo;
import com.z.monit.bootstrap.core.InvokeOperFactory;
import com.z.monit.bootstrap.core.InvokeOperInf;
import com.z.monit.bootstrap.core.InvokeParam;
import com.z.monit.bootstrap.core.intercept.Interceptor;
import com.z.monit.bootstrap.dubbo.constants.DubboConstants;

/**
 * DOBUBBO服务消费方调用
 * 
 * @author zhaozhenzuo
 *
 */
public class DubboConsumerInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(DubboConsumerInterceptor.class);

	public void before(Object target, Object args) {
		try {

			/**
			 * 0.获取当前线程调用信息，没有则创建一个
			 */
			InvokeOperInf invokeOperInf = InvokeOperFactory.getInvokeOper();
			InvokeParam invokeParam = new InvokeParam();

			InvokeInfo invokeInfo = invokeOperInf.getOrCreateInvokerInfoCurThread(invokeParam);
			if (invokeInfo == null) {
				logger.error(">invoke info is null");
				return;
			}

			/**
			 * 1.组装调用下个服务的信息
			 */
			InvokeParam invokeParamToAcceptor = invokeOperInf
					.composeInvokeParamAndIncreaseCurInvokeSeqForInvoker("2017000001");

			/**
			 * 2.将此信息传送给服务提供方
			 */
			RpcInvocation invocation = (RpcInvocation) args;
			invocation.setAttachment(DubboConstants.TRANSACTION_ID, invokeParamToAcceptor.getTransactionId());
			invocation.setAttachment(DubboConstants.INVOKE_SEQ_FOR_ACCEPTOR,
					invokeParamToAcceptor.getInvokeSeq().toString());
			invocation.setAttachment(DubboConstants.PARENT_SPAN_ID, invokeParamToAcceptor.getParentId().toString());

			logger.info(">DubboConsumerInterceptor before method");
		} catch (Exception e) {
			logger.error(">before exception");
		}

	}

	public void after(Object target, Object args, Object result, Throwable throwable) {
		logger.info(">DubboProvideInterceptor after method");
	}

}
