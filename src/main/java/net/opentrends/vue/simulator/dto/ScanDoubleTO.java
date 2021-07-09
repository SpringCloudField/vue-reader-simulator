package net.opentrends.vue.simulator.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Jacksonized
public class ScanDoubleTO {
	
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
