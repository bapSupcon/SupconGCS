package com.supcon.gcs.entities;
/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */

/**
 * 
 * 
 * @author Administrator
 * @version 1.0
 */
public class Device {
	
	private String name; //点位
	
	private Integer status; //状态  0关闭   1开启

	private Integer operate;//操作命令  0 关闭  1开启
	
	private String diagnoseInfo;//诊断信息
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public String getDiagnoseInfo() {
		return diagnoseInfo;
	}

	public void setDiagnoseInfo(String diagnoseInfo) {
		this.diagnoseInfo = diagnoseInfo;
	}
	
}
