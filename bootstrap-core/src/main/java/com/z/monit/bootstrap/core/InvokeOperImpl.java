package com.z.monit.bootstrap.core;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class InvokeOperImpl implements InvokeOperInf {

	private static final ThreadLocal<InvokeInfo> invokeInfoStore = new ThreadLocal<InvokeInfo>();

	private static final String ROOT_ID = "-1";

	public InvokeInfo getInvokerInfoCurThreadForAcceptor(InvokeParam invokeParam) {
		InvokeInfo invokeInfo = invokeInfoStore.get();
		if (invokeInfo != null) {
			return invokeInfo;
		}

		String invokeUniqueKey = invokeParam != null ? invokeParam.getInvokeUniqueKey() : null;
		String parentId = invokeParam != null ? invokeParam.getParentId() : null;
		Integer invokeSeq = invokeParam != null ? invokeParam.getInvokeSeq() : null;

		if (invokeUniqueKey == null) {
			invokeUniqueKey = this.generateUniqueKey();
		}

		if (parentId == null) {
			parentId = ROOT_ID;
		}

		if (invokeSeq == null) {
			invokeSeq = 0;
		}

		String currentId;
		if (ROOT_ID.equals(parentId)) {
			currentId = "0";
		} else {
			currentId = parentId + CodeInfo.NODE_SPLIT + invokeSeq;
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

	public InvokeParam composeInvokeParamAndIncreaseCurInvokeSeqForInvoker(String bizUniqueKey) {
		InvokeInfo invokeInfo = invokeInfoStore.get();
		if (invokeInfo == null) {
			throw new IllegalArgumentException("not found invokeInfo");
		}

		InvokeParam invokeParam = new InvokeParam();

		// 调用链id
		invokeParam.setInvokeUniqueKey(invokeInfo.getTransactionId());

		// 调用父结点id等于当前结点id
		invokeParam.setParentId(invokeInfo.getCurrentSpanId());

		// 调用序列,下个被调用结点的id=［父结点id］＋［.］+［调用序列］
		invokeParam.setInvokeSeq(invokeInfo.getCurrentInvokeSeq().getAndIncrement());

		invokeParam.setBizUniqueKey(bizUniqueKey);

		return invokeParam;
	}

	private String generateUniqueKey() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

}
