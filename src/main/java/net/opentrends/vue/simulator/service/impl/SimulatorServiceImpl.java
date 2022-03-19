package net.opentrends.vue.simulator.service.impl;

import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.dto.CoeficientsTO;
import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.dto.DateTimeTO;
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
import net.opentrends.vue.simulator.exception.AppRuntimeException;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.SimulatorService;
import net.opentrends.vue.simulator.utils.DefaultParams;

public class SimulatorServiceImpl implements SimulatorService {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ");

	private final ConfigurationService configurationService;
	private final CassetteTypeService cassetteTypeService;
	private final ResourceLoader resourceLoader;
	
	public SimulatorServiceImpl(ConfigurationService configurationService, CassetteTypeService cassetteTypeService, ResourceLoader resourceLoader) {
		this.configurationService = configurationService;
		this.cassetteTypeService = cassetteTypeService;
		this.resourceLoader = resourceLoader;				
	}


	@Override
	public ConfigReaderTO getConfigReader(String serialNumber, String serverName) throws AppRuntimeException {
		configurationService.getConfigBySerialNumber(serialNumber);
		
		EthernetTO ethernet = new EthernetTO();
		ethernet.setDhcp(true);
		ethernet.setGateway(DefaultParams.GATEWAY);
		ethernet.setIp(serverName);
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
		config.setEthernet(ethernet);
		config.setWifiAp(wifiAp);
		config.setWifimode(wifiMode);
		config.setWifiStation(wifiStation);

		return config;
	}

	@Override
	public StatusTO getStatusConfig(String serialNumber) throws AppRuntimeException {
		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);

		DeviceStatusTO deviceStatus = new DeviceStatusTO();
		deviceStatus.setBusyState(configuration.getBusyState());
		deviceStatus.setCassetteIn(configuration.getCassetteIn());
		deviceStatus.setCassetteTime(configuration.getCassetteTime());
		deviceStatus.setDateTime(sdf.format(new Date()));
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
		resultTO.setCassetteProcessId(ofNullable(configuration.getProcessId()).orElse(111));
		resultTO.setPreviousProcessId(ofNullable(configuration.getPreviousProcessId()).orElse(-1));

		CassetteTypeTO cassetteType = new CassetteTypeTO();
		cassetteType.setCode(configuration.getCassetteTypeId());
		cassetteType.setType(cassetteTypeRead.getType());
		if(configuration.getScanSingle() != null)
			cassetteType.setLotNumber(configuration.getScanSingle().getLotNumber());

		ErrorTO errorCodeTO = createErrorTo(configuration.getCassetteErrorCode());
		ReaderDataTO readerData = new ReaderDataTO();
		readerData.setCassetteTime(configuration.getCassetteTime());
		readerData.setDateTime(sdf.format(new Date()));
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
	public ResultTO getCassetteProcesses2(String serialNumber) throws AppRuntimeException {
		ConfigurationTO configuration = configurationService.getConfigBySerialNumber(serialNumber);
		if (!configuration.getSameCassette()) {
			return getCassetteProcesses (serialNumber);
		}
		CassetteTypeTO cassetteTypeRead = cassetteTypeService.getCassetteTypeByCode(configuration.getCassetteTypeId());
		ResultTO resultTO = new ResultTO();
		CassetteTypeTO cassetteType = new CassetteTypeTO();
		cassetteType.setCode(configuration.getCassetteTypeId());
		cassetteType.setType(cassetteTypeRead.getType());
		if(configuration.getScanSingle() != null)
			cassetteType.setLotNumber(configuration.getScanSingle().getLotNumber2());
		
		ReaderDataTO readerData = new ReaderDataTO();
		readerData.setCassetteTime(configuration.getCassetteTime());
		readerData.setDateTime(sdf.format(new Date()));
		readerData.setDbVersion(DefaultParams.DB_VERSION);
		readerData.setReleaseVersion(configuration.getReleaseVersion());
		readerData.setSettingsVersion(configuration.getSettingsVersion());
		
		resultTO.setCassetteProcessId(ofNullable(configuration.getProcessId2()).orElse(111));
		resultTO.setPreviousProcessId(ofNullable(configuration.getPreviousProcessId2()).orElse(-1));

		ErrorTO errorCodeTO = createErrorTo(configuration.getCassetteErrorCode2());

		List<ProcessResultTO> processResults = new ArrayList<>();

		switch (cassetteTypeRead.getCode()) {
		case 2:
			createFelvFivProcessResults2(configuration, processResults, cassetteTypeRead);
			break;
		case 8:
			createCplProcessResult2(configuration, processResults, cassetteTypeRead);
			break;
		case 9:
			createFlex4ProcessResult2(configuration, processResults, cassetteTypeRead);
			break;
		default:
			createSingleProcessResults2(configuration, processResults, cassetteTypeRead);
			break;
		}
		resultTO.setError(errorCodeTO);
		resultTO.setProcessResults(processResults);
		resultTO.setCassetteType(cassetteType);
		resultTO.setReaderData(readerData);
		return resultTO;
	}
	
	@Override
	public ImagesTO getImage(String serialNumber) throws IOException {
		// Nothing to do here with simulator config, just to raise an exception in case serialNumber doesn't exist
		configurationService.getConfigBySerialNumber(serialNumber);
		ImagesTO imageTo = new ImagesTO();
		imageTo.setId(1);	
		InputStream is = resourceLoader.getResource("classpath:static/images/vue.png").getInputStream();
		imageTo.setImage(StreamUtils.copyToByteArray(is));
		return imageTo;
	}
	
	@Override
	public DateTimeTO getReaderDateAndTime(String serialNumber) {
		// Nothing to do here with simulator config, just to raise an exception in case serialNumber doesn't exist
		configurationService.getConfigBySerialNumber(serialNumber);
		DateTimeTO dateTime = new DateTimeTO();
		dateTime.setDateTime(sdf.format(new Date()));
		return dateTime;
	}

	@Override
	public String cancelTimedScan(String serialNumber) {
		// Nothing to do here with simulator config, just to raise an exception in case serialNumber doesn't exist
		configurationService.getConfigBySerialNumber(serialNumber);
		return "cancel";
	}
	
	private void createCplProcessResult(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultCpl = new ProcessResultTO();
		resultCpl.setBackground(DefaultParams.BACKGROUND);
		resultCpl.setColor01(DefaultParams.CPL_COLOR);
		resultCpl.setColor02(DefaultParams.CPL_COLOR);
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
		CoeficientsTO coeficients = new CoeficientsTO();
		coeficients.setA(2);
		coeficients.setB(1);
		coeficients.setC(3);
		coeficients.setD(1);
		coeficients.setF(1);
		resultCpl.setCoeficients(coeficients);
	
		resultCpl.setWarnings(new ArrayList<>());
		processResults.add(resultCpl);
	}
	
	private void createCplProcessResult2(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultCpl = new ProcessResultTO();
		resultCpl.setBackground(DefaultParams.BACKGROUND);
		resultCpl.setColor01(DefaultParams.CPL_COLOR);
		resultCpl.setColor02(DefaultParams.CPL_COLOR);
		resultCpl.setControl(configuration.getScanSingle().getControl2());
		ErrorTO errorCplTO = createErrorTo(configuration.getScanSingle().getTestErrorCode2());
		resultCpl.setError(errorCplTO);
		resultCpl.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		resultCpl.setNoise(configuration.getScanSingle().getNoise2());
		resultCpl.setPositive(configuration.getScanSingle().getPositive2());
		resultCpl.setReliable(true);
		resultCpl.setPosition(1);
		resultCpl.setTestLineValue(configuration.getScanSingle().getTestLineValue2());
		resultCpl.setTestName(cassetteTypeRead.getType());
		resultCpl.setScaledResult(configuration.getScanSingle().getScaledResult2());
		CoeficientsTO coeficients = new CoeficientsTO();
		coeficients.setA(2);
		coeficients.setB(1);
		coeficients.setC(3);
		coeficients.setD(1);
		coeficients.setF(1);
		resultCpl.setCoeficients(coeficients);
	
		resultCpl.setWarnings(new ArrayList<>());
		processResults.add(resultCpl);
	}
	
	private void createFlex4ProcessResult(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultAnaplasmaTO = new ProcessResultTO();
		resultAnaplasmaTO.setBackground(DefaultParams.BACKGROUND);
		resultAnaplasmaTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultAnaplasmaTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultAnaplasmaTO.setControl(configuration.getScanMultiple().getControlAnaplasma());
		ErrorTO errorAnaplasmaTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeAnaplasma());
		resultAnaplasmaTO.setError(errorAnaplasmaTO);
		resultAnaplasmaTO.setInitial(DefaultParams.ANAPLASMA.toUpperCase().charAt(0));
		resultAnaplasmaTO.setNoise(configuration.getScanMultiple().getNoiseAnaplasma());
		resultAnaplasmaTO.setPositive(configuration.getScanMultiple().getPositiveAnaplasma());
		resultAnaplasmaTO.setReliable(true);
		resultAnaplasmaTO.setPosition(1);
		resultAnaplasmaTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueAnaplasma());
		resultAnaplasmaTO.setTestName(DefaultParams.ANAPLASMA);		
		processResults.add(resultAnaplasmaTO);
		
		ProcessResultTO resultEhrlichiaTO = new ProcessResultTO();
		resultEhrlichiaTO.setBackground(DefaultParams.BACKGROUND);
		resultEhrlichiaTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultEhrlichiaTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultEhrlichiaTO.setControl(configuration.getScanMultiple().getControlEhrlichia());		
		ErrorTO errorEhrlichiaTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeEhrlichia());
		resultEhrlichiaTO.setError(errorEhrlichiaTO);
		resultEhrlichiaTO.setInitial(DefaultParams.EHRLICHIA.toUpperCase().charAt(0));
		resultEhrlichiaTO.setNoise(configuration.getScanMultiple().getNoiseEhrlichia());
		resultEhrlichiaTO.setPositive(configuration.getScanMultiple().getPositiveEhrlichia());
		resultEhrlichiaTO.setReliable(true);
		resultEhrlichiaTO.setPosition(2);
		resultEhrlichiaTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueEhrlichia());
		resultEhrlichiaTO.setTestName(DefaultParams.EHRLICHIA);		
		processResults.add(resultEhrlichiaTO);
		
		ProcessResultTO resultLymeTO = new ProcessResultTO();
		resultLymeTO.setBackground(DefaultParams.BACKGROUND);
		resultLymeTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultLymeTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultLymeTO.setControl(configuration.getScanMultiple().getControlLyme());
		ErrorTO errorLymeTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeLyme());
		resultLymeTO.setError(errorLymeTO);
		resultLymeTO.setInitial(DefaultParams.LYME.toUpperCase().charAt(0));
		resultLymeTO.setNoise(configuration.getScanMultiple().getNoiseLyme());
		resultLymeTO.setPositive(configuration.getScanMultiple().getPositiveLyme());
		resultLymeTO.setReliable(true);
		resultLymeTO.setPosition(3);
		resultLymeTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueLyme());
		resultLymeTO.setTestName(DefaultParams.LYME);		
		processResults.add(resultLymeTO);
		
		ProcessResultTO resultHeartwormTO = new ProcessResultTO();
		resultHeartwormTO.setBackground(DefaultParams.BACKGROUND);
		resultHeartwormTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultHeartwormTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultHeartwormTO.setControl(configuration.getScanMultiple().getControlHeartworm());
		ErrorTO errorHeartwormTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeHeartworm());		
		resultHeartwormTO.setError(errorHeartwormTO);
		resultHeartwormTO.setInitial(DefaultParams.HEARTWORM.toUpperCase().charAt(0));
		resultHeartwormTO.setNoise(configuration.getScanMultiple().getNoiseHeartworm());
		resultHeartwormTO.setPositive(configuration.getScanMultiple().getPositiveHeartworm());
		resultHeartwormTO.setReliable(true);
		resultHeartwormTO.setPosition(4);
		resultHeartwormTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueHeartworm());
		resultHeartwormTO.setTestName(DefaultParams.HEARTWORM);		
		processResults.add(resultHeartwormTO);
	}
	
	private void createFlex4ProcessResult2(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultAnaplasmaTO = new ProcessResultTO();
		resultAnaplasmaTO.setBackground(DefaultParams.BACKGROUND);
		resultAnaplasmaTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultAnaplasmaTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultAnaplasmaTO.setControl(configuration.getScanMultiple().getControlAnaplasma2());
		ErrorTO errorAnaplasmaTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeAnaplasma2());
		resultAnaplasmaTO.setError(errorAnaplasmaTO);
		resultAnaplasmaTO.setInitial(DefaultParams.ANAPLASMA.toUpperCase().charAt(0));
		resultAnaplasmaTO.setNoise(configuration.getScanMultiple().getNoiseAnaplasma2());
		resultAnaplasmaTO.setPositive(configuration.getScanMultiple().getPositiveAnaplasma2());
		resultAnaplasmaTO.setReliable(true);
		resultAnaplasmaTO.setPosition(1);
		resultAnaplasmaTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueAnaplasma2());
		resultAnaplasmaTO.setTestName(DefaultParams.ANAPLASMA);		
		processResults.add(resultAnaplasmaTO);
		
		ProcessResultTO resultEhrlichiaTO = new ProcessResultTO();
		resultEhrlichiaTO.setBackground(DefaultParams.BACKGROUND);
		resultEhrlichiaTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultEhrlichiaTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultEhrlichiaTO.setControl(configuration.getScanMultiple().getControlEhrlichia2());		
		ErrorTO errorEhrlichiaTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeEhrlichia2());
		resultEhrlichiaTO.setError(errorEhrlichiaTO);
		resultEhrlichiaTO.setInitial(DefaultParams.EHRLICHIA.toUpperCase().charAt(0));
		resultEhrlichiaTO.setNoise(configuration.getScanMultiple().getNoiseEhrlichia2());
		resultEhrlichiaTO.setPositive(configuration.getScanMultiple().getPositiveEhrlichia2());
		resultEhrlichiaTO.setReliable(true);
		resultEhrlichiaTO.setPosition(2);
		resultEhrlichiaTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueEhrlichia2());
		resultEhrlichiaTO.setTestName(DefaultParams.EHRLICHIA);		
		processResults.add(resultEhrlichiaTO);
		
		ProcessResultTO resultLymeTO = new ProcessResultTO();
		resultLymeTO.setBackground(DefaultParams.BACKGROUND);
		resultLymeTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultLymeTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultLymeTO.setControl(configuration.getScanMultiple().getControlLyme2());
		ErrorTO errorLymeTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeLyme2());
		resultLymeTO.setError(errorLymeTO);
		resultLymeTO.setInitial(DefaultParams.LYME.toUpperCase().charAt(0));
		resultLymeTO.setNoise(configuration.getScanMultiple().getNoiseLyme2());
		resultLymeTO.setPositive(configuration.getScanMultiple().getPositiveLyme2());
		resultLymeTO.setReliable(true);
		resultLymeTO.setPosition(3);
		resultLymeTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueLyme2());
		resultLymeTO.setTestName(DefaultParams.LYME);		
		processResults.add(resultLymeTO);
		
		ProcessResultTO resultHeartwormTO = new ProcessResultTO();
		resultHeartwormTO.setBackground(DefaultParams.BACKGROUND);
		resultHeartwormTO.setColor01(DefaultParams.FLEX4_COLOR);
		resultHeartwormTO.setColor02(DefaultParams.FLEX4_COLOR);
		resultHeartwormTO.setControl(configuration.getScanMultiple().getControlHeartworm2());
		ErrorTO errorHeartwormTO = createErrorTo(configuration.getScanMultiple().getTestErrorCodeHeartworm2());		
		resultHeartwormTO.setError(errorHeartwormTO);
		resultHeartwormTO.setInitial(DefaultParams.HEARTWORM.toUpperCase().charAt(0));
		resultHeartwormTO.setNoise(configuration.getScanMultiple().getNoiseHeartworm2());
		resultHeartwormTO.setPositive(configuration.getScanMultiple().getPositiveHeartworm2());
		resultHeartwormTO.setReliable(true);
		resultHeartwormTO.setPosition(4);
		resultHeartwormTO.setTestLineValue(configuration.getScanMultiple().getTestLineValueHeartworm2());
		resultHeartwormTO.setTestName(DefaultParams.HEARTWORM);		
		processResults.add(resultHeartwormTO);
	}
	
	private void createFelvFivProcessResults(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultFelvTO = new ProcessResultTO();
		resultFelvTO.setBackground(DefaultParams.BACKGROUND);
		resultFelvTO.setColor01(DefaultParams.FELVFIV_COLOR);
		resultFelvTO.setColor02(DefaultParams.FELVFIV_COLOR);
		resultFelvTO.setControl(configuration.getScanDouble().getControlFelv());
		ErrorTO errorFelvTO = createErrorTo(configuration.getScanDouble().getTestErrorCodeFelv());		
		resultFelvTO.setError(errorFelvTO);
		resultFelvTO.setInitial(DefaultParams.FELV.toUpperCase().charAt(0));
		resultFelvTO.setNoise(configuration.getScanDouble().getNoiseFelv());
		resultFelvTO.setPositive(configuration.getScanDouble().getPositiveFelv());
		resultFelvTO.setReliable(true);
		resultFelvTO.setPosition(1);
		resultFelvTO.setTestLineValue(configuration.getScanDouble().getTestLineValueFelv());
		resultFelvTO.setTestName(DefaultParams.FELV);
		processResults.add(resultFelvTO);

		ProcessResultTO resultFivTO = new ProcessResultTO();
		resultFivTO.setBackground(DefaultParams.BACKGROUND);
		resultFivTO.setColor01(DefaultParams.FELVFIV_COLOR);
		resultFivTO.setColor02(DefaultParams.FELVFIV_COLOR);
		resultFivTO.setControl(configuration.getScanDouble().getControlFiv());
		ErrorTO errorFivTO = createErrorTo(configuration.getScanDouble().getTestErrorCodeFiv());
		resultFivTO.setError(errorFivTO);
		resultFivTO.setInitial(DefaultParams.FIV.toUpperCase().charAt(0));
		resultFivTO.setNoise(configuration.getScanDouble().getNoiseFiv());
		resultFivTO.setPositive(configuration.getScanDouble().getPositiveFiv());
		resultFivTO.setReliable(true);
		resultFivTO.setPosition(2);
		resultFivTO.setTestLineValue(configuration.getScanDouble().getTestLineValueFiv());
		resultFivTO.setTestName(DefaultParams.FIV);		
		processResults.add(resultFivTO);
	}
	
	private void createFelvFivProcessResults2(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO resultFelvTO = new ProcessResultTO();
		resultFelvTO.setBackground(DefaultParams.BACKGROUND);
		resultFelvTO.setColor01(DefaultParams.FELVFIV_COLOR);
		resultFelvTO.setColor02(DefaultParams.FELVFIV_COLOR);
		resultFelvTO.setControl(configuration.getScanDouble().getControlFelv2());
		ErrorTO errorFelvTO = createErrorTo(configuration.getScanDouble().getTestErrorCodeFelv2());		
		resultFelvTO.setError(errorFelvTO);
		resultFelvTO.setInitial(DefaultParams.FELV.toUpperCase().charAt(0));
		resultFelvTO.setNoise(configuration.getScanDouble().getNoiseFelv2());
		resultFelvTO.setPositive(configuration.getScanDouble().getPositiveFelv2());
		resultFelvTO.setReliable(true);
		resultFelvTO.setPosition(1);
		resultFelvTO.setTestLineValue(configuration.getScanDouble().getTestLineValueFelv2());
		resultFelvTO.setTestName(DefaultParams.FELV);
		processResults.add(resultFelvTO);

		ProcessResultTO resultFivTO = new ProcessResultTO();
		resultFivTO.setBackground(DefaultParams.BACKGROUND);
		resultFivTO.setColor01(DefaultParams.FELVFIV_COLOR);
		resultFivTO.setColor02(DefaultParams.FELVFIV_COLOR);
		resultFivTO.setControl(configuration.getScanDouble().getControlFiv2());
		ErrorTO errorFivTO = createErrorTo(configuration.getScanDouble().getTestErrorCodeFiv2());
		resultFivTO.setError(errorFivTO);
		resultFivTO.setInitial(DefaultParams.FIV.toUpperCase().charAt(0));
		resultFivTO.setNoise(configuration.getScanDouble().getNoiseFiv2());
		resultFivTO.setPositive(configuration.getScanDouble().getPositiveFiv2());
		resultFivTO.setReliable(true);
		resultFivTO.setPosition(2);
		resultFivTO.setTestLineValue(configuration.getScanDouble().getTestLineValueFiv2());
		resultFivTO.setTestName(DefaultParams.FIV);		
		processResults.add(resultFivTO);
	}
	
	private void createSingleProcessResults(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO result1 = new ProcessResultTO();
		result1.setBackground(DefaultParams.BACKGROUND);
		result1.setColor01(getColor(cassetteTypeRead));
		result1.setColor02(getColor(cassetteTypeRead));
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
		result1.setWarnings(new ArrayList<>());
		processResults.add(result1);
	}
	
	private void createSingleProcessResults2(ConfigurationTO configuration, List<ProcessResultTO> processResults, CassetteTypeTO cassetteTypeRead) {
		ProcessResultTO result1 = new ProcessResultTO();
		result1.setBackground(DefaultParams.BACKGROUND);
		result1.setColor01(getColor(cassetteTypeRead));
		result1.setColor02(getColor(cassetteTypeRead));
		result1.setControl(configuration.getScanSingle().getControl2());
		ErrorTO errorTO = createErrorTo(configuration.getScanSingle().getTestErrorCode2());
		result1.setError(errorTO);
		result1.setInitial(cassetteTypeRead.getType().toUpperCase().charAt(0));
		result1.setNoise(configuration.getScanSingle().getNoise2());
		result1.setPositive(configuration.getScanSingle().getPositive2());
		result1.setReliable(true);
		result1.setPosition(1);
		result1.setTestLineValue(configuration.getScanSingle().getTestLineValue2());
		result1.setTestName(cassetteTypeRead.getType());
		result1.setWarnings(new ArrayList<>());
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
	
	private String getColor(CassetteTypeTO cassetteTypeRead ) {
		switch (cassetteTypeRead.getCode()) {
			case 1:
				return DefaultParams.ANAPLASMA_COLOR;
			case 3:
				return DefaultParams.EHRLICHIA_COLOR;
			case 4:
				return DefaultParams.LYME_COLOR;
			case 5:
				return DefaultParams.PARVO_COLOR;
			case 6:
				return DefaultParams.GIARDIA_COLOR;
			case 7:
				return DefaultParams.HEARTWORM_COLOR;
			default:
				return DefaultParams.ANAPLASMA_COLOR;
		}
	}

}

