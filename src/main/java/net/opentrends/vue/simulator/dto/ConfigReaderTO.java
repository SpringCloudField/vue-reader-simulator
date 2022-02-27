package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Jacksonized
public class ConfigReaderTO {
	
	private EthernetTO ethernet;
	@JsonProperty("wifi_ap")
	private WifiApTO wifiAp;
	@JsonProperty("wifi_mode")
	private WifiModeTO wifimode;
	@JsonProperty("wifi_station")
	private WifiStationTO wifiStation;
	
}
