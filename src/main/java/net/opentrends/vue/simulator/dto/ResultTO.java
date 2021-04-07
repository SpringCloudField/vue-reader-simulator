package net.opentrends.vue.simulator.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultTO {
	
	@JsonProperty("cassette_process_id")
	private Integer cassetteProcessId;
	
	@JsonProperty("cassette_type")
	private CassetteTypeTO cassetteType;
	
	@JsonProperty("error")
	private ErrorTO error;
	
	@JsonProperty("results")
	private ArrayList<ProcessResultTO> processResults;
	
	@JsonProperty("previous_process_id")
	private Integer previousProcessId;
	
	@JsonProperty("reader_data")
	private ReaderDataTO readerData;

	public Integer getCassetteProcessId() {
		return cassetteProcessId;
	}

	public void setCassetteProcessId(Integer cassetteProcessId) {
		this.cassetteProcessId = cassetteProcessId;
	}

	public CassetteTypeTO getCassetteType() {
		return cassetteType;
	}

	public void setCassetteType(CassetteTypeTO cassetteType) {
		this.cassetteType = cassetteType;
	}

	public ErrorTO getError() {
		return error;
	}

	public void setError(ErrorTO error) {
		this.error = error;
	}

	public ArrayList<ProcessResultTO> getProcessResults() {
		return processResults;
	}

	public void setProcessResults(ArrayList<ProcessResultTO> processResults) {
		this.processResults = processResults;
	}

	public Integer getPreviousProcessId() {
		return previousProcessId;
	}

	public void setPreviousProcessId(Integer previousProcessId) {
		this.previousProcessId = previousProcessId;
	}

	public ReaderDataTO getReaderData() {
		return readerData;
	}

	public void setReaderData(ReaderDataTO readerData) {
		this.readerData = readerData;
	}
	
	
	
}
