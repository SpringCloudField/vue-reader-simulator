package net.opentrends.vue.simulator.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class ConfigurationTO {

	private String id;
	
	@NotEmpty(message = "Serial_number is required")
	private String serialNumber;

	@NotNull(message = "Busy_state is required")
	private Boolean busyState;
	@NotNull(message = "Cassette_in is required")
	private Boolean cassetteIn;
	@NotNull(message = "Cassette_time is required")
	private Double cassetteTime;
	@NotEmpty(message = "Settings_version is required")
	private String settingsVersion;
	@NotEmpty(message = "Release_version is required")
	private String releaseVersion;

	@NotNull(message = "Cassette_type is required")	
	private Integer cassetteTypeId;
	@NotNull(message = "Test_type is required")	
	private String testType;
	@NotNull(message = "Same_cassette is required")
	private Boolean sameCassette;
	private Integer cassetteErrorCode;
	private Integer cassetteErrorCode2;
	private Integer processId;
	private Integer previousProcessId;
	private Integer processId2;
	private Integer previousProcessId2;
	private ScanSingleTO scanSingle;
	private ScanMultipleTO scanMultiple;
	private ScanDoubleTO scanDouble;
		
}
