package net.opentrends.vue.simulator.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.opentrends.vue.simulator.dto.reponse.CancelTimedScanResponseTO;
import net.opentrends.vue.simulator.dto.reponse.CassetteProcessesResponseTO;
import net.opentrends.vue.simulator.dto.reponse.ConfigReaderResponseTO;
import net.opentrends.vue.simulator.dto.reponse.ImagesResponseTO;
import net.opentrends.vue.simulator.dto.reponse.ReaderDateTimeResponseTO;
import net.opentrends.vue.simulator.dto.reponse.StatusResponseTO;
import net.opentrends.vue.simulator.service.SimulatorService;

@RestController
@RequestMapping("/api/vue/simulator/")
public class SimulatorController {

	private SimulatorService simulatorService;

	public SimulatorController(SimulatorService simulatorService) {
		this.simulatorService = simulatorService;
	}
	
	@ApiOperation(value = "Configuration from VUE reader simulator ")
	@GetMapping("/{serialNumber}/v2.4/config_reader")
	public ResponseEntity<ConfigReaderResponseTO> config(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber, HttpServletRequest request) {
		ConfigReaderResponseTO configResponse = new ConfigReaderResponseTO();
		configResponse.setConfigReaderTO(simulatorService.getConfigReader(serialNumber, request.getServerName()));
		return ResponseEntity.ok(configResponse);
	}

	@ApiOperation(value = "Status from VUE reader simulator ")
	@GetMapping("/{serialNumber}/status")
	public ResponseEntity<StatusResponseTO> readerStatus(
			@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		StatusResponseTO statusResponse = new StatusResponseTO();
		statusResponse.setStatusTO(simulatorService.getStatusConfig(serialNumber));
		return ResponseEntity.ok(statusResponse);		
	}

	@ApiOperation(value = "Reader date and time ")
	@PutMapping("/{serialNumber}/v2.4/reader_date_and_time")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void readerDateAndTime(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		ReaderDateTimeResponseTO timeStampResponse = new ReaderDateTimeResponseTO();
		timeStampResponse.setTimeStamp(simulatorService.getReaderDateAndTime(serialNumber));
	}

	@ApiOperation(value = "Test from VUE reader simulator ")
	@PostMapping("/{serialNumber}/v2.4/cassette_processes")
	public ResponseEntity<CassetteProcessesResponseTO> cassetteProcesses2(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		CassetteProcessesResponseTO cassetteProcessesResponse = new CassetteProcessesResponseTO();
		cassetteProcessesResponse.setResultTO(simulatorService.getCassetteProcesses2(serialNumber));
		return ResponseEntity.ok(cassetteProcessesResponse);
	}

	@ApiOperation(value = "Test from VUE reader simulator ")
	@GetMapping("/{serialNumber}/v2.4/cassette_processes")
	public ResponseEntity<CassetteProcessesResponseTO> cassetteProcesses1(
			@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		CassetteProcessesResponseTO cassetteProcessesResponse = new CassetteProcessesResponseTO();
		cassetteProcessesResponse.setResultTO(simulatorService.getCassetteProcesses(serialNumber));
		return ResponseEntity.ok(cassetteProcessesResponse);
	}

	@ApiOperation(value = "Image test from VUE reader simulator ")
	@GetMapping("/{serialNumber}/v2.4/images/{cassetteProcessId}")
	public ResponseEntity<ImagesResponseTO> images(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber,
			@ApiParam(value = "Cassette process ID") @PathVariable String cassetteProcessId) throws IOException {
		ImagesResponseTO imagesResponseTO = new ImagesResponseTO();
		imagesResponseTO.setImage(simulatorService.getImage(serialNumber));
		return ResponseEntity.ok(imagesResponseTO);
	}

	@ApiOperation(value = "Cancel timed scan ")
	@PutMapping("/{serialNumber}/v2.4/cancel_timed_scan")
	public ResponseEntity<CancelTimedScanResponseTO> cancelTimedScan(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		CancelTimedScanResponseTO to = new CancelTimedScanResponseTO();
		to.setDummy(simulatorService.cancelTimedScan(serialNumber));
		return ResponseEntity.ok(to);
	}

}
