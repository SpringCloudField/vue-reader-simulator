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
import net.opentrends.vue.simulator.dto.ConfigReaderResponseTO;
import net.opentrends.vue.simulator.dto.DateTimeResponseTO;
import net.opentrends.vue.simulator.dto.StatusResponseTO;
import net.opentrends.vue.simulator.service.SimulatorService;

@RestController
@RequestMapping("/api/vue/simulator/")
public class SimulatorController {

	@Autowired
	private SimulatorService simulatorService;

	@ApiOperation(value = "Configuration from VUE reader simulator ")
	@GetMapping("/{serialNumber}/v2.4/config_reader")
	public ResponseEntity<ConfigReaderResponseTO> config(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		ConfigReaderResponseTO configResponse = new ConfigReaderResponseTO();
		configResponse.setConfigReaderTO(simulatorService.getConfigReader(serialNumber));
		if (configResponse.getConfigReaderTO() == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(configResponse);
		}
	}

	@ApiOperation(value = "Status from VUE reader simulator ")
	@GetMapping("/{serialNumber}/status")
	public ResponseEntity<StatusResponseTO> readerStatus(
			@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		StatusResponseTO statusResponse = new StatusResponseTO();
		statusResponse.setStatusTO(simulatorService.getStatusConfig(serialNumber));
		if (statusResponse.getStatusTO() == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(statusResponse);
		}
	}

	@ApiOperation(value = " ")
	@GetMapping("/{serialNumber}/v2.4/reader_date_and_time")
	public ResponseEntity<DateTimeResponseTO> readerDateAndTime(
			@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		return ResponseEntity.ok(new DateTimeResponseTO());
	}

	@ApiOperation(value = "Test from VUE reader simulator ")
	@PostMapping("/{serialNumber}/v2.4/cassette_processes")
	public String cassetteProcesses1(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		return "statusTO";
	}

	@ApiOperation(value = "Test from VUE reader simulator ")
	@GetMapping("/{serialNumber}/v2.4/cassette_processes")
	public ResponseEntity<CassetteProcessesResponseTO> cassetteProcesses2(
			@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		CassetteProcessesResponseTO cassetteProcessesResponse = new CassetteProcessesResponseTO();
		cassetteProcessesResponse.setResultTO(simulatorService.getCassetteProcesses(serialNumber));
		if (cassetteProcessesResponse.getResultTO() == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(cassetteProcessesResponse);
		}
	}

	@ApiOperation(value = "Image test from VUE reader simulator ")
	@GetMapping("/{serialNumber}/v2.4/images")
	public String images(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		return "statusTO";
	}

	@ApiOperation(value = "Cancel timed scan ")
	@PutMapping("/{serialNumber}/v2.4/images")
	public String cancelTimedScant(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		return "statusTO";
	}

}
