package net.opentrends.vue.simulator.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import net.opentrends.vue.simulator.model.ScanDouble;
import net.opentrends.vue.simulator.model.ScanMultiple;
import net.opentrends.vue.simulator.model.ScanSingle;

public class ConfigurationTO {

	private String id;
	
	// Config
	@NotEmpty(message = "Serial_number is required")
	private String serialNumber;	
	@NotEmpty(message = "Ethernet_ip is required")
	private String ethernetIp;

	// Status
	@NotNull
	private Boolean busyState;
	@NotNull(message = "Cassette_in is required")
	private Integer cassetteIn;
	@NotNull(message = "Cassette_in is required")
	private Double cassetteTime;
	@NotEmpty(message = "Settings_version is required")
	private String settingsVersion;
	@NotEmpty(message = "Release_version is required")
	private String releaseVersion;

	// Scan
	@NotNull(message = "Cassette_type is required")	
	private Integer cassetteTypeId;
	@NotNull(message = "Test_type is required")	
	private String testType;
	private Integer cassetteErrorCode;
	private Integer processId;
	private Integer previousProcessId;
	private ScanSingle scanSingle;
	private ScanMultiple scanMultiple;
	private ScanDouble scanDouble;
		

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

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}
}
