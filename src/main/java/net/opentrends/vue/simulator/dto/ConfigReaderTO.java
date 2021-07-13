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
	
	@JsonProperty("ethernet")
	private EthernetTO ethernetTO;
	@JsonProperty("wifi_ap")
	private WifiApTO wifiApTO;
	@JsonProperty("wifi_mode")
	private WifiModeTO wifimodeTO;
	@JsonProperty("wifi_station")
	private WifiStationTO wifiStationTO;
	
}
