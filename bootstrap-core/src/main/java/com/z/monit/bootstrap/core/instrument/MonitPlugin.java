package com.z.monit.bootstrap.core.instrument;

import java.util.Map;

/**
 * monit插件接口
 * 
 * @author zhaozhenzuo
 *
 */
public interface MonitPlugin {

	/**
	 * 业务方实现plugin，将要织入的类名放入map的key中，map的value为要织入的逻辑s
	 * 
	 * @return
	 */
	public Map<String, TransformCallback> addTransformCallBack();

}
