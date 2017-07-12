package com.z.monit.bootstrap.dubbo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.z.monit.bootstrap.core.InvokeEvent;
import com.z.monit.bootstrap.core.InvokeInfo;
import com.z.monit.bootstrap.core.InvokeOperFactory;
import com.z.monit.bootstrap.core.InvokeOperInf;
import com.z.monit.bootstrap.core.InvokeParam;
import com.z.monit.bootstrap.core.constants.MonitInvokeEventRole;
import com.z.monit.bootstrap.core.context.InvokeEventStore;
import com.z.monit.bootstrap.core.intercept.Interceptor;
import com.z.monit.bootstrap.dubbo.constants.DubboConstants;

public class DubboProvideInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(DubboProvideInterceptor.class);

	public void before(Object target, Object args) {
		Invoker invoker = (Invoker) target;

		System.out.println("====" + invoker.getInterface().getSimpleName());

		RpcInvocation invocation = (RpcInvocation) args;

		String transactionId = invocation.getAttachment(DubboConstants.TRANSACTION_ID);
		String parentId = invocation.getAttachment(DubboConstants.PARENT_SPAN_ID);
		String invokeSeq = invocation.getAttachment(DubboConstants.INVOKE_SEQ_FOR_ACCEPTOR);

		Integer invokeSeqInt = null;
		if (invokeSeq != null) {
			invokeSeqInt = Integer.valueOf(invokeSeq);
		}

		InvokeParam invokeParam = new InvokeParam();
		invokeParam.setTransactionId(transactionId);
		invokeParam.setParentId(parentId);
		invokeParam.setInvokeSeq(invokeSeqInt);

		InvokeOperInf invokeOperInf = InvokeOperFactory.getInvokeOper();
		invokeOperInf.getOrCreateInvokerInfoCurThread(invokeParam);

		/**
		 * 将当前被调用信息放入栈中
		 */
		InvokeEvent invokeEvent = new InvokeEvent();
		invokeEvent.setTransactionId(transactionId);
		invokeEvent.setCurSpanId(parentId + "." + invokeSeq);
		invokeEvent.setAcceptorInterfaceName(invoker.getInterface().getName());
		invokeEvent.setRole(MonitInvokeEventRole.ACCEPTOR);

		logger.info(">monit,event[" + invokeEvent.toString() + "]");

		InvokeEventStore.pushInvokeEvent(invokeEvent);

		logger.info(">DubboProvideInterceptor before method");

	}

	public void after(Object target, Object args, Object result, Throwable throwable) {
		logger.info(">DubboProvideInterceptor after method");
	}

}
