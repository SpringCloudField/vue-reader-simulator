package net.opentrends.vue.simulator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configuration")
public class Configuration {

	@Id
	private String id;
	
	private String userId;

	// Config
	@NotNull
	@NotEmpty
	private String serialNumber;
	@NotNull
	@NotEmpty
	private String ethernetIp;
	
	// Status
	@NotNull
	@NotEmpty
	private Boolean busyState;
	@NotNull
	@NotEmpty
	private Integer cassetteIn;
	@NotNull
	@NotEmpty
	private Double cassetteTime;
	@NotNull
	@NotEmpty
	private String settingsVersion;
	@NotNull
	@NotEmpty
	private String releaseVersion;
	
	// Scan
	@NotNull
	@NotEmpty
	private Integer cassetteTypeId;		
	@NotNull
	@NotEmpty
	private String testType;
	private Integer cassetteErrorCode;
	private Integer processId;
	private Integer previousProcessId;
	private ScanSingle scanSingle;
	private ScanMultiple scanMultiple;
	private ScanDouble scanDouble;
	
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
	public String getEthernetIp() {
		return ethernetIp;
	}
	public void setEthernetIp(String ethernetIp) {
		this.ethernetIp = ethernetIp;
	}
	public Boolean getBusyState() {
		return busyState;
	}
	public void setBusyState(Boolean busyState) {
		this.busyState = busyState;
	}
	public Integer getCassetteIn() {
		return cassetteIn;
	}
	public void setCassetteIn(Integer cassetteIn) {
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
	public ScanSingle getScanSingle() {
		return scanSingle;
	}
	public void setScanSingle(ScanSingle scanSingle) {
		this.scanSingle = scanSingle;
	}
	public ScanMultiple getScanMultiple() {
		return scanMultiple;
	}
	public void setScanMultiple(ScanMultiple scanMultiple) {
		this.scanMultiple = scanMultiple;
	}
	public ScanDouble getScanDouble() {
		return scanDouble;
	}
	public void setScanDouble(ScanDouble scanDouble) {
		this.scanDouble = scanDouble;
	}
	public Integer getCassetteTypeId() {
		return cassetteTypeId;
	}
	public void setCassetteTypeId(Integer cassetteTypeId) {
		this.cassetteTypeId = cassetteTypeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}

}
