package net.opentrends.vue.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.opentrends.vue.simulator.dto.CassetteProcessesResponseTO;
import net.opentrends.vue.simulator.dto.ConfigResponseTO;
import net.opentrends.vue.simulator.dto.StatusResponseTO;
import net.opentrends.vue.simulator.service.ConfigurationService;

@RestController
@RequestMapping("/api/vue/simulator/")
public class SimulatorController {

	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private StatusResponseTO statusResponse;
	
	@Autowired 
	ConfigResponseTO configResponse;
	
	@Autowired
	CassetteProcessesResponseTO cassetteProcessesResponse;
	
	@ApiOperation(value = "Configuration from VUE reader simulator ")
	@GetMapping("/{idSimulator}/v2.4/config_reader")
	public ResponseEntity<?> config(
			@ApiParam(value = "Id simulator")
			@PathVariable String idSimulator) {
		configResponse.setConfigTO(configurationService.getConfig(idSimulator));
		
		return ResponseEntity.ok(configResponse); 
	}

	@ApiOperation(value = "Status from VUE reader simulator ")
	@GetMapping("/{idSimulator}/status")
	public ResponseEntity <?> readerStatus(
			@ApiParam(value = "Id simulator") 
			@PathVariable String idSimulator) {
		
		statusResponse.setStatusTO(configurationService.getStatusConfig(idSimulator));
		if (statusResponse == null) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(statusResponse);
		}
	}
	
	@ApiOperation(value = " ")
	@GetMapping("/{idSimulator}/v2.4/reader_date_and_time")
	public ResponseEntity<DateTimeResponse> readerDateAndTime(
			@ApiParam(value = "Id simulator") 
			@PathVariable String idSimulator) {
		
		return ResponseEntity.ok(new DateTimeResponse());
		
	}
	
	@ApiOperation(value = "Test from VUE reader simulator ")
	@PostMapping("/{idSimulator}/v2.4/cassette_processes")
	public String cassetteProcesses1(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
	}
	
	@ApiOperation(value = "Test from VUE reader simulator ")
	@GetMapping("/{idSimulator}/v2.4/cassette_processes")
	public CassetteProcessesResponseTO cassetteProcesses2(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		cassetteProcessesResponse.setResult(configurationService.getCassetteProcesses(idSimulator));
		
		return cassetteProcessesResponse;
	}
	
	@ApiOperation(value = "Image test from VUE reader simulator ")
	@GetMapping("/{idSimulator}/v2.4/images")
	public String images(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
	}
	
	@ApiOperation(value = "Cancel timed scan ")
	@PutMapping("/{idSimulator}/v2.4/images")
	public String cancelTimedScant(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
	}

}

class DateTimeResponse {
	  public String date_time;
	  DateTimeResponse() {
		  this.date_time = "1997-07-16T19:20+01:00";
	  }
	  
}
