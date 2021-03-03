package net.opentrends.vue.simulator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/vue/simulator/")
public class SimulatorController {

	@ApiOperation(value = "Configuration from VUE reader simulator ")
	@GetMapping("/{idSimulator}/v2.4/config_reader")
	public String config(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
	}

	@ApiOperation(value = "Status from VUE reader simulator ")
	@GetMapping("/{idSimulator}/status")
	public String status(
			@ApiParam(value = "Id simulator") 
			@PathVariable String idSimulator) {
		return "statusTO";
	}
	
	@ApiOperation(value = " ")
	@GetMapping("/{idSimulator}/v2.4/reader_date_and_time")
	public String readerDateAndTime(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
	}
	
	@ApiOperation(value = "Test from VUE reader simulator ")
	@PostMapping("/{idSimulator}/v2.4/cassette_processes")
	public String cassetteProcesses1(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
	}
	
	@ApiOperation(value = "Test from VUE reader simulator ")
	@GetMapping("/{idSimulator}/v2.4/cassette_processes")
	public String cassetteProcesses2(@ApiParam(value = "Id simulator") @PathVariable String idSimulator) {
		return "statusTO";
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
