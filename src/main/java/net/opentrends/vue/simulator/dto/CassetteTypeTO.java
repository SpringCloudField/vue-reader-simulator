package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CassetteTypeTO {
	
	@JsonProperty("description")
	private String type;	
	private Integer code;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "CassetteTypeTO [type=" + type + ", code=" + code + "]";
	}
	
	

}
