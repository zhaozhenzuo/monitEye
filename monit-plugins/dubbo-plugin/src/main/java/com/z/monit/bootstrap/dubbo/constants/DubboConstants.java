package com.z.monit.bootstrap.dubbo.constants;

public interface DubboConstants {

	public static final String NOT_SAMPLE = "_DUBBO_NOT_SAMPLE";

	public static final String TRANSACTION_ID = "_DUBBO_TRANSACTION_ID";

	/**
	 * 上级调用方id
	 */
	public static final String PARENT_SPAN_ID = "_DUBBO_PARENT_SPAN_ID";

	/**
	 * 服务消费方给服务提供方的spanId
	 */
	public static final String SPAN_ID = "_DUBBO_INVOKE_SPAN_ID";

}
