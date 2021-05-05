package net.opentrends.vue.simulator.dto.reponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentrends.vue.simulator.dto.ResultTO;

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
