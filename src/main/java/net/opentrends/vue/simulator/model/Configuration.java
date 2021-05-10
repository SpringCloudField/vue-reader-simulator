package net.opentrends.vue.simulator.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configuration")
public class Configuration {

	@Id
	private String id;
	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	private String serialNumber;
	
	@NotNull
	@NotEmpty
	private Boolean busyState;
	@NotNull
	@NotEmpty
	private Boolean cassetteIn;
	@NotNull
	@NotEmpty
	private Double cassetteTime;
	@NotNull
	@NotEmpty
	private String settingsVersion;
	@NotNull
	@NotEmpty
	private String releaseVersion;
	
	@NotNull
	@NotEmpty
	private Integer cassetteTypeId;		
	@NotNull
	@NotEmpty
	private String testType;
	private Boolean sameCassette;
	private Integer cassetteErrorCode;
	private Integer cassetteErrorCode2;
	private Integer processId;
	private Integer processId2;
	private Integer previousProcessId;
	private Integer previousProcessId2;
	private ScanSingle scanSingle;
	private ScanMultiple scanMultiple;
	private ScanDouble scanDouble;

	@LastModifiedDate
	private Date lastModifiedDate; 
	
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
	public Integer getCassetteErrorCode2() {
		return cassetteErrorCode2;
	}
	public void setCassetteErrorCode2(Integer cassetteErrorCode2) {
		this.cassetteErrorCode2 = cassetteErrorCode2;
	}
	public Integer getProcessId() {
		return processId;
	}
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	public Integer getProcessId2() {
		return processId2;
	}
	public void setProcessId2(Integer processId2) {
		this.processId2 = processId2;
	}
	public Integer getPreviousProcessId() {
		return previousProcessId;
	}
	public void setPreviousProcessId(Integer previousProcessId) {
		this.previousProcessId = previousProcessId;
	}
	public Integer getPreviousProcessId2() {
		return previousProcessId2;
	}
	public void setPreviousProcessId2(Integer previousProcessId2) {
		this.previousProcessId2 = previousProcessId2;
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
	public Boolean getSameCassette() {
		return sameCassette;
	}
	public void setSameCassette(Boolean sameCassette) {
		this.sameCassette = sameCassette;
	}	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
