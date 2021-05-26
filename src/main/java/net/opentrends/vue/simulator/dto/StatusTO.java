package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusTO {

	@JsonProperty("device_status")
	private DeviceStatusTO deviceStatus;

	@JsonProperty("error")
	private ErrorTO error;

	public DeviceStatusTO getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(DeviceStatusTO deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public ErrorTO getError() {
		return error;
	}

	public void setError(ErrorTO error) {
		this.error = error;
	}

}
