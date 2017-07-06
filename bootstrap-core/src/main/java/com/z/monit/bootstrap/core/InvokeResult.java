package com.z.monit.bootstrap.core;

import java.io.Serializable;
import java.util.Date;

/**
 * 调用结果信息
 * 
 * @author zhaozhenzuo
 *
 */
public class InvokeResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 调用链id
	 */
	private String invokeUniqueKey;

	/**
	 * 父结点id
	 */
	private String parentId;

	/**
	 * 当前调用id
	 */
	private String invokeId;

	private String interfaceName;

	private String ip;

	/**
	 * 0-调用者 1-被调用者
	 */
	private Integer role;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 调用结果状态<br/>
	 * -1:错误<br/>
	 * 1:正常
	 */
	private Integer status;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	/**
	 * 业务唯一值<br/>
	 * eg:单号
	 */
	private String bizUniqueKey;

	public String getInvokeUniqueKey() {
		return invokeUniqueKey;
	}

	public void setInvokeUniqueKey(String invokeUniqueKey) {
		this.invokeUniqueKey = invokeUniqueKey;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getInvokeId() {
		return invokeId;
	}

	public void setInvokeId(String invokeId) {
		this.invokeId = invokeId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getBizUniqueKey() {
		return bizUniqueKey;
	}

	public void setBizUniqueKey(String bizUniqueKey) {
		this.bizUniqueKey = bizUniqueKey;
	}

}
