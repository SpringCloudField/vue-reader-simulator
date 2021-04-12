package net.opentrends.vue.simulator.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class CassetteProcessesResponseTO {
	@JsonProperty("Result")
	private ResultTO resultTO;

	public ResultTO getResultTO() {
		return resultTO;
	}

	public void setResultTO(ResultTO resultTO) {
		this.resultTO = resultTO;
	}
	
}
