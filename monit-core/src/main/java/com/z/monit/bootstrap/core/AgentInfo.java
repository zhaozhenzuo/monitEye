package com.z.monit.bootstrap.core;

import java.io.Serializable;
import java.util.Date;

/**
 * agent相关信息<br/>
 * 包含:agentName,启动时间等
 * 
 * @author zhaozhenzuo
 *
 */
public class AgentInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * agent名称
	 */
	private String agentName;
	
	/**
	 * agent启动时间
	 */
	private Date startTime;

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
