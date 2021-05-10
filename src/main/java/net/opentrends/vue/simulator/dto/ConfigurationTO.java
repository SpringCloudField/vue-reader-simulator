package net.opentrends.vue.simulator.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ConfigurationTO {

	private String id;
	
	@NotEmpty(message = "Serial_number is required")
	private String serialNumber;

	@NotNull(message = "Busy_state is required")
	private Boolean busyState;
	@NotNull(message = "Cassette_in is required")
	private Boolean cassetteIn;
	@NotNull(message = "Cassette_time is required")
	private Double cassetteTime;
	@NotEmpty(message = "Settings_version is required")
	private String settingsVersion;
	@NotEmpty(message = "Release_version is required")
	private String releaseVersion;

	@NotNull(message = "Cassette_type is required")	
	private Integer cassetteTypeId;
	@NotNull(message = "Test_type is required")	
	private String testType;
	@NotNull(message = "Same_cassette is required")
	private Boolean sameCassette;
	private Integer cassetteErrorCode;
	private Integer processId;
	private Integer previousProcessId;
	private ScanSingleTO scanSingle;
	private ScanMultipleTO scanMultiple;
	private ScanDoubleTO scanDouble;
		

	public ConfigurationTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

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

	public String getSettingsVersion() {
		return settingsVersion;
	}

	public void setSettingsVersion(String settingsVersion) {
		this.settingsVersion = settingsVersion;
	}

	public String getReleaseVersion() {
		return releaseVersion;
	}

	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
	
	public Integer getCassetteErrorCode() {
		return cassetteErrorCode;
	}

	public void setCassetteErrorCode(Integer cassetteErrorCode) {
		this.cassetteErrorCode = cassetteErrorCode;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getPreviousProcessId() {
		return previousProcessId;
	}

	public void setPreviousProcessId(Integer previousProcessId) {
		this.previousProcessId = previousProcessId;
	}

	public ScanSingleTO getScanSingle() {
		return scanSingle;
	}

	public void setScanSingle(ScanSingleTO scanSingle) {
		this.scanSingle = scanSingle;
	}

	public ScanMultipleTO getScanMultiple() {
		return scanMultiple;
	}

	public void setScanMultiple(ScanMultipleTO scanMultiple) {
		this.scanMultiple = scanMultiple;
	}

	public ScanDoubleTO getScanDouble() {
		return scanDouble;
	}

	public void setScanDouble(ScanDoubleTO scanDouble) {
		this.scanDouble = scanDouble;
	}

	public Integer getCassetteTypeId() {
		return cassetteTypeId;
	}

	public void setCassetteTypeId(Integer cassetteTypeId) {
		this.cassetteTypeId = cassetteTypeId;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public Boolean getSameCassette() {
		return sameCassette;
	}

	public void setSameCassette(Boolean sameCassette) {
		this.sameCassette = sameCassette;
	}

	@Override
	public String toString() {
		return "ConfigurationTO [id=" + id + ", serialNumber=" + serialNumber 
				+ ", busyState=" + busyState + ", cassetteIn=" + cassetteIn + ", cassetteTime=" + cassetteTime
				+ ", settingsVersion=" + settingsVersion + ", releaseVersion=" + releaseVersion + ", cassetteTypeId="
				+ cassetteTypeId + ", testType=" + testType + ", cassetteErrorCode=" + cassetteErrorCode
				+ ", processId=" + processId + ", previousProcessId=" + previousProcessId + ", scanSingle=" + scanSingle
				+ ", scanMultiple=" + scanMultiple + ", scanDouble=" + scanDouble + "]";
	}
	
}
