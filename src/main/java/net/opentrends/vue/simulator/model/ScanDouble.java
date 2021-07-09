package net.opentrends.vue.simulator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ScanDouble {
	
	private Boolean positiveFelv;
	private Double controlFelv;
	private Double noiseFelv;
	private Double testLineValueFelv;
	private Integer testErrorCodeFelv;
	
	private Boolean positiveFiv;
	private Double controlFiv;
	private Double noiseFiv;
	private Double testLineValueFiv;
	private Integer testErrorCodeFiv;
	
	private Boolean positiveFelv2;
	private Double controlFelv2;
	private Double noiseFelv2;
	private Double testLineValueFelv2;
	private Integer testErrorCodeFelv2;
	
	private Boolean positiveFiv2;
	private Double controlFiv2;
	private Double noiseFiv2;
	private Double testLineValueFiv2;
	private Integer testErrorCodeFiv2;
	
}
