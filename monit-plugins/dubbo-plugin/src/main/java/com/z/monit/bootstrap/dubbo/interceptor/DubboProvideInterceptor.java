package com.z.monit.bootstrap.dubbo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.z.monit.bootstrap.core.InvokeEvent;
import com.z.monit.bootstrap.core.InvokeOperFactory;
import com.z.monit.bootstrap.core.InvokeOperInf;
import com.z.monit.bootstrap.core.InvokeParam;
import com.z.monit.bootstrap.core.constants.MonitInvokeEventRole;
import com.z.monit.bootstrap.core.context.InvokeEventStore;
import com.z.monit.bootstrap.core.intercept.Interceptor;
import com.z.monit.bootstrap.dubbo.constants.DubboConstants;

public class DubboProvideInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(DubboProvideInterceptor.class);

	@SuppressWarnings("rawtypes")
	public void before(Object target, Object args) {
		try {
			Invoker invoker = (Invoker) target;

			Object[] params = (Object[]) args;
			RpcInvocation invocation = (RpcInvocation) params[0];

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
			invokeEvent.setBeginTime(System.currentTimeMillis());
			invokeEvent.setTransactionId(transactionId);
			invokeEvent.setCurSpanId(parentId + "." + invokeSeq);
			invokeEvent.setInterfaceName(invoker.getInterface().getName());
			invokeEvent.setRole(MonitInvokeEventRole.ACCEPTOR);
			invokeEvent.setInvokerIp(RpcContext.getContext().getRemoteHost());
			invokeEvent.setParentId(parentId);
			invokeEvent.setMethodName(invocation.getMethodName());
			invokeEvent.setInvokeSeq(invokeSeqInt);

			InvokeEventStore.pushInvokeEvent(invokeEvent);

			logger.info(">monit begin,event[" + invokeEvent.toString() + "]");
		} catch (Exception e) {
			logger.error(">before exception", e);
		}
	}

	public void after(Object target, Object args, Object result, Throwable throwable) {

		try {
			InvokeEvent invokeEvent = InvokeEventStore.popInvokeEvent();
			if (invokeEvent == null) {
				return;
			}

			long endTime = System.currentTimeMillis();
			long cost = endTime - invokeEvent.getBeginTime();
			invokeEvent.setCost(cost);

			logger.info(">monit end,event[" + invokeEvent.toString() + "]");
		} finally {
			InvokeEventStore.clearInvokeEventForCurrentThread();
			InvokeOperInf invokeOperInf = InvokeOperFactory.getInvokeOper();
			invokeOperInf.clearInvokerInfoCurThread();
		}

	}

}
