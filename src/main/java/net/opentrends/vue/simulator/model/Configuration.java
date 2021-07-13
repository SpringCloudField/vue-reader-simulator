package net.opentrends.vue.simulator.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Document(collection = "configuration")
public class Configuration {

	@Id
	private String id;
	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	private String serialNumber;
	
	@NotNull
	@NotEmpty
	private Boolean busyState;
	@NotNull
	@NotEmpty
	private Boolean cassetteIn;
	@NotNull
	@NotEmpty
	private Double cassetteTime;
	@NotNull
	@NotEmpty
	private String settingsVersion;
	@NotNull
	@NotEmpty
	private String releaseVersion;
	
	@NotNull
	@NotEmpty
	private Integer cassetteTypeId;		
	@NotNull
	@NotEmpty
	private String testType;
	private Boolean sameCassette;
	private Integer cassetteErrorCode;
	private Integer cassetteErrorCode2;
	private Integer processId;
	private Integer processId2;
	private Integer previousProcessId;
	private Integer previousProcessId2;
	private ScanSingle scanSingle;
	private ScanMultiple scanMultiple;
	private ScanDouble scanDouble;

	@LastModifiedDate
	private Date lastModifiedDate; 
	
}
