package net.opentrends.vue.simulator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ScanSingle {
	
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
