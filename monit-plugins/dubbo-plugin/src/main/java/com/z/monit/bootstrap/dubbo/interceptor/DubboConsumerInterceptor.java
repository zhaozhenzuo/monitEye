package com.z.monit.bootstrap.dubbo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
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

/**
 * DOBUBBO服务消费方调用<br/>
 * 注：当前线程相关调用信息释放由第一个结点释放，这里是dubbo的被调用类：dubboProviderInterceptor或者是http入口<br/>
 * 
 * @author zhaozhenzuo
 *
 */
public class DubboConsumerInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(DubboConsumerInterceptor.class);

	@SuppressWarnings("rawtypes")
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
			 * 1.组装调用下个服务的信息<br/>
			 * 注：每次调用新方法表明一个调用事件需要生成一个新的spanId
			 */
			String transactionId = invokeInfo.getTransactionId();
			String nextSpanId = invokeOperInf.nextSpanId();

			/**
			 * 2.将此信息传送给服务提供方
			 */
			Object[] params = (Object[]) args;
			RpcInvocation invocation = (RpcInvocation) params[1];
			invocation.setAttachment(DubboConstants.TRANSACTION_ID, transactionId);
			invocation.setAttachment(DubboConstants.PARENT_SPAN_ID, invokeInfo.getCurrentSpanId());
			invocation.setAttachment(DubboConstants.SPAN_ID, nextSpanId);

			/**
			 * 3.记录新的调用事件信息,新事件的spanId为[nextSpanId]
			 */
			InvokeEvent invokeEvent = new InvokeEvent();
			invokeEvent.setBeginTime(System.currentTimeMillis());
			invokeEvent.setTransactionId(transactionId);
			invokeEvent.setCurSpanId(invokeInfo.getCurrentSpanId());
			invokeEvent.setRole(MonitInvokeEventRole.INVOKER);
			invokeEvent.setInvokerIp(RpcContext.getContext().getRemoteHost());
			invokeEvent.setParentId(invokeInfo.getParentSpanId());
			invokeEvent.setNextSpanId(nextSpanId);

			Invoker invoker = (Invoker) params[0];
			invokeEvent.setInterfaceName(invoker.getInterface().getName());
			invokeEvent.setMethodName(invocation.getMethodName());

			InvokeEventStore.pushInvokeEvent(invokeEvent);

			logger.info(">monit begin,event[" + invokeEvent.toString() + "]");
		} catch (Exception e) {
			logger.error(">before exception");
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
		} catch (Exception e) {
			logger.info(">after exception", e);
		} finally {

		}
	}

}
