package net.opentrends.vue.simulator.dto.reponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentrends.vue.simulator.dto.StatusTO;

@Component
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
