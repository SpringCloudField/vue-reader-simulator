package net.opentrends.vue.simulator.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ConfigReaderResponseTO {
	
	@JsonProperty("Config")
	private ConfigReaderTO configReaderTO;

	public ConfigReaderTO getConfigReaderTO() {
		return configReaderTO;
	}

	public void setConfigReaderTO(ConfigReaderTO configReaderTO) {
		this.configReaderTO = configReaderTO;
	}

}