package net.opentrends.vue.simulator.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class CassetteProcessesResponseTO {
	@JsonProperty("Result")
	private ResultTO result;

	public ResultTO getResult() {
		return result;
	}

	public void setResult(ResultTO result) {
		this.result = result;
	}
	
}
