package net.opentrends.vue.simulator.controller;

import java.io.IOException;

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
import net.opentrends.vue.simulator.dto.DateTimeTO;
import net.opentrends.vue.simulator.dto.ImagesResponseTO;
import net.opentrends.vue.simulator.dto.StatusResponseTO;
import net.opentrends.vue.simulator.dto.TimeStampTO;
import net.opentrends.vue.simulator.exception.AppRuntimeException;
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
	public ResponseEntity<TimeStampTO> readerDateAndTime(
			@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {		
		TimeStampTO timeStampResponse = new TimeStampTO();
		DateTimeTO dateTime = new DateTimeTO();
		dateTime.setDateTime("2015-06-17T12:42:02+02:00");
		timeStampResponse.setTimeStamp(dateTime);;
		return ResponseEntity.ok(timeStampResponse);
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
	public ResponseEntity<ImagesResponseTO> images(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) throws AppRuntimeException, IOException {
		ImagesResponseTO imagesResponseTO = new ImagesResponseTO();
		imagesResponseTO.setImage(simulatorService.getImage(serialNumber));
		return ResponseEntity.ok(imagesResponseTO);
	}

	@ApiOperation(value = "Cancel timed scan ")
	@PutMapping("/{serialNumber}/v2.4/cancel_timed_scan")
	public ResponseEntity<?> cancelTimedScan(@ApiParam(value = "VUE Reader Simulator SN") @PathVariable String serialNumber) {
		return ResponseEntity.accepted().build();
	}

}
