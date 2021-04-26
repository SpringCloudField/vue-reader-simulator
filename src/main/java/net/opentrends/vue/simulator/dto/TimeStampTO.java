package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeStampTO {
	
	@JsonProperty("Timestamp")
	private DateTimeTO timeStamp;

	public DateTimeTO getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(DateTimeTO timeStamp) {
		this.timeStamp = timeStamp;
	}

}
