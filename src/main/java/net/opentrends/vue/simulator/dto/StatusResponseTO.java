package net.opentrends.vue.simulator.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
//@JsonRootName(value = "Status")
public class StatusResponseTO {
	
	@JsonProperty("Status")
	private StatusTO statusTO;

	public StatusTO getStatusTO() {
		return statusTO;
	}

	public void setStatusTO(StatusTO statusTO) {
		this.statusTO = statusTO;
	}
	
}
