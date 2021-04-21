package net.opentrends.vue.simulator.service.impl;

import static java.util.Optional.ofNullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.dto.CoeficientsTO;
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
import net.opentrends.vue.simulator.dto.WarningTO;
import net.opentrends.vue.simulator.dto.WifiApTO;
import net.opentrends.vue.simulator.dto.WifiModeTO;
import net.opentrends.vue.simulator.dto.WifiStationTO;
import net.opentrends.vue.simulator.exception.AppRuntimeException;
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
	public ConfigReaderTO getConfigReader(String serialNumber) throws AppRuntimeException {
		// TODO: throw exception?
		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);
		
		EthernetTO ethernet = new EthernetTO();
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
	public StatusTO getStatusConfig(String serialNumber) throws AppRuntimeException {
		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);

		DeviceStatusTO deviceStatus = new DeviceStatusTO();
		deviceStatus.setBusyState(configuration.getBusyState());
		deviceStatus.setCassetteIn(configuration.getCassetteIn());
		deviceStatus.setCassetteTime(configuration.getCassetteTime().toString());
		deviceStatus.setDateTime("1970-01-01T00:08:52.702955");
		deviceStatus.setDbVersion(5);
		deviceStatus.setPlatform(DefaultParams.PLATFORM);
		deviceStatus.setReleaseVersion(configuration.getReleaseVersion());
		deviceStatus.setSerialNumber(configuration.getSerialNumber());
		deviceStatus.setSettingsVersion(configuration.getSettingsVersion());
		
		ErrorTO errorTO = createErrorTo(configuration.getCassetteErrorCode());

		StatusTO status = new StatusTO();
		status.setDeviceStatus(deviceStatus);
		status.setError(errorTO);

		return status;
	}

	@Override
	public ResultTO getCassetteProcesses(String serialNumber) throws AppRuntimeException {
		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);
		CassetteTypeTO cassetteTypeRead = cassetteTypeService.getCassetteTypeByCode(configuration.getCassetteTypeId());
		ResultTO resultTO = new ResultTO();
//		Integer i = ofNullable(configuration.getProcessId()).ifPresent(resultTO::setCassetteProcessId);
		resultTO.setCassetteProcessId(ofNullable(configuration.getProcessId()).map(x -> configuration.getProcessId()).orElse(111));
		resultTO.setPreviousProcessId(ofNullable(configuration.getPreviousProcessId()).map(x -> configuration.getPreviousProcessId()).orElse(-1));

		CassetteTypeTO cassetteType = new CassetteTypeTO();
		cassetteType.setCode(configuration.getCassetteTypeId());
		cassetteType.setType(cassetteTypeRead.getType());

		ErrorTO errorCodeTO = createErrorTo(configuration.getCassetteErrorCode());
		ReaderDataTO readerData = new ReaderDataTO();
		readerData.setCassetteTime(configuration.getCassetteTime());
		readerData.setDateTime("2020-11-15 11:29:59");
		readerData.setDbVersion(DefaultParams.DB_VERSION);
		readerData.setReleaseVersion(configuration.getReleaseVersion());
		readerData.setSettingsVersion(configuration.getSettingsVersion());

		List<ProcessResultTO> processResults = new ArrayList<>();

		switch (cassetteTypeRead.getCode()) {
			case 2:
				createFelvFivProcessResults(configuration, processResults, cassetteTypeRead);
				break;
			case 8:
				createCplProcessResult(configuration, processResults, cassetteTypeRead);
				break;
			case 9:
				createFlex4ProcessResult(configuration, processResults, cassetteTypeRead);
				break;
			default:
				createSingleProcessResults(configuration, processResults, cassetteTypeRead);
				break;
		}

		resultTO.setCassetteType(cassetteType);
		resultTO.setError(errorCodeTO);
		resultTO.setReaderData(readerData);
		resultTO.setProcessResults(processResults);
		return resultTO;
	}
	
	@Override
	public ImagesTO getImage(String serialNumber) throws AppRuntimeException, IOException {
		configurationService.getConfigBySerialNumber(serialNumber);
		ImagesTO imageTo = new ImagesTO();
		imageTo.setId(1);
		File file = ResourceUtils.getFile("classpath:static/images/vue.png");
		imageTo.setImage(Files.readAllBytes(file.toPath()));
		return imageTo;
	}
	
	private void createCplProcessResult(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultCpl = new ProcessResultTO();
		resultCpl.setBackground(DefaultParams.BACKGROUND);
		resultCpl.setColor01(DefaultParams.COLOR);
		resultCpl.setColor02(DefaultParams.COLOR);
		resultCpl.setControl(configuration.getScanSingle().getControl());
		ErrorTO errorCplTO = createErrorTo(configuration.getScanSingle().getTestErrorCode());
		resultCpl.setError(errorCplTO);
		resultCpl.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultCpl.setNoise(configuration.getScanSingle().getNoise());
		resultCpl.setPositive(configuration.getScanSingle().getPositive());
		resultCpl.setReliable(true);
		resultCpl.setPosition(1);
		resultCpl.setTestLineValue(configuration.getScanSingle().getTestLineValue());
		resultCpl.setTestName(cassetteTypeRead.getType());
		resultCpl.setScaledResult(configuration.getScanSingle().getScaledResult());
		resultCpl.setLotNumber(configuration.getScanSingle().getLotNumber());
		CoeficientsTO coeficients = new CoeficientsTO();
		coeficients.setA(2);
		coeficients.setB(1);
		coeficients.setC(3);
		coeficients.setD(1);
		coeficients.setF(1);
		resultCpl.setCoeficients(coeficients);
	
		List<WarningTO> warningArrayCpl = new ArrayList<>();
		resultCpl.setWarnings(warningArrayCpl);
		processResults.add(resultCpl);
	}
	
	private void createFlex4ProcessResult(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultAnaplasmaTO = new ProcessResultTO();
		resultAnaplasmaTO.setBackground(DefaultParams.BACKGROUND);
		resultAnaplasmaTO.setColor01(DefaultParams.COLOR);
		resultAnaplasmaTO.setColor02(DefaultParams.COLOR);
		resultAnaplasmaTO.setControl(configuration.getScanMultiple().getControlAnaplasma());
		ErrorTO errorAnaplasmaTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeAnaplasma());
		resultAnaplasmaTO.setError(errorAnaplasmaTO);
		resultAnaplasmaTO.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultAnaplasmaTO.setNoise(configuration.getScanMultiple().getNoiseAnaplasma());
		resultAnaplasmaTO.setPositive(configuration.getScanMultiple().getPositiveAnaplasma());
		resultAnaplasmaTO.setReliable(true);
		resultAnaplasmaTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueAnaplasma());
		resultAnaplasmaTO.setTestName(cassetteTypeRead.getType());		
		processResults.add(resultAnaplasmaTO);
		
		ProcessResultTO resultEhrlichiaTO = new ProcessResultTO();
		resultEhrlichiaTO.setBackground(DefaultParams.BACKGROUND);
		resultEhrlichiaTO.setColor01(DefaultParams.COLOR);
		resultEhrlichiaTO.setColor02(DefaultParams.COLOR);
		resultEhrlichiaTO.setControl(configuration.getScanMultiple().getControlEhrlichia());		
		ErrorTO errorEhrlichiaTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeEhrlichia());
		resultEhrlichiaTO.setError(errorEhrlichiaTO);
		resultEhrlichiaTO.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultEhrlichiaTO.setNoise(configuration.getScanMultiple().getNoiseEhrlichia());
		resultEhrlichiaTO.setPositive(configuration.getScanMultiple().getPositiveEhrlichia());
		resultEhrlichiaTO.setReliable(true);
		resultEhrlichiaTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueEhrlichia());
		resultEhrlichiaTO.setTestName(cassetteTypeRead.getType());		
		processResults.add(resultEhrlichiaTO);
		
		ProcessResultTO resultLyme = new ProcessResultTO();
		resultLyme.setBackground(DefaultParams.BACKGROUND);
		resultLyme.setColor01(DefaultParams.COLOR);
		resultLyme.setColor02(DefaultParams.COLOR);
		resultLyme.setControl(configuration.getScanMultiple().getControlLyme());
		ErrorTO errorLymeTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeLyme());
		resultLyme.setError(errorLymeTO);
		resultLyme.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultLyme.setNoise(configuration.getScanMultiple().getNoiseLyme());
		resultLyme.setPositive(configuration.getScanMultiple().getPositiveLyme());
		resultLyme.setReliable(true);
		resultLyme.setTestLineValue(configuration.getScanMultiple().getTestLineValueLyme());
		resultLyme.setTestName(cassetteTypeRead.getType());		
		processResults.add(resultLyme);
		
		ProcessResultTO resultHeartworm = new ProcessResultTO();
		resultHeartworm.setBackground(DefaultParams.BACKGROUND);
		resultHeartworm.setColor01(DefaultParams.COLOR);
		resultHeartworm.setColor02(DefaultParams.COLOR);
		resultHeartworm.setControl(configuration.getScanMultiple().getControlHeartworm());
		ErrorTO errorHeartwormTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeHeartworm());		
		resultHeartworm.setError(errorHeartwormTO);
		resultHeartworm.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultHeartworm.setNoise(configuration.getScanMultiple().getNoiseHeartworm());
		resultHeartworm.setPositive(configuration.getScanMultiple().getPositiveHeartworm());
		resultHeartworm.setReliable(true);
		resultHeartworm.setTestLineValue(configuration.getScanMultiple().getTestLineValueHeartworm());
		resultHeartworm.setTestName(cassetteTypeRead.getType());		
		processResults.add(resultHeartworm);
	}
	
	private void createFelvFivProcessResults(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultFelv = new ProcessResultTO();
		resultFelv.setBackground(DefaultParams.BACKGROUND);
		resultFelv.setColor01(DefaultParams.COLOR);
		resultFelv.setColor02(DefaultParams.COLOR);
		resultFelv.setControl(configuration.getScanDouble().getControlFelv());
		ErrorTO errorFelvTO = createErrorTo(configuration.getScanDouble().getTestErrorCodeFelv());		
		resultFelv.setError(errorFelvTO);
		resultFelv.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultFelv.setNoise(configuration.getScanDouble().getNoiseFelv());
		resultFelv.setPositive(configuration.getScanDouble().getPositiveFelv());
		resultFelv.setReliable(true);
		resultFelv.setTestLineValue(configuration.getScanDouble().getTestLineValueFelv());
		resultFelv.setTestName(cassetteTypeRead.getType());
		processResults.add(resultFelv);

		ProcessResultTO resultFiv = new ProcessResultTO();
		resultFiv.setBackground(DefaultParams.BACKGROUND);
		resultFiv.setColor01(DefaultParams.COLOR);
		resultFiv.setColor02(DefaultParams.COLOR);
		resultFiv.setControl(configuration.getScanDouble().getControlFiv());
		ErrorTO errorFivTO = createErrorTo(configuration.getScanDouble().getTestErrorCodeFiv());
		resultFiv.setError(errorFivTO);
		resultFiv.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultFiv.setNoise(configuration.getScanDouble().getNoiseFiv());
		resultFiv.setPositive(configuration.getScanDouble().getPositiveFiv());
		resultFiv.setReliable(true);
		resultFiv.setTestLineValue(configuration.getScanDouble().getTestLineValueFiv());
		resultFiv.setTestName(cassetteTypeRead.getType());		
		processResults.add(resultFiv);
	}
	
	private void createSingleProcessResults(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO result1 = new ProcessResultTO();
		result1.setBackground(DefaultParams.BACKGROUND);
		result1.setColor01(DefaultParams.COLOR);
		result1.setColor02(DefaultParams.COLOR);
		result1.setControl(configuration.getScanSingle().getControl());
		ErrorTO errorTO = createErrorTo(configuration.getScanSingle().getTestErrorCode());
		result1.setError(errorTO);
		result1.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		result1.setNoise(configuration.getScanSingle().getNoise());
		result1.setPositive(configuration.getScanSingle().getPositive());
		result1.setReliable(true);
		result1.setPosition(1);
		result1.setTestLineValue(configuration.getScanSingle().getTestLineValue());
		result1.setTestName(cassetteTypeRead.getType());
		List<WarningTO> warningArray = new ArrayList<>();
		result1.setWarnings(warningArray);
		processResults.add(result1);
	}
	
	private ErrorTO createErrorTo(Integer resultErrorCode) {
		ErrorTO errorTO = new ErrorTO();
		if (resultErrorCode != null && resultErrorCode != 0) {
			errorTO.setCode(resultErrorCode);
			errorTO.setDescription("Error " + resultErrorCode);
		} else {
			errorTO.setCode(0);
			errorTO.setDescription(DefaultParams.SUCCESS);
		}
		return errorTO;
	}

}

