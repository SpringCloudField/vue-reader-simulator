package net.opentrends.vue.simulator.dto;

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
public class WifiStationTO {
	
	private Boolean dhcp;
	private String gateway;
	private String ip;
	private String netmask;
	private String pwd;
	private String ssid;
	
}
