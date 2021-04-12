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
import net.opentrends.vue.simulator.dto.DateTimeResponseTO;
import net.opentrends.vue.simulator.dto.StatusResponseTO;
import net.opentrends.vue.simulator.service.ConfigurationService;

@RestController
@RequestMapping("/api/vue/simulator/")
public class SimulatorController {

	@Autowired
	private ConfigurationService configurationService;
	
	@ApiOperation(value = "Configuration from VUE reader simulator ")
	@GetMapping("/{idSimulator}/v2.4/config_reader")
	public ResponseEntity<?> config(
			@ApiParam(value = "Id simulator")
			@PathVariable String idSimulator) {
		ConfigResponseTO configResponse = new ConfigResponseTO();
		configResponse.setConfigTO(configurationService.getConfig(idSimulator));
		if (configResponse.getConfigTO() == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(configResponse);
		}
	}

	@ApiOperation(value = "Status from VUE reader simulator ")
	@GetMapping("/{idSimulator}/status")
	public ResponseEntity <?> readerStatus(
			@ApiParam(value = "Id simulator") 
			@PathVariable String idSimulator) {
		StatusResponseTO statusResponse = new StatusResponseTO();
		statusResponse.setStatusTO(configurationService.getStatusConfig(idSimulator));
		if (statusResponse.getStatusTO() == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(statusResponse);
		}
	}
	
	@ApiOperation(value = " ")
	@GetMapping("/{idSimulator}/v2.4/reader_date_and_time")
	public ResponseEntity<DateTimeResponseTO> readerDateAndTime(
			@ApiParam(value = "Id simulator") 
			@PathVariable String idSimulator) {
		return ResponseEntity.ok(new DateTimeResponseTO());
	}
	
	@ApiOperation(value = "Test from VUE reader simulator ")
	@PostMapping("/{idSimulator}/v2.4/cassette_processes")
	public String cassetteProcesses1(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
	}
	
	@ApiOperation(value = "Test from VUE reader simulator ")
	@GetMapping("/{idSimulator}/v2.4/cassette_processes")
	public ResponseEntity<?> cassetteProcesses2(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		CassetteProcessesResponseTO  cassetteProcessesResponse = new CassetteProcessesResponseTO();
		cassetteProcessesResponse.setResultTO(configurationService.getCassetteProcesses(idSimulator));
		if (cassetteProcessesResponse.getResultTO() == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(cassetteProcessesResponse);
		}
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

