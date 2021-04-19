package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImagesResponseTO {
	
	@JsonProperty("Image")
	private ImagesTO image;

	public ImagesTO getImage() {
		return image;
	}

	public void setImage(ImagesTO image) {
		this.image = image;
	}
}
