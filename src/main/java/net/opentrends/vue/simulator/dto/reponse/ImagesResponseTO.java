package net.opentrends.vue.simulator.dto.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentrends.vue.simulator.dto.ImagesTO;

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
