package net.opentrends.vue.simulator.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ConfigReaderTO {
	@JsonProperty("ethernet")
	private EthernetTO ethernetTO;
	@JsonProperty("wifi_ap")
	private WifiApTO wifiApTO;
	@JsonProperty("wifi_mode")
	private WifiModeTO wifimodeTO;
	@JsonProperty("wifi_station")
	private WifiStationTO wifiStationTO;
	
	public EthernetTO getEthernetTO() {
		return ethernetTO;
	}
	public void setEthernetTO(EthernetTO ethernetTO) {
		this.ethernetTO = ethernetTO;
	}
	public WifiApTO getWifiApTO() {
		return wifiApTO;
	}
	public void setWifiApTO(WifiApTO wifiApTO) {
		this.wifiApTO = wifiApTO;
	}
	public WifiModeTO getWifimodeTO() {
		return wifimodeTO;
	}
	public void setWifimodeTO(WifiModeTO wifimodeTO) {
		this.wifimodeTO = wifimodeTO;
	}
	public WifiStationTO getWifiStationTO() {
		return wifiStationTO;
	}
	public void setWifiStationTO(WifiStationTO wifiStationTO) {
		this.wifiStationTO = wifiStationTO;
	}
	
}
