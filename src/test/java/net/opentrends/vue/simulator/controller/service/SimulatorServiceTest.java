package net.opentrends.vue.simulator.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.dto.StatusTO;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.impl.SimulatorServiceImpl;
import net.opentrends.vue.simulator.utils.DefaultParams;

@ExtendWith(SpringExtension.class)
public class SimulatorServiceTest {
	
	@Mock
	private ConfigurationService configurationService;	
	@Mock
	private CassetteTypeService cassetteTypeService;	
	@InjectMocks
	private SimulatorServiceImpl simulatorService;
	
	@Test
	public void getImagesTest() throws IOException {
		ImagesTO to = simulatorService.getImage("serial_number");
		assertNotNull(to);
		assertNotNull(to.getImage());
		assertEquals(1, to.getId());
	}
	
	@Test
	public void getConfigReaderTest() throws IOException {
		ConfigurationTO confTO = createConfigurationTO(false);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		
		ConfigReaderTO responseTO = simulatorService.getConfigReader("serial_number");
		assertNotNull(responseTO);
		assertEquals(confTO.getEthernetIp(), responseTO.getEthernetTO().getIp());
		assertEquals(1, responseTO.getEthernetTO().getDhcp());
		assertEquals(DefaultParams.GATEWAY, responseTO.getEthernetTO().getGateway());
		assertEquals(DefaultParams.IP, responseTO.getWifiApTO().getIp());
		assertEquals(DefaultParams.MASK, responseTO.getWifiApTO().getNetmask());
		assertEquals(DefaultParams.PWD, responseTO.getWifiApTO().getPwd());
		assertEquals(DefaultParams.SSID, responseTO.getWifiApTO().getSsid());
		assertEquals(Boolean.TRUE, responseTO.getWifimodeTO().getWifiAp());
		assertEquals(Boolean.TRUE, responseTO.getWifiStationTO().getDhcp());
		assertEquals(DefaultParams.IP, responseTO.getWifiStationTO().getIp());
		assertEquals(DefaultParams.GATEWAY, responseTO.getWifiStationTO().getGateway());
		assertEquals(DefaultParams.MASK, responseTO.getWifiStationTO().getNetmask());
		assertEquals(DefaultParams.PWD, responseTO.getWifiStationTO().getPwd());
		assertEquals(DefaultParams.SSID, responseTO.getWifiStationTO().getSsid());
	}
	
	@Test
	public void getStatusReaderTestNoCassetteError() throws IOException {
		ConfigurationTO confTO = createConfigurationTO(false);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		
		StatusTO responseTO = simulatorService.getStatusConfig("serial_number");
		assertNotNull(responseTO);
		assertEquals(confTO.getSerialNumber(), responseTO.getDeviceStatus().getSerialNumber());		
		assertEquals(confTO.getBusyState(), responseTO.getDeviceStatus().getBusyState());
		assertEquals(confTO.getCassetteIn(), responseTO.getDeviceStatus().getCassetteIn());
		assertEquals(confTO.getCassetteTime().toString(), responseTO.getDeviceStatus().getCassetteTime());
		assertEquals(confTO.getReleaseVersion(), responseTO.getDeviceStatus().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), responseTO.getDeviceStatus().getSettingsVersion());
		assertEquals(0, responseTO.getError().getCode());
		assertEquals("success", responseTO.getError().getDescription());		
	}
	
	@Test
	public void getStatusReaderTestWithCassetteError() throws IOException {
		ConfigurationTO confTO = createConfigurationTO(true);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		
		StatusTO responseTO = simulatorService.getStatusConfig("serial_number");
		assertNotNull(responseTO);
		assertEquals(confTO.getSerialNumber(), responseTO.getDeviceStatus().getSerialNumber());		
		assertEquals(confTO.getBusyState(), responseTO.getDeviceStatus().getBusyState());
		assertEquals(confTO.getCassetteIn(), responseTO.getDeviceStatus().getCassetteIn());
		assertEquals(confTO.getCassetteTime().toString(), responseTO.getDeviceStatus().getCassetteTime());
		assertEquals(confTO.getReleaseVersion(), responseTO.getDeviceStatus().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), responseTO.getDeviceStatus().getSettingsVersion());
		assertEquals(120, responseTO.getError().getCode());
		assertEquals("Error 120", responseTO.getError().getDescription());
		
	}
	
	@Test
	public void scanSingleTest() throws IOException {
		
	}
	
	@Test
	public void scanCplTest() throws IOException {		
		
	}
	
	private ConfigurationTO createConfigurationTO(boolean cassetteErrorConfigured) {		
		ConfigurationTO confTO = new ConfigurationTO();
		confTO.setSerialNumber("serial_number");
		confTO.setEthernetIp("192.168.133.11");
		
		confTO.setBusyState(Boolean.TRUE);
		confTO.setCassetteIn(11);
		confTO.setCassetteTime(2D);
		confTO.setReleaseVersion("1.2.3");
		confTO.setSettingsVersion("3.2.1");
		if (cassetteErrorConfigured) {
			confTO.setCassetteErrorCode(120);
		}
		return confTO;
	}
	
	// TODO: more JU cases

}
