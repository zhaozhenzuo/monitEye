package com.z.monit.bootstrap.dubbo.constants;

public interface DubboConstants {

	public static final String NOT_SAMPLE = "_DUBBO_NOT_SAMPLE";

	public static final String TRANSACTION_ID = "_DUBBO_TRANSACTION_ID";

	/**
	 * 上级调用方id
	 */
	public static final String PARENT_SPAN_ID = "_DUBBO_PARENT_SPAN_ID";

	/**
	 * 服务提供方的id
	 */
	public static final String SPAN_ID_FOR_ACCEPTOR = "_DUBBO_CURRENT_ID";

}
