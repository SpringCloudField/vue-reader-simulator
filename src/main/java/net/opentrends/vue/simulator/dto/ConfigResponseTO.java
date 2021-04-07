package net.opentrends.vue.simulator.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ConfigResponseTO {
	
	@JsonProperty("Config")
	private ConfigTO configTO;

	public ConfigTO getConfigTO() {
		return configTO;
	}

	public void setConfigTO(ConfigTO configTO) {
		this.configTO = configTO;
	}

}
