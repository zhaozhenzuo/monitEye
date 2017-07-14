package com.z.monit.bootstrap.core;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.z.monit.bootstrap.core.constants.MonitConstants;

public class InvokeOperImpl implements InvokeOperInf {

	private static final ThreadLocal<InvokeInfo> invokeInfoStore = new ThreadLocal<InvokeInfo>();

	private static final String ROOT_ID = "-1";

	private static final int START_LEVEL_NUM = 0;

	public InvokeInfo getOrCreateInvokerInfoCurThread(InvokeParam invokeParam) {
		InvokeInfo invokeInfo = invokeInfoStore.get();
		if (invokeInfo != null) {
			return invokeInfo;
		}

		String invokeUniqueKey = invokeParam != null ? invokeParam.getTransactionId() : null;
		String parentId = invokeParam != null ? invokeParam.getParentId() : null;

		if (invokeUniqueKey == null) {
			invokeUniqueKey = this.generateUniqueKey();
		}

		if (parentId == null) {
			parentId = ROOT_ID;
		}

		String currentId;
		if (ROOT_ID.equals(parentId)) {
			currentId = "0";
		} else {
			currentId = parentId + CodeInfo.NODE_SPLIT + START_LEVEL_NUM;
		}

		InvokeInfo invokeInfoNew = new InvokeInfo();
		invokeInfoNew.setTransactionId(invokeUniqueKey);
		invokeInfoNew.setParentSpanId(parentId);
		invokeInfoNew.setCurrentSpanId(currentId);

		AtomicInteger currentInvokeSeq = new AtomicInteger(0);
		invokeInfoNew.setCurrentInvokeSeq(currentInvokeSeq);

		invokeInfoStore.set(invokeInfoNew);

		return invokeInfoNew;
	}

	public InvokeParam composeInvokeParamAndIncreaseCurInvokeSeqForInvoker(String transactionId, String nextSpanId,
			String bizUniqueKey) {
		InvokeInfo invokeInfo = invokeInfoStore.get();
		if (invokeInfo == null) {
			throw new IllegalArgumentException("not found invokeInfo");
		}

		InvokeParam invokeParam = new InvokeParam();

		// 调用链id
		invokeParam.setTransactionId(transactionId);

		// 调用父结点id等于当前结点id
		invokeParam.setParentId(nextSpanId);

		invokeParam.setBizUniqueKey(bizUniqueKey);

		return invokeParam;
	}

	private String generateUniqueKey() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

	public InvokeInfo getInvokerInfoCurThread() {
		return invokeInfoStore.get();
	}

	public void clearInvokerInfoCurThread() {
		invokeInfoStore.remove();
	}

	/**
	 * 增加当前span，用于新的调用事件
	 */
	public String nextSpanId() {
		InvokeInfo invokeInfo = invokeInfoStore.get();
		if (invokeInfo == null) {
			throw new RuntimeException("invokerInfo is null");
		}

		String res = increaseLastLevel(invokeInfo.getCurrentSpanId());
		invokeInfo.setCurrentSpanId(res);

		return res;
	}

	public static void main(String[] args) {
		String res = "1";

		System.out.println(increaseLastLevel(res));
	}

	private static String increaseLastLevel(String curSpanId) {
		if (curSpanId == null) {
			return curSpanId;
		}

		int lastIndex = curSpanId.lastIndexOf(MonitConstants.LEVEL_SPLIT);
		if (lastIndex >= 0) {
			int lastLevel = Integer.valueOf(curSpanId.substring(lastIndex + 1)) + 1;
			curSpanId = curSpanId.substring(0, lastIndex) + MonitConstants.LEVEL_SPLIT + lastLevel;
		} else {
			int temp = Integer.valueOf(curSpanId);
			curSpanId = String.valueOf(temp + 1);
		}

		return curSpanId;
	}

}
