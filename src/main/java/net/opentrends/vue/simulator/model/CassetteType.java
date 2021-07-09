package net.opentrends.vue.simulator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "cassetteType")
public class CassetteType {

	@Id
	private String id;
	private String type;	
	private Integer code;

}
