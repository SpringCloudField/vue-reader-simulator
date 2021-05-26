package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceStatusTO {
	
	@JsonProperty("busy_state")
	private Boolean busyState;
	
	@JsonProperty("cassette_in")
	private Boolean cassetteIn;
	
	@JsonProperty("cassette_time")
	private Double cassetteTime;
	
	@JsonProperty("date_time")
	private String dateTime;
	
	@JsonProperty("db_version")
	private Integer dbVersion;
	
	private String platform;
	
	@JsonProperty("release_version")
	private String releaseVersion;
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("settings_version")
	private String settingsVersion;
	
	public Boolean getBusyState() {
		return busyState;
	}
	public void setBusyState(Boolean busyState) {
		this.busyState = busyState;
	}
	public Boolean getCassetteIn() {
		return cassetteIn;
	}
	public void setCassetteIn(Boolean cassetteIn) {
		this.cassetteIn = cassetteIn;
	}
	public Double getCassetteTime() {
		return cassetteTime;
	}
	public void setCassetteTime(Double cassetteTime) {
		this.cassetteTime = cassetteTime;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Integer getDbVersion() {
		return dbVersion;
	}
	public void setDbVersion(Integer dbVersion) {
		this.dbVersion = dbVersion;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getReleaseVersion() {
		return releaseVersion;
	}
	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSettingsVersion() {
		return settingsVersion;
	}
	public void setSettingsVersion(String settingsVersion) {
		this.settingsVersion = settingsVersion;
	}
	
	
	
}
