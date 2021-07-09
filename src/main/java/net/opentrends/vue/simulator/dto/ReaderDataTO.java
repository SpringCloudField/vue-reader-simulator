package net.opentrends.vue.simulator.dto;

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
public class ReaderDataTO {
	
	@JsonProperty("cassette_time")
	private Double cassetteTime;
	
	@JsonProperty("date_time")
	private String dateTime;
	
	@JsonProperty("db_version")
	private String dbVersion;
	
	@JsonProperty("release_version")
	private String releaseVersion;
	
	@JsonProperty("settings_version")
	private String settingsVersion;
	
}

