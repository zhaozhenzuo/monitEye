package com.z.monit.bootstrap.core.context;

import java.util.Stack;

import com.z.monit.bootstrap.core.InvokeEvent;

/**
 * 当前线程中的调用事件存储，线程间独立
 * 
 * @author zhaozhenzuo
 *
 */
public class InvokeEventStore {

	private static final ThreadLocal<Stack<InvokeEvent>> invokeEventStore = new ThreadLocal<Stack<InvokeEvent>>();

	/**
	 * 将调用事件放入到当前线程的栈中<br/>
	 * 这里不需要同步，因为是线程独立的
	 * 
	 * @param invokeEvent
	 */
	public static void pushInvokeEvent(final InvokeEvent invokeEvent) {
		if(invokeEventStore == null){
			return;
		}
		
		Stack<InvokeEvent> stack = invokeEventStore.get();
		if (stack == null) {
			stack = new Stack<InvokeEvent>();
			invokeEventStore.set(stack);
		}

		stack.push(invokeEvent);
	}
	
	public static void clearInvokeEventForCurrentThread(){
		if(invokeEventStore==null){
			return;
		}
		
		Stack<InvokeEvent> stack =invokeEventStore.get();
		if(stack == null){
			return;
		}
		stack.clear();
	}

	public static InvokeEvent popInvokeEvent() {
		if(invokeEventStore == null){
			return null;
		}
		
		Stack<InvokeEvent> stack = invokeEventStore.get();
		if (stack == null) {
			return null;
		}

		return stack.pop();
	}

}
