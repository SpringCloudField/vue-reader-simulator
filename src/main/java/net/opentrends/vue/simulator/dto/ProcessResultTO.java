package net.opentrends.vue.simulator.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class ProcessResultTO {

	private String background;
	@JsonProperty("color_01")
	private String color01;
	@JsonProperty("color_02")
	private String color02;
	private Double control;
	private ErrorTO error;
	private Character initial;
	private Double noise;
	private Boolean positive;
	private Boolean reliable;
	private Integer position;
	@JsonProperty("test_line_value")
	private Double testLineValue;
	@JsonProperty("test_name")
	private String testName;
	private Double scaledResult;	
	private CoeficientsTO coeficients;
	private List<WarningTO> warnings;

}
