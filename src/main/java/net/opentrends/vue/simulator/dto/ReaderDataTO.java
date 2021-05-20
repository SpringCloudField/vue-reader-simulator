package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReaderDataTO {
	@JsonProperty("cassette_time")
	private Double cassetteTime;
	
	@JsonProperty("date_time")
	private String dateTime;
	
	@JsonProperty("db_version")
	private String dbVersion;
	
	@JsonProperty("release_version")
	private String releaseVersion;
	
	@JsonProperty("settings_version")
	private String settingsVersion;

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

	public String getDbVersion() {
		return dbVersion;
	}

	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}

	public String getReleaseVersion() {
		return releaseVersion;
	}

	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}

	public String getSettingsVersion() {
		return settingsVersion;
	}

	public void setSettingsVersion(String settingsVersion) {
		this.settingsVersion = settingsVersion;
	}
	
	
	
}

