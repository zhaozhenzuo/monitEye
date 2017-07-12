package com.z.monit.bootstrap.core.context;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.monit.bootstrap.core.InvokeEvent;

/**
 * 当前线程中的调用事件存储，线程间独立
 * 
 * @author zhaozhenzuo
 *
 */
public class InvokeEventStore {

	private static final Logger logger = LoggerFactory.getLogger(InvokeEventStore.class);

	private static final ThreadLocal<Stack<InvokeEvent>> invokeEventStore = new ThreadLocal<Stack<InvokeEvent>>();

	/**
	 * 将调用事件放入到当前线程的栈中<br/>
	 * 这里不需要同步，因为是线程独立的
	 * 
	 * @param invokeEvent
	 */
	public static void pushInvokeEvent(final InvokeEvent invokeEvent) {
		Stack<InvokeEvent> stack = invokeEventStore.get();
		if (stack == null) {
			stack = new Stack<InvokeEvent>();
		}

		stack.push(invokeEvent);
	}

	public static InvokeEvent popInvokeEvent() {
		Stack<InvokeEvent> stack = invokeEventStore.get();
		if (stack == null) {
			logger.error(">popInvokeEvent err,no stack found");
			return null;
		}

		return stack.pop();
	}

}
