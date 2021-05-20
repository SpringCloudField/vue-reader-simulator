package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WifiModeTO {
	@JsonProperty("wifi_ap")
	private Boolean wifiAp;

	public Boolean getWifiAp() {
		return wifiAp;
	}

	public void setWifiAp(Boolean wifiAp) {
		this.wifiAp = wifiAp;
	}
	
}
