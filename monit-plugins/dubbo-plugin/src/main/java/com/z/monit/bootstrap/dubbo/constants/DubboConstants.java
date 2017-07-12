package com.z.monit.bootstrap.dubbo.constants;

public interface DubboConstants {

	public static final String NOT_SAMPLE = "_DUBBO_NOT_SAMPLE";

	public static final String TRANSACTION_ID = "_DUBBO_TRANSACTION_ID";

	/**
	 * 上级调用方id
	 */
	public static final String PARENT_SPAN_ID = "_DUBBO_PARENT_SPAN_ID";

	/**
	 * 服务消费方给服务提供方调用序列,服务提供方id＝PARENT_SPAN_ID+"."+INVOKE_SEQ_FOR_ACCEPTOR
	 */
	public static final String INVOKE_SEQ_FOR_ACCEPTOR = "_DUBBO_INVOKE_SEQ_FOR_ACCEPTOR";

}
