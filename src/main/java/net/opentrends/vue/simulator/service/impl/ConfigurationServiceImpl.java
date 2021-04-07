package net.opentrends.vue.simulator.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.dto.ConfigTO;
import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.dto.DeviceStatusTO;
import net.opentrends.vue.simulator.dto.ErrorTO;
import net.opentrends.vue.simulator.dto.EthernetTO;
import net.opentrends.vue.simulator.dto.ProcessResultTO;
import net.opentrends.vue.simulator.dto.ReaderDataTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.StatusResponseTO;
import net.opentrends.vue.simulator.dto.StatusTO;
import net.opentrends.vue.simulator.dto.WifiApTO;
import net.opentrends.vue.simulator.dto.WifiModeTO;
import net.opentrends.vue.simulator.dto.WifiStationTO;
import net.opentrends.vue.simulator.model.Configuration;
import net.opentrends.vue.simulator.repository.ConfigurationRepository;
import net.opentrends.vue.simulator.service.ConfigurationService;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	@Autowired
	private ConfigurationRepository configRespository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private DeviceStatusTO deviceStatus;
	@Autowired
	private ErrorTO error;
	@Autowired
	private StatusTO status;
	@Autowired 
	private ConfigTO config;
	
	

	@Override
	public void saveConfig(ConfigurationTO config, String userId) {
		Configuration conf = mapper.map(config, Configuration.class);
		conf.setUserId(userId);
		configRespository.save(conf);
	}

	@Override
	public ConfigurationTO getConfigById(String configId) {		
		Optional<Configuration> conf = configRespository.findById(configId);
		return conf.map(c-> mapper.map(c, ConfigurationTO.class)).orElse(null);
	}

	@Override
	public List<ConfigurationTO> getConfigsByUserId(String idUser) {
		return configRespository.findByUserId(idUser)
				.stream()
				.map(config -> mapper.map(config, ConfigurationTO.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public ConfigTO getConfig(String serialNumber) {
		
		Optional<Configuration>conf = configRespository.findBySerialNumber(serialNumber);
		ConfigurationTO configuration = conf.map(c-> mapper.map(c, ConfigurationTO.class)).orElse(null);
//		ConfigTO config = new ConfigTO();
		EthernetTO ethernet = new EthernetTO();
		WifiApTO wifiAp = new WifiApTO();
		WifiModeTO wifiMode = new WifiModeTO();
		WifiStationTO wifiStation = new WifiStationTO();
		System.out.println("PASSSEM PER AQUI CONFIG" + configuration.toString()); //esborrar		

		ethernet.setDhcp(1);
		ethernet.setGateway("");
		ethernet.setIp(configuration.getEthernetIp());
		ethernet.setNetmask("255.255.255.0");
		wifiAp.setIp("192.168.133.1");
		wifiAp.setNetmask("255.255.255.0");
		wifiAp.setPwd("abwi-rdr");
		wifiAp.setSsid("VUESIMUL");
		wifiMode.setWifiAp(true);
		wifiStation.setDhcp(true);
		wifiStation.setGateway("192.168.0.1");
		wifiStation.setIp("192.168.255.254");
		wifiStation.setNetmask("255.255.255.0");
		wifiStation.setPwd("pwd_easy_easy");
		wifiStation.setSsid("VUE123456");
		config.setEthernetTO(ethernet);
		config.setWifiApTO(wifiAp);
		config.setWifimodeTO(wifiMode);
		config.setWifiStationTO(wifiStation);
		
		
		return config;
		
	}

	@Override
	public StatusTO getStatusConfig(String serialNumber) {

		Optional<Configuration>conf = configRespository.findBySerialNumber(serialNumber);
		//		Optional<Configuration> conf = Optional.of(configRespository.findBySerialNumber(serialNumber));
		ConfigurationTO configuration = conf.map(c-> mapper.map(c, ConfigurationTO.class)).orElse(null);
//		System.out.println("PASSSEM PER AQUI" + configuration.toString()); //esborrar		
		
		
//		DeviceStatusTO deviceStatus = new  DeviceStatusTO();
//		ErrorTO errorTO = new ErrorTO();
//		StatusTO statusTO = new StatusTO();
		
		deviceStatus.setBusyState(configuration.getBusyState());
		deviceStatus.setCassetteIn(configuration.getCassetteIn());
		deviceStatus.setCassetteTime(configuration.getCassetteTime().toString());
		deviceStatus.setDateTime("1970-01-01T00:08:52.702955"); //mocked data
		deviceStatus.setDbVersion(5);//mocked data
		deviceStatus.setPlatform("RPi3_x86");//mocked data
		deviceStatus.setReleaseVersion(configuration.getReleaseVersion());
		deviceStatus.setSerialNumber(configuration.getSerialNumber());
		deviceStatus.setSettingsVersion(configuration.getSettingsVersion());
		
		Integer errorCode= configuration.getCassetteErrorCode();
		error.setCode(errorCode);
		if (errorCode !=0) {error.setDescription("Error " + configuration.getCassetteErrorCode());}
		else {error.setDescription("success");}
		
		status.setDeviceStatus(deviceStatus);
		status.setError(error);
		
		return status;
	}

	@Override
	public ResultTO getCassetteProcesses(String serialNumber) {
		
		Optional<Configuration>conf = configRespository.findBySerialNumber(serialNumber);
		ConfigurationTO configuration = conf.map(c-> mapper.map(c, ConfigurationTO.class)).orElse(null);
		System.out.println("CASSETTE PROCESS" + configuration.toString());
		ResultTO result = new ResultTO();
		CassetteTypeTO cassetteType = new CassetteTypeTO();
		ErrorTO error = new ErrorTO();
		ProcessResultTO result1 = new ProcessResultTO();
		ErrorTO resultError = new ErrorTO();
		ReaderDataTO readerData = new ReaderDataTO();
		
		result.setCassetteProcessId(1);
		result.setPreviousProcessId(-1);
		
		cassetteType.setCode(1);
		cassetteType.setType("Anaplasma");
		
		error.setCode(0);
		error.setDescription("success");
		
		result1.setBackground("205.9887617021278");
		result1.setColor01("#FF7000");
		result1.setColor02("#FF7000");
		result1.setControl(58.98551);
		
		resultError.setCode(0);
		resultError.setDescription("success");
		result1.setError(resultError);

		result1.setInitial('A');
		result1.setNoise(58.98551);
		result1.setPositive(true);
		result1.setReliable(true);
		result1.setTestLineValue(0.5487337);
		result1.setTestName("Anaplasma");
		result1.setWarnings(null);
		
		readerData.setCassetteTime(4.24);
		readerData.setDateTime("2020-11-15 11:29:59");
		readerData.setDbVersion("5");
		readerData.setReleaseVersion("9.2.2.4");
		readerData.setSettingsVersion("5.3.6");
		
		result.setCassetteType(cassetteType);
		result.setError(error);
		ArrayList<ProcessResultTO> processResults = new ArrayList<ProcessResultTO>();
		processResults.add(result1);
		result.setProcessResults(processResults);
		result.setReaderData(readerData);
		return result;
	}	

}
