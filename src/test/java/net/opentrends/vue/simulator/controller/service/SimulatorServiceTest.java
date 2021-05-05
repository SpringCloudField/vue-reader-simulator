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

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.ScanDoubleTO;
import net.opentrends.vue.simulator.dto.ScanMultipleTO;
import net.opentrends.vue.simulator.dto.ScanSingleTO;
import net.opentrends.vue.simulator.dto.StatusTO;
import net.opentrends.vue.simulator.exception.AppRuntimeException;
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
	public void getImagesTest() throws AppRuntimeException, IOException {
		ImagesTO to = simulatorService.getImage("serial_number");
		assertNotNull(to);
		assertNotNull(to.getImage());
		assertEquals(1, to.getId());
	}
	
	@Test
	public void getConfigReaderTest() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(false, 0, false);
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
	public void getStatusReaderTestNoCassetteError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(false, 0, false);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		
		StatusTO responseTO = simulatorService.getStatusConfig("serial_number");
		assertNotNull(responseTO);
		assertEquals(confTO.getSerialNumber(), responseTO.getDeviceStatus().getSerialNumber());		
		assertEquals(confTO.getBusyState(), responseTO.getDeviceStatus().getBusyState());
		assertEquals(confTO.getCassetteIn(), responseTO.getDeviceStatus().getCassetteIn());
		assertEquals(confTO.getCassetteTime(), responseTO.getDeviceStatus().getCassetteTime());
		assertEquals(confTO.getReleaseVersion(), responseTO.getDeviceStatus().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), responseTO.getDeviceStatus().getSettingsVersion());
		assertEquals(0, responseTO.getError().getCode());
		assertEquals("success", responseTO.getError().getDescription());		
	}
	
	@Test
	public void getStatusReaderTestWithCassetteError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(true, 0, false);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		
		StatusTO responseTO = simulatorService.getStatusConfig("serial_number");
		assertNotNull(responseTO);
		assertEquals(confTO.getSerialNumber(), responseTO.getDeviceStatus().getSerialNumber());		
		assertEquals(confTO.getBusyState(), responseTO.getDeviceStatus().getBusyState());
		assertEquals(confTO.getCassetteIn(), responseTO.getDeviceStatus().getCassetteIn());
		assertEquals(confTO.getCassetteTime(), responseTO.getDeviceStatus().getCassetteTime());
		assertEquals(confTO.getReleaseVersion(), responseTO.getDeviceStatus().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), responseTO.getDeviceStatus().getSettingsVersion());
		assertEquals(120, responseTO.getError().getCode());
		assertEquals("Error 120", responseTO.getError().getDescription());
		
	}
	
	@Test
	public void scanSingleTestWithoutError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(false, 1, false);
		CassetteTypeTO cassTO = createCassetteTypeTO(1);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(0, resultTO.getError().getCode());
		assertEquals("success", resultTO.getError().getDescription());
		assertEquals(cassTO.getType(),resultTO.getCassetteType().getType());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		defaultParamsTest (resultTO, 0);
		assertEquals(confTO.getScanSingle().getPositive(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanSingle().getTestErrorCode(), resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals(confTO.getScanSingle().getControl(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanSingle().getNoise(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanSingle().getTestLineValue(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(cassTO.getType(), resultTO.getProcessResults().get(0).getTestName());
		assertEquals(0, resultTO.getProcessResults().get(0).getWarnings().size());
	}
	
	@Test
	public void scanSingleTestWithCassetteError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(true, 1, false);
		CassetteTypeTO cassTO = createCassetteTypeTO(1);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(120, resultTO.getError().getCode());
		assertEquals("Error 120", resultTO.getError().getDescription());
		assertEquals(cassTO.getType(),resultTO.getCassetteType().getType());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		defaultParamsTest (resultTO, 0);
		assertEquals(confTO.getScanSingle().getPositive(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(confTO.getScanSingle().getTestErrorCode(), resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals(confTO.getScanSingle().getControl(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanSingle().getNoise(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanSingle().getTestLineValue(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(cassTO.getType(), resultTO.getProcessResults().get(0).getTestName());
		assertEquals(0, resultTO.getProcessResults().get(0).getWarnings().size());
	}
	
	@Test
	public void scanSingleTestWithCassetteAndTestError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(true, 1, true);
		CassetteTypeTO cassTO = createCassetteTypeTO(1);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(120, resultTO.getError().getCode());
		assertEquals("Error 120", resultTO.getError().getDescription());
		assertEquals(cassTO.getType(),resultTO.getCassetteType().getType());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		defaultParamsTest (resultTO, 0);
		assertEquals(confTO.getScanSingle().getPositive(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(150, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("Error 150", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanSingle().getControl(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanSingle().getNoise(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanSingle().getTestLineValue(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(cassTO.getType(), resultTO.getProcessResults().get(0).getTestName());
		assertEquals(0, resultTO.getProcessResults().get(0).getWarnings().size());
	}
	
	@Test
	public void scanDoubleTestNoCassetteError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(false, 2, false);
		CassetteTypeTO cassTO = createCassetteTypeTO(2);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		//common data
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(0, resultTO.getError().getCode());
		assertEquals("success", resultTO.getError().getDescription());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		// FeLV data
		defaultParamsTest(resultTO, 0);
		assertEquals(confTO.getScanDouble().getPositiveFelv(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanDouble().getControlFelv(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanDouble().getNoiseFelv(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanDouble().getTestLineValueFelv(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(DefaultParams.FELV, resultTO.getProcessResults().get(0).getTestName());
		//FIV data
		defaultParamsTest(resultTO, 1);
		assertEquals(confTO.getScanDouble().getPositiveFiv(), resultTO.getProcessResults().get(1).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(1).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(1).getError().getDescription());
		assertEquals(confTO.getScanDouble().getControlFiv(), resultTO.getProcessResults().get(1).getControl());
		assertEquals(confTO.getScanDouble().getNoiseFiv(), resultTO.getProcessResults().get(1).getNoise());
		assertEquals(confTO.getScanDouble().getTestLineValueFiv(), resultTO.getProcessResults().get(1).getTestLineValue());
		assertEquals(2, resultTO.getProcessResults().get(1).getPosition());
		assertEquals(DefaultParams.FIV, resultTO.getProcessResults().get(1).getTestName());
		
	}
	
	@Test
	public void scanDoubleTestWithCassetteError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(true, 2, false);
		CassetteTypeTO cassTO = createCassetteTypeTO(2);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		//common data
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(120, resultTO.getError().getCode());
		assertEquals("Error 120", resultTO.getError().getDescription());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		// FeLV data
		defaultParamsTest(resultTO, 0);
		assertEquals(confTO.getScanDouble().getPositiveFelv(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(confTO.getScanDouble().getTestErrorCodeFelv(), resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals(confTO.getScanDouble().getControlFelv(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanDouble().getNoiseFelv(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanDouble().getTestLineValueFelv(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(DefaultParams.FELV, resultTO.getProcessResults().get(0).getTestName());
		//FIV data
		defaultParamsTest(resultTO, 1);
		assertEquals(confTO.getScanDouble().getPositiveFiv(), resultTO.getProcessResults().get(1).getPositive());
		assertEquals(confTO.getScanDouble().getTestErrorCodeFiv(), resultTO.getProcessResults().get(1).getError().getCode());
		assertEquals(confTO.getScanDouble().getControlFiv(), resultTO.getProcessResults().get(1).getControl());
		assertEquals(confTO.getScanDouble().getNoiseFiv(), resultTO.getProcessResults().get(1).getNoise());
		assertEquals(confTO.getScanDouble().getTestLineValueFiv(), resultTO.getProcessResults().get(1).getTestLineValue());
		assertEquals(2, resultTO.getProcessResults().get(1).getPosition());
		assertEquals(DefaultParams.FIV, resultTO.getProcessResults().get(1).getTestName());
		
	}
	
	@Test
	public void scanDoubleTestWithCassetteAndTestError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(true, 2, true);
		CassetteTypeTO cassTO = createCassetteTypeTO(2);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		//common data
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(120, resultTO.getError().getCode());
		assertEquals("Error 120", resultTO.getError().getDescription());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		// FeLV data
		defaultParamsTest(resultTO, 0);
		assertEquals(confTO.getScanDouble().getPositiveFelv(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(150, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("Error 150", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanDouble().getControlFelv(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanDouble().getNoiseFelv(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanDouble().getTestLineValueFelv(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(DefaultParams.FELV, resultTO.getProcessResults().get(0).getTestName());
		//FIV data
		defaultParamsTest(resultTO, 1);
		assertEquals(confTO.getScanDouble().getPositiveFiv(), resultTO.getProcessResults().get(1).getPositive());
		assertEquals(confTO.getScanDouble().getTestErrorCodeFiv(), resultTO.getProcessResults().get(1).getError().getCode());
		assertEquals(confTO.getScanDouble().getControlFiv(), resultTO.getProcessResults().get(1).getControl());
		assertEquals(confTO.getScanDouble().getNoiseFiv(), resultTO.getProcessResults().get(1).getNoise());
		assertEquals(confTO.getScanDouble().getTestLineValueFiv(), resultTO.getProcessResults().get(1).getTestLineValue());
		assertEquals(2, resultTO.getProcessResults().get(1).getPosition());
		assertEquals(DefaultParams.FIV, resultTO.getProcessResults().get(1).getTestName());
		
	}
	
		
	@Test
	public void scanCplTestNoCassetteError() throws IOException {
		ConfigurationTO confTO = createConfigurationTO(false, 8, false);
		CassetteTypeTO cassTO = createCassetteTypeTO(8);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(0, resultTO.getError().getCode());
		assertEquals("success", resultTO.getError().getDescription());
		assertEquals(cassTO.getType(),resultTO.getCassetteType().getType());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		defaultParamsTest(resultTO, 0);
		assertEquals(confTO.getScanSingle().getPositive(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanSingle().getControl(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanSingle().getNoise(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanSingle().getLotNumber(), resultTO.getProcessResults().get(0).getLotNumber());
		assertEquals(confTO.getScanSingle().getScaledResult(), resultTO.getProcessResults().get(0).getScaledResult());
		assertEquals(confTO.getScanSingle().getTestLineValue(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(cassTO.getType(), resultTO.getProcessResults().get(0).getTestName());
		assertEquals(0, resultTO.getProcessResults().get(0).getWarnings().size());
		assertEquals(2, resultTO.getProcessResults().get(0).getCoeficients().getA());
		assertEquals(1, resultTO.getProcessResults().get(0).getCoeficients().getB());
		assertEquals(3, resultTO.getProcessResults().get(0).getCoeficients().getC());
		assertEquals(1, resultTO.getProcessResults().get(0).getCoeficients().getD());
		assertEquals(1, resultTO.getProcessResults().get(0).getCoeficients().getF());
		
	}
	
	@Test
	public void scanCplTestWithCassetteAndTestError() throws IOException {
		ConfigurationTO confTO = createConfigurationTO(true, 8, true);
		CassetteTypeTO cassTO = createCassetteTypeTO(8);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(120, resultTO.getError().getCode());
		assertEquals("Error 120", resultTO.getError().getDescription());
		assertEquals(cassTO.getType(),resultTO.getCassetteType().getType());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		defaultParamsTest(resultTO, 0);
		assertEquals(confTO.getScanSingle().getPositive(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(150, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("Error 150", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanSingle().getControl(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanSingle().getNoise(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanSingle().getLotNumber(), resultTO.getProcessResults().get(0).getLotNumber());
		assertEquals(confTO.getScanSingle().getScaledResult(), resultTO.getProcessResults().get(0).getScaledResult());
		assertEquals(confTO.getScanSingle().getTestLineValue(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(cassTO.getType(), resultTO.getProcessResults().get(0).getTestName());
		assertEquals(0, resultTO.getProcessResults().get(0).getWarnings().size());
		assertEquals(2, resultTO.getProcessResults().get(0).getCoeficients().getA());
		assertEquals(1, resultTO.getProcessResults().get(0).getCoeficients().getB());
		assertEquals(3, resultTO.getProcessResults().get(0).getCoeficients().getC());
		assertEquals(1, resultTO.getProcessResults().get(0).getCoeficients().getD());
		assertEquals(1, resultTO.getProcessResults().get(0).getCoeficients().getF());
		
	}
	
	
	
	@Test
	public void scanMultipleTestNoCassetteError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(false, 9, false);
		CassetteTypeTO cassTO = createCassetteTypeTO(9);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		//common data
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(0, resultTO.getError().getCode());
		assertEquals("success", resultTO.getError().getDescription());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		// Anaplasma data
		defaultParamsTest(resultTO, 0);
		assertEquals(confTO.getScanMultiple().getPositiveAnaplasma(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlAnaplasma(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseAnaplasma(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueAnaplasma(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(DefaultParams.ANAPLASMA, resultTO.getProcessResults().get(0).getTestName());
		//Ehrlichia data
		defaultParamsTest(resultTO, 1);
		assertEquals(confTO.getScanMultiple().getPositiveEhrlichia(), resultTO.getProcessResults().get(1).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(1).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(1).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlEhrlichia(), resultTO.getProcessResults().get(1).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseEhrlichia(), resultTO.getProcessResults().get(1).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueEhrlichia(), resultTO.getProcessResults().get(1).getTestLineValue());
		assertEquals(2, resultTO.getProcessResults().get(1).getPosition());
		assertEquals(DefaultParams.EHRLICHIA, resultTO.getProcessResults().get(1).getTestName());
		//Lyme
		defaultParamsTest(resultTO, 2);
		assertEquals(confTO.getScanMultiple().getPositiveLyme(), resultTO.getProcessResults().get(2).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(2).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(2).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlLyme(), resultTO.getProcessResults().get(2).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseLyme(), resultTO.getProcessResults().get(2).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueLyme(), resultTO.getProcessResults().get(2).getTestLineValue());
		assertEquals(3, resultTO.getProcessResults().get(2).getPosition());
		assertEquals(DefaultParams.LYME, resultTO.getProcessResults().get(2).getTestName());
		//Heartworm
		defaultParamsTest(resultTO, 3);
		assertEquals(confTO.getScanMultiple().getPositiveHeartworm(), resultTO.getProcessResults().get(3).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(3).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(3).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlHeartworm(), resultTO.getProcessResults().get(3).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseHeartworm(), resultTO.getProcessResults().get(3).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueHeartworm(), resultTO.getProcessResults().get(3).getTestLineValue());
		assertEquals(4, resultTO.getProcessResults().get(3).getPosition());
		assertEquals(DefaultParams.HEARTWORM, resultTO.getProcessResults().get(3).getTestName());

	}
	
	@Test
	public void scanMultipleTestWithCassetteAndTestError() throws AppRuntimeException {
		ConfigurationTO confTO = createConfigurationTO(true, 9, true);
		CassetteTypeTO cassTO = createCassetteTypeTO(9);
		when(configurationService.getConfigBySerialNumber(any())).thenReturn(confTO);
		when(cassetteTypeService.getCassetteTypeByCode(any())).thenReturn(cassTO);
		
		ResultTO resultTO = simulatorService.getCassetteProcesses("serial_number");
		assertNotNull(resultTO);
		//common data
		assertEquals(confTO.getProcessId(), resultTO.getCassetteProcessId());
		assertEquals(120, resultTO.getError().getCode());
		assertEquals("Error 120", resultTO.getError().getDescription());
		assertEquals(confTO.getCassetteTime(), resultTO.getReaderData().getCassetteTime());
		assertEquals("2020-11-15 11:29:59", resultTO.getReaderData().getDateTime());
		assertEquals(confTO.getReleaseVersion(), resultTO.getReaderData().getReleaseVersion());
		assertEquals(confTO.getSettingsVersion(), resultTO.getReaderData().getSettingsVersion());
		// Anaplasma data
		defaultParamsTest(resultTO, 0);
		assertEquals(confTO.getScanMultiple().getPositiveAnaplasma(), resultTO.getProcessResults().get(0).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(0).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(0).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlAnaplasma(), resultTO.getProcessResults().get(0).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseAnaplasma(), resultTO.getProcessResults().get(0).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueAnaplasma(), resultTO.getProcessResults().get(0).getTestLineValue());
		assertEquals(1, resultTO.getProcessResults().get(0).getPosition());
		assertEquals(DefaultParams.ANAPLASMA, resultTO.getProcessResults().get(0).getTestName());
		//Ehrlichia data
		defaultParamsTest(resultTO, 1);
		assertEquals(confTO.getScanMultiple().getPositiveEhrlichia(), resultTO.getProcessResults().get(1).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(1).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(1).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlEhrlichia(), resultTO.getProcessResults().get(1).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseEhrlichia(), resultTO.getProcessResults().get(1).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueEhrlichia(), resultTO.getProcessResults().get(1).getTestLineValue());
		assertEquals(2, resultTO.getProcessResults().get(1).getPosition());
		assertEquals(DefaultParams.EHRLICHIA, resultTO.getProcessResults().get(1).getTestName());
		//Lyme
		defaultParamsTest(resultTO, 2);
		assertEquals(confTO.getScanMultiple().getPositiveLyme(), resultTO.getProcessResults().get(2).getPositive());
		assertEquals(150, resultTO.getProcessResults().get(2).getError().getCode());
		assertEquals("Error 150", resultTO.getProcessResults().get(2).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlLyme(), resultTO.getProcessResults().get(2).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseLyme(), resultTO.getProcessResults().get(2).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueLyme(), resultTO.getProcessResults().get(2).getTestLineValue());
		assertEquals(3, resultTO.getProcessResults().get(2).getPosition());
		assertEquals(DefaultParams.LYME, resultTO.getProcessResults().get(2).getTestName());
		//Heartworm
		defaultParamsTest(resultTO, 3);
		assertEquals(confTO.getScanMultiple().getPositiveHeartworm(), resultTO.getProcessResults().get(3).getPositive());
		assertEquals(0, resultTO.getProcessResults().get(3).getError().getCode());
		assertEquals("success", resultTO.getProcessResults().get(3).getError().getDescription());
		assertEquals(confTO.getScanMultiple().getControlHeartworm(), resultTO.getProcessResults().get(3).getControl());
		assertEquals(confTO.getScanMultiple().getNoiseHeartworm(), resultTO.getProcessResults().get(3).getNoise());
		assertEquals(confTO.getScanMultiple().getTestLineValueHeartworm(), resultTO.getProcessResults().get(3).getTestLineValue());
		assertEquals(4, resultTO.getProcessResults().get(3).getPosition());
		assertEquals(DefaultParams.HEARTWORM, resultTO.getProcessResults().get(3).getTestName());

	}
	
	
		
	
	private ConfigurationTO createConfigurationTO(boolean cassetteErrorConfigured, int testCode, boolean testErrorConfigured) {		
		ConfigurationTO confTO = new ConfigurationTO();
		
		confTO.setProcessId(111);
		confTO.setSerialNumber("serial_number");
		confTO.setEthernetIp("192.168.133.11");
		confTO.setBusyState(Boolean.TRUE);
		confTO.setCassetteIn(Boolean.TRUE);
		confTO.setCassetteTime(2D);
		confTO.setReleaseVersion("1.2.3");
		confTO.setSettingsVersion("3.2.1");
		confTO.setTestType("Quick");
		confTO.setSameCassette(Boolean.FALSE);
		if (cassetteErrorConfigured) {
			confTO.setCassetteErrorCode(120);
		}else {
			confTO.setCassetteErrorCode(0);
		}
		
		switch (testCode) {
		
		case 1: //Single test except cPL
			ScanSingleTO scanSingleTO = new ScanSingleTO();
			confTO.setCassetteTypeId(1);
			scanSingleTO.setPositive(Boolean.TRUE);
			scanSingleTO.setControl(99999D);
			scanSingleTO.setNoise(123123D);
			scanSingleTO.setTestLineValue(123123D);
			Integer setSingleErrorCode = (testErrorConfigured) ? 150:0;
			scanSingleTO.setTestErrorCode(setSingleErrorCode);
			confTO.setScanSingle(scanSingleTO);
			break;
			
		case 2: //FeLV FIV
			ScanDoubleTO scanDoubleTO = new ScanDoubleTO();
			confTO.setCassetteTypeId(2);
			scanDoubleTO.setPositiveFelv(Boolean.TRUE);
			scanDoubleTO.setControlFelv(22222D);
			scanDoubleTO.setNoiseFelv(234234D);
			scanDoubleTO.setTestLineValueFelv(324234D);
			scanDoubleTO.setTestLineValueFelv(234D);
			Integer setErrorCodeFelv = (testErrorConfigured) ? 150:0;
			scanDoubleTO.setTestErrorCodeFelv(setErrorCodeFelv);
			scanDoubleTO.setPositiveFiv(Boolean.FALSE);
			scanDoubleTO.setControlFiv(123123D);
			scanDoubleTO.setNoiseFiv(3234234D);
			scanDoubleTO.setTestLineValueFiv(345345D);
			scanDoubleTO.setTestErrorCodeFiv(0);
			confTO.setScanDouble(scanDoubleTO);
			break;
			
		case 8: //cPL
			ScanSingleTO scanCplTO = new ScanSingleTO();
			confTO.setCassetteTypeId(8);
			scanCplTO.setPositive(Boolean.TRUE);
			scanCplTO.setControl(99999D);
			scanCplTO.setNoise(123123D);
			scanCplTO.setTestLineValue(123123D);
			scanCplTO.setLotNumber(9063);
			scanCplTO.setScaledResult(0.434443);
			Integer setErrorCodeCpl = (testErrorConfigured) ? 150:0;
			scanCplTO.setTestErrorCode(setErrorCodeCpl);
			
			confTO.setScanSingle(scanCplTO);
			break;
			
			
		case 9: //Flex4
			ScanMultipleTO scanMultipleTO = new ScanMultipleTO();
			confTO.setCassetteTypeId(9);
			scanMultipleTO.setPositiveLyme(Boolean.TRUE);
			scanMultipleTO.setControlLyme(234234D);
			scanMultipleTO.setNoiseLyme(234234D);
			scanMultipleTO.setTestLineValueLyme(4234234D);
			Integer setErrorCodeLyme = (testErrorConfigured) ? 150:0;
			scanMultipleTO.setTestErrorCodeLyme(setErrorCodeLyme);
			scanMultipleTO.setPositiveAnaplasma(Boolean.TRUE);
			scanMultipleTO.setControlAnaplasma(234234D);
			scanMultipleTO.setNoiseAnaplasma(234234D);
			scanMultipleTO.setTestLineValueAnaplasma(4234234D);
			scanMultipleTO.setTestErrorCodeAnaplasma(0);
			scanMultipleTO.setPositiveHeartworm(Boolean.TRUE);
			scanMultipleTO.setControlHeartworm(234234D);
			scanMultipleTO.setNoiseHeartworm(234234D);
			scanMultipleTO.setTestLineValueHeartworm(4234234D);
			scanMultipleTO.setTestErrorCodeHeartworm(0);
			scanMultipleTO.setPositiveEhrlichia(Boolean.TRUE);
			scanMultipleTO.setControlEhrlichia(234234D);
			scanMultipleTO.setNoiseEhrlichia(234234D);
			scanMultipleTO.setTestLineValueEhrlichia(4234234D);
			scanMultipleTO.setTestErrorCodeEhrlichia(0);
			confTO.setScanMultiple(scanMultipleTO);
			break;
		}
		
		return confTO;
		
	}
	
	private CassetteTypeTO createCassetteTypeTO(int cassetteCode) {
		CassetteTypeTO cassetteTypeTO = new CassetteTypeTO();
		
		switch (cassetteCode){
			case 1:
				cassetteTypeTO.setCode(1);
				cassetteTypeTO.setType("Anaplasma");
			break;
			
			case 2:
				cassetteTypeTO.setCode(2);
				cassetteTypeTO.setType("FeLV FIV");
			break;
		
			case 8:
				cassetteTypeTO.setCode(8);
				cassetteTypeTO.setType("cPL");
			break;
		
			case 9:
				cassetteTypeTO.setCode(9);
				cassetteTypeTO.setType("Flex4");
			break;
		}
		
		return cassetteTypeTO;
	}
	
	private void defaultParamsTest (ResultTO resultTO, Integer resultIndex) {
		assertEquals(Boolean.TRUE, resultTO.getProcessResults().get(0).getReliable());
		assertEquals(DefaultParams.DB_VERSION, resultTO.getReaderData().getDbVersion());
		assertEquals(DefaultParams.BACKGROUND, resultTO.getProcessResults().get(resultIndex).getBackground());
		assertEquals(DefaultParams.COLOR, resultTO.getProcessResults().get(resultIndex).getColor01());
		assertEquals(DefaultParams.COLOR, resultTO.getProcessResults().get(resultIndex).getColor02());
		
	}
	
	
	// TODO: more JU cases

}
