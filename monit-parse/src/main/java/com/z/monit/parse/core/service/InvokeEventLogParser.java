package com.z.monit.parse.core.service;

import java.util.List;

import com.z.monit.parse.core.model.InvokeEventParseResult;

/**
 * 监控日志分析接口
 * 
 * @author zhaozhenzuo
 *
 */
public interface InvokeEventLogParser {

	/**
	 * 解析日志
	 * 
	 * @param log
	 * @return
	 */
	public List<InvokeEventParseResult> parseLog(String log);

}
