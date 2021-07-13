package net.opentrends.vue.simulator.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ResultTO {
	
	@JsonProperty("cassette_process_id")
	private Integer cassetteProcessId;
	
	@JsonProperty("cassette_type")
	private CassetteTypeTO cassetteType;
	
	@JsonProperty("error")
	private ErrorTO error;
	
	@JsonProperty("results")
	private List<ProcessResultTO> processResults;
	
	@JsonProperty("previous_process_id")
	private Integer previousProcessId;
	
	@JsonProperty("reader_data")
	private ReaderDataTO readerData;

	
}
