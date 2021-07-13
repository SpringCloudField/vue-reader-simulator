package net.opentrends.vue.simulator.dto.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import net.opentrends.vue.simulator.dto.ResultTO;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Jacksonized
public class CassetteProcessesResponseTO {
	@JsonProperty("Result")
	private ResultTO resultTO;
	
}
