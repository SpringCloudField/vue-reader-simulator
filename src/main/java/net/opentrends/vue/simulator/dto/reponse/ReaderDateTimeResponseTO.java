package net.opentrends.vue.simulator.dto.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentrends.vue.simulator.dto.DateTimeTO;

public class ReaderDateTimeResponseTO {
	
	@JsonProperty("Timestamp")
	private DateTimeTO timeStamp;

	public DateTimeTO getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(DateTimeTO timeStamp) {
		this.timeStamp = timeStamp;
	}

}
