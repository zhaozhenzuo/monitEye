package com.z.monit.bootstrap.core;

public class InvokeOperFactory {

	private static final InvokeOperInf invokeOper = new InvokeOperImpl();

	public static InvokeOperInf getInvokeOper() {
		return invokeOper;
	}

}
