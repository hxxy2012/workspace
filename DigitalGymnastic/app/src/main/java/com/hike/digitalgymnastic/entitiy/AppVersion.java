package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

public class AppVersion implements Serializable{
	String latestVersion;//		最新版本	
	String forceVersion	;//	强升版本	
	String packageUrl	;//	安装包地址
	public String getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}
	public String getForceVersion() {
		return forceVersion;
	}
	public void setForceVersion(String forceVersion) {
		this.forceVersion = forceVersion;
	}
	public String getPackageUrl() {
		return packageUrl;
	}
	public void setPackageUrl(String packageUrl) {
		this.packageUrl = packageUrl;
	}
	
	
}
