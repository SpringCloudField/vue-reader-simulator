package net.opentrends.vue.simulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	@JsonProperty("test_line_value")
	private Double testLineValue;

	@JsonProperty("test_name")
	private String testName;

	private WarningTO[] warnings;

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getColor01() {
		return color01;
	}

	public void setColor01(String color01) {
		this.color01 = color01;
	}

	public String getColor02() {
		return color02;
	}

	public void setColor02(String color02) {
		this.color02 = color02;
	}

	public Double getControl() {
		return control;
	}

	public void setControl(Double control) {
		this.control = control;
	}

	public ErrorTO getError() {
		return error;
	}

	public void setError(ErrorTO error) {
		this.error = error;
	}

	public Character getInitial() {
		return initial;
	}

	public void setInitial(Character initial) {
		this.initial = initial;
	}

	public Double getNoise() {
		return noise;
	}

	public void setNoise(Double noise) {
		this.noise = noise;
	}

	public Boolean getPositive() {
		return positive;
	}

	public void setPositive(Boolean positive) {
		this.positive = positive;
	}

	public Boolean getReliable() {
		return reliable;
	}

	public void setReliable(Boolean reliable) {
		this.reliable = reliable;
	}

	public Double getTestLineValue() {
		return testLineValue;
	}

	public void setTestLineValue(Double testLineValue) {
		this.testLineValue = testLineValue;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public WarningTO[] getWarnings() {
		return warnings;
	}

	public void setWarnings(WarningTO[] warnings) {
		this.warnings = warnings;
	}

}
