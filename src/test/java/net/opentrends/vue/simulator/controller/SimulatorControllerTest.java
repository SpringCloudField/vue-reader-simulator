package net.opentrends.vue.simulator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.DateTimeTO;
import net.opentrends.vue.simulator.dto.DeviceStatusTO;
import net.opentrends.vue.simulator.dto.ErrorTO;
import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.dto.StatusTO;
import net.opentrends.vue.simulator.dto.reponse.CancelTimedScanResponseTO;
import net.opentrends.vue.simulator.dto.reponse.ConfigReaderResponseTO;
import net.opentrends.vue.simulator.dto.reponse.ImagesResponseTO;
import net.opentrends.vue.simulator.dto.reponse.ReaderDateTimeResponseTO;
import net.opentrends.vue.simulator.dto.reponse.StatusResponseTO;
import net.opentrends.vue.simulator.service.SimulatorService;
import net.opentrends.vue.simulator.utils.DefaultParams;

@ExtendWith(SpringExtension.class)
public class SimulatorControllerTest {
	
	@Mock
	private SimulatorService simulatorService;
	@InjectMocks
	private SimulatorController simulatorController;
	
	@Test
	public void configTest() {
		when(simulatorService.getConfigReader(any(), any())).thenReturn(new ConfigReaderTO());
		ResponseEntity<ConfigReaderResponseTO> response = simulatorController.config("serial_number", new MockHttpServletRequest());
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void cancelTimedScanTest() {
		when(simulatorService.cancelTimedScan(any())).thenReturn("cancel");
		ResponseEntity<CancelTimedScanResponseTO> response = simulatorController.cancelTimedScan("serial_number");
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("cancel", response.getBody().getDummy());
	}
	
	@Test
	public void imagesTest() throws IOException {
		ImagesTO imageTo = new ImagesTO();
		imageTo.setId(1);
		imageTo.setImage("image".getBytes());
		when(simulatorService.getImage(any())).thenReturn(imageTo);
		
		ResponseEntity<ImagesResponseTO> response = simulatorController.images("serial_number");
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getImage().getId());
		assertNotNull(response.getBody().getImage().getImage());
	}
	
	@Test
	public void readerDateAndTimeTest() {
		DateTimeTO dateTimeTO = new DateTimeTO();
		dateTimeTO.setDateTime("date_time");
		
		when(simulatorService.getReaderDateAndTime(any())).thenReturn(dateTimeTO);
		ResponseEntity<ReaderDateTimeResponseTO> response = simulatorController.readerDateAndTime("serial_number");
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("date_time", response.getBody().getTimeStamp().getDateTime());
	}
	
	@Test
	public void readerStatusNoCassetteErrorTest() {
		DeviceStatusTO deviceStatusTO = new DeviceStatusTO();
		deviceStatusTO.setBusyState(Boolean.TRUE);
		deviceStatusTO.setCassetteIn(Boolean.FALSE);
		deviceStatusTO.setCassetteTime(2.2);
		deviceStatusTO.setDbVersion(4);
		deviceStatusTO.setDateTime("date_time");
		deviceStatusTO.setPlatform("platform");
		deviceStatusTO.setReleaseVersion("1.0");
		deviceStatusTO.setSerialNumber("serial_number");
		deviceStatusTO.setSettingsVersion("2.0");
		
		ErrorTO errorTO = new ErrorTO();
		errorTO.setCode(0);
		errorTO.setDescription(DefaultParams.SUCCESS);
		
		StatusTO statusTo = new StatusTO();
		statusTo.setDeviceStatus(deviceStatusTO);
		statusTo.setError(errorTO);
		
		when(simulatorService.getStatusConfig(any())).thenReturn(statusTo);
		ResponseEntity<StatusResponseTO> response = simulatorController.readerStatus("serial_number");
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deviceStatusTO.getBusyState(), response.getBody().getStatusTO().getDeviceStatus().getBusyState());
		assertEquals(deviceStatusTO.getCassetteIn(), response.getBody().getStatusTO().getDeviceStatus().getCassetteIn());
		assertEquals(deviceStatusTO.getCassetteTime(), response.getBody().getStatusTO().getDeviceStatus().getCassetteTime());
		assertEquals(deviceStatusTO.getDbVersion(), response.getBody().getStatusTO().getDeviceStatus().getDbVersion());
		assertEquals(deviceStatusTO.getDateTime(), response.getBody().getStatusTO().getDeviceStatus().getDateTime());		
		assertEquals(deviceStatusTO.getPlatform(), response.getBody().getStatusTO().getDeviceStatus().getPlatform());
		assertEquals(deviceStatusTO.getReleaseVersion(), response.getBody().getStatusTO().getDeviceStatus().getReleaseVersion());
		assertEquals(deviceStatusTO.getSerialNumber(), response.getBody().getStatusTO().getDeviceStatus().getSerialNumber());
		assertEquals(deviceStatusTO.getSettingsVersion(), response.getBody().getStatusTO().getDeviceStatus().getSettingsVersion());
		assertEquals(errorTO.getCode(), response.getBody().getStatusTO().getError().getCode());
		assertEquals(errorTO.getDescription(), response.getBody().getStatusTO().getError().getDescription());
	}
	
	@Test
	public void readerStatusWithCassetteErrorTest() {
		DeviceStatusTO deviceStatusTO = new DeviceStatusTO();
		deviceStatusTO.setBusyState(Boolean.TRUE);
		deviceStatusTO.setCassetteIn(Boolean.FALSE);
		deviceStatusTO.setCassetteTime(2.2);
		deviceStatusTO.setDbVersion(4);
		deviceStatusTO.setDateTime("date_time");
		deviceStatusTO.setPlatform("platform");
		deviceStatusTO.setReleaseVersion("1.0");
		deviceStatusTO.setSerialNumber("serial_number");
		deviceStatusTO.setSettingsVersion("2.0");
		
		ErrorTO errorTO = new ErrorTO();
		errorTO.setCode(100);
		errorTO.setDescription("Error 0");
		
		StatusTO statusTo = new StatusTO();
		statusTo.setDeviceStatus(deviceStatusTO);
		statusTo.setError(errorTO);
		
		when(simulatorService.getStatusConfig(any())).thenReturn(statusTo);
		ResponseEntity<StatusResponseTO> response = simulatorController.readerStatus("serial_number");
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deviceStatusTO.getBusyState(), response.getBody().getStatusTO().getDeviceStatus().getBusyState());
		assertEquals(deviceStatusTO.getCassetteIn(), response.getBody().getStatusTO().getDeviceStatus().getCassetteIn());
		assertEquals(deviceStatusTO.getCassetteTime(), response.getBody().getStatusTO().getDeviceStatus().getCassetteTime());
		assertEquals(deviceStatusTO.getDbVersion(), response.getBody().getStatusTO().getDeviceStatus().getDbVersion());
		assertEquals(deviceStatusTO.getDateTime(), response.getBody().getStatusTO().getDeviceStatus().getDateTime());		
		assertEquals(deviceStatusTO.getPlatform(), response.getBody().getStatusTO().getDeviceStatus().getPlatform());
		assertEquals(deviceStatusTO.getReleaseVersion(), response.getBody().getStatusTO().getDeviceStatus().getReleaseVersion());
		assertEquals(deviceStatusTO.getSerialNumber(), response.getBody().getStatusTO().getDeviceStatus().getSerialNumber());
		assertEquals(deviceStatusTO.getSettingsVersion(), response.getBody().getStatusTO().getDeviceStatus().getSettingsVersion());
		assertEquals(errorTO.getCode(), response.getBody().getStatusTO().getError().getCode());
		assertEquals(errorTO.getDescription(), response.getBody().getStatusTO().getError().getDescription());
	}
	
	@Test
	public void cassetteProcesses2Test() {
	}
	
	@Test
	public void cassetteProcessesTest() {
	}

}
