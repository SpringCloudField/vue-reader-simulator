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
public class ScanSingleTO {
	
	private Boolean positive;
	private Double control;
	private Double noise;
	private Double testLineValue;
	private Integer lotNumber;
	private Double scaledResult;	
	private Integer testErrorCode;
	
	private Boolean positive2;
	private Double control2;
	private Double noise2;
	private Double testLineValue2;
	private Integer lotNumber2;
	private Double scaledResult2;	
	private Integer testErrorCode2;
	
}
