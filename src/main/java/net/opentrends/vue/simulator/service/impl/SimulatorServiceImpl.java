package net.opentrends.vue.simulator.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.dto.DeviceStatusTO;
import net.opentrends.vue.simulator.dto.ErrorTO;
import net.opentrends.vue.simulator.dto.EthernetTO;
import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.dto.ProcessResultTO;
import net.opentrends.vue.simulator.dto.ReaderDataTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.StatusTO;
import net.opentrends.vue.simulator.dto.WifiApTO;
import net.opentrends.vue.simulator.dto.WifiModeTO;
import net.opentrends.vue.simulator.dto.WifiStationTO;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.SimulatorService;
import net.opentrends.vue.simulator.utils.DefaultParams;

@Service
public class SimulatorServiceImpl implements SimulatorService {
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private CassetteTypeService cassetteTypeService;
	
	@Override
	public ConfigReaderTO getConfigReader(String serialNumber) {
		// TODO: throw exception?
		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);
		
		EthernetTO ethernet = new EthernetTO();
		// TODO: boolean?
		ethernet.setDhcp(1);
		ethernet.setGateway(DefaultParams.GATEWAY);
		ethernet.setIp(configuration.getEthernetIp());
		ethernet.setNetmask(DefaultParams.MASK);
		
		WifiApTO wifiAp = new WifiApTO();
		wifiAp.setIp(DefaultParams.IP);
		wifiAp.setNetmask(DefaultParams.MASK);
		wifiAp.setPwd(DefaultParams.PWD);
		wifiAp.setSsid(DefaultParams.SSID);
		
		WifiModeTO wifiMode = new WifiModeTO();
		wifiMode.setWifiAp(true);
		
		WifiStationTO wifiStation = new WifiStationTO();
		wifiStation.setDhcp(true);
		wifiStation.setGateway(DefaultParams.GATEWAY);
		wifiStation.setIp(DefaultParams.IP);
		wifiStation.setNetmask(DefaultParams.MASK);
		wifiStation.setPwd(DefaultParams.PWD);
		wifiStation.setSsid(DefaultParams.SSID);
		
		ConfigReaderTO config = new ConfigReaderTO();
		config.setEthernetTO(ethernet);
		config.setWifiApTO(wifiAp);
		config.setWifimodeTO(wifiMode);
		config.setWifiStationTO(wifiStation);

		return config;
	}

	@Override
	public StatusTO getStatusConfig(String serialNumber) {
		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);

		DeviceStatusTO deviceStatus = new DeviceStatusTO();
		deviceStatus.setBusyState(configuration.getBusyState());
		deviceStatus.setCassetteIn(configuration.getCassetteIn());
		// save as double getCassetteTime
		deviceStatus.setCassetteTime(configuration.getCassetteTime().toString());
		deviceStatus.setDateTime("1970-01-01T00:08:52.702955");
		deviceStatus.setDbVersion(5);
		deviceStatus.setPlatform(DefaultParams.PLATFORM);
		deviceStatus.setReleaseVersion(configuration.getReleaseVersion());
		deviceStatus.setSerialNumber(configuration.getSerialNumber());
		deviceStatus.setSettingsVersion(configuration.getSettingsVersion());

		ErrorTO error = new ErrorTO();
		Integer errorCode = configuration.getCassetteErrorCode();
		if (errorCode!= null && errorCode != 0) {
			error.setCode(errorCode);
			error.setDescription("Error " + configuration.getCassetteErrorCode());
		} else {
			error.setCode(0);
			error.setDescription("success");
		}

		StatusTO status = new StatusTO();
		status.setDeviceStatus(deviceStatus);
		status.setError(error);

		return status;
	}

	@Override
	public ResultTO getCassetteProcesses(String serialNumber) {

		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);
		CassetteTypeTO cassetteTypeRead = cassetteTypeService.getCassetteTypeByCode(configuration.getCassetteTypeId());		

		ResultTO result = new ResultTO();
		result.setCassetteProcessId(configuration.getProcessId());
		result.setPreviousProcessId(configuration.getPreviousProcessId());

		CassetteTypeTO cassetteType = new CassetteTypeTO();
		cassetteType.setCode(configuration.getCassetteTypeId());
		cassetteType.setType(cassetteTypeRead.getType());

		Integer errorCode = configuration.getCassetteErrorCode();
		ErrorTO error = new ErrorTO();
		if (errorCode!= null && errorCode != 0) {
			error.setCode(errorCode);
			error.setDescription("Error " + errorCode);
		} else {
			error.setCode(0);
			error.setDescription("success");
		}

		ProcessResultTO result1 = new ProcessResultTO();
		result1.setBackground("205.9887617021278");
		result1.setColor01("#FF7000");
		result1.setColor02("#FF7000");
		result1.setControl(configuration.getScanSingle().getControl());

		ErrorTO error1 = new ErrorTO();
		Integer resultErrorCode = configuration.getScanSingle().getTestErrorCode();
		if (resultErrorCode != null && resultErrorCode != 0) {
			error1.setCode(resultErrorCode);
			error1.setDescription("Error " + resultErrorCode);
		} else {
			error1.setCode(0);
			error1.setDescription("success");
		}
		result1.setError(error1);
		result1.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		result1.setNoise(configuration.getScanSingle().getNoise());
		result1.setPositive(true);
		result1.setReliable(true);
		result1.setTestLineValue(configuration.getScanSingle().getTestLineValue());
		result1.setTestName(cassetteTypeRead.getType());
		result1.setWarnings(null);

		ReaderDataTO readerData = new ReaderDataTO();
		readerData.setCassetteTime(configuration.getCassetteTime());
		readerData.setDateTime("2020-11-15 11:29:59");
		readerData.setDbVersion("5");
		readerData.setReleaseVersion(configuration.getReleaseVersion());
		readerData.setSettingsVersion(configuration.getSettingsVersion());

		result.setCassetteType(cassetteType);
		result.setError(error);
		ArrayList<ProcessResultTO> processResults = new ArrayList<ProcessResultTO>();
		processResults.add(result1);
		result.setProcessResults(processResults);
		result.setReaderData(readerData);

		return result;
	}

	@Override
	public ImagesTO getImage(String serialNumber) throws IOException {
		configurationService.getConfigBySerialNumber(serialNumber);
		ImagesTO imageTo = new ImagesTO();
		imageTo.setId(1);
		File file = ResourceUtils.getFile("classpath:static/images/vue.png");
		imageTo.setImage(Files.readAllBytes(file.toPath()));
		return imageTo;
	}

}
