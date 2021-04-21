package net.opentrends.vue.simulator.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

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
		deviceStatus.setCassetteTime(configuration.getCassetteTime().toString());
		deviceStatus.setDateTime("1970-01-01T00:08:52.702955");
		deviceStatus.setDbVersion(5);
		deviceStatus.setPlatform(DefaultParams.PLATFORM);
		deviceStatus.setReleaseVersion(configuration.getReleaseVersion());
		deviceStatus.setSerialNumber(configuration.getSerialNumber());
		deviceStatus.setSettingsVersion(configuration.getSettingsVersion());

		ErrorTO error = new ErrorTO();
		Integer errorCode = configuration.getCassetteErrorCode();
		if (errorCode != null && errorCode != 0) {
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

		if (configuration.getProcessId() == null) {
			result.setCassetteProcessId(111); // mocked value arbitrary assigned
		} else {
			result.setCassetteProcessId(configuration.getProcessId());
		}

		if (configuration.getPreviousProcessId() == null) {
			result.setPreviousProcessId(-1);
		} else {
			result.setPreviousProcessId(configuration.getPreviousProcessId());
		}

		CassetteTypeTO cassetteType = new CassetteTypeTO();
		cassetteType.setCode(configuration.getCassetteTypeId());
		cassetteType.setType(cassetteTypeRead.getType());

		Integer errorCode = configuration.getCassetteErrorCode();
		ErrorTO error = new ErrorTO();
		if (errorCode != null && errorCode != 0) {
			error.setCode(errorCode);
			error.setDescription("Error " + errorCode);
		} else {
			error.setCode(0);
			error.setDescription("success");
		}

		ReaderDataTO readerData = new ReaderDataTO();
		readerData.setCassetteTime(configuration.getCassetteTime());
		readerData.setDateTime("2020-11-15 11:29:59");
		readerData.setDbVersion("5");
		readerData.setReleaseVersion(configuration.getReleaseVersion());
		readerData.setSettingsVersion(configuration.getSettingsVersion());

		ArrayList<ProcessResultTO> processResults = new ArrayList<ProcessResultTO>();

		switch (cassetteTypeRead.getCode()) {

		case 2:
			ProcessResultTO resultFelv = new ProcessResultTO();
			resultFelv.setBackground("205.9887617021278");
			resultFelv.setColor01("#FF7000");
			resultFelv.setColor02("#FF7000");
			resultFelv.setControl(configuration.getScanDouble().getControlFelv());

			ErrorTO errorFelv = new ErrorTO();
			Integer resultErrorCodeFelv = configuration.getScanDouble().getTestErrorCodeFelv();
			if (resultErrorCodeFelv != null && resultErrorCodeFelv != 0) {
				errorFelv.setCode(resultErrorCodeFelv);
				errorFelv.setDescription("Error " + resultErrorCodeFelv);
			} else {
				errorFelv.setCode(0);
				errorFelv.setDescription("success");
			}
			resultFelv.setError(errorFelv);
			resultFelv.setInitial('F');
			resultFelv.setNoise(configuration.getScanDouble().getNoiseFelv());
			resultFelv.setPositive(configuration.getScanDouble().getPositiveFelv());
			resultFelv.setReliable(true);
			resultFelv.setTestLineValue(configuration.getScanDouble().getTestLineValueFelv());
			resultFelv.setTestName("FeLV");

			processResults.add(resultFelv);

			ProcessResultTO resultFiv = new ProcessResultTO();
			resultFiv.setBackground("205.9887617021278");
			resultFiv.setColor01("#FF7000");
			resultFiv.setColor02("#FF7000");
			resultFiv.setControl(configuration.getScanDouble().getControlFiv());

			ErrorTO errorFiv = new ErrorTO();
			Integer resultErrorCodeFiv = configuration.getScanDouble().getTestErrorCodeFiv();
			if (resultErrorCodeFiv != null && resultErrorCodeFiv != 0) {
				errorFiv.setCode(resultErrorCodeFiv);
				errorFiv.setDescription("Error " + resultErrorCodeFiv);
			} else {
				errorFiv.setCode(0);
				errorFiv.setDescription("success");
			}
			resultFiv.setError(errorFiv);
			resultFiv.setInitial('F');
			resultFiv.setNoise(configuration.getScanDouble().getNoiseFiv());
			resultFiv.setPositive(configuration.getScanDouble().getPositiveFiv());
			resultFiv.setReliable(true);
			resultFiv.setTestLineValue(configuration.getScanDouble().getTestLineValueFiv());
			resultFiv.setTestName("FiV");
			
			processResults.add(resultFiv);

			break;
		
		case 8:
			
			ProcessResultTO resultCpl = new ProcessResultTO();
			resultCpl.setBackground("205.9887617021278");
			resultCpl.setColor01("#FF7000");
			resultCpl.setColor02("#FF7000");
			resultCpl.setControl(configuration.getScanSingle().getControl());

			ErrorTO errorCpl = new ErrorTO();
			Integer resultErrorCodeCpl = configuration.getScanSingle().getTestErrorCode();
			if (resultErrorCodeCpl != null && resultErrorCodeCpl != 0) {
				errorCpl.setCode(resultErrorCodeCpl);
				errorCpl.setDescription("Error " + resultErrorCodeCpl);
			} else {
				errorCpl.setCode(0);
				errorCpl.setDescription("success");
			}
			resultCpl.setError(errorCpl);
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
		
			ArrayList<WarningTO> warningArrayCpl = new ArrayList<WarningTO>();
			resultCpl.setWarnings(warningArrayCpl);
			processResults.add(resultCpl);
			
			break;
			
		case 9:
			
			ProcessResultTO resultAnaplasma = new ProcessResultTO();
			resultAnaplasma.setBackground("204.9887617021278");
			resultAnaplasma.setColor01("#FF7000");
			resultAnaplasma.setColor02("#FF7000");
			resultAnaplasma.setControl(configuration.getScanMultiple().getControlAnaplasma());
			
			ErrorTO errorAnaplasma = new ErrorTO();
			Integer resultErrorCodeAnaplasma = configuration.getScanMultiple().getTestErrorCodeAnaplasma();
			if (resultErrorCodeAnaplasma != null && resultErrorCodeAnaplasma != 0) {
				errorAnaplasma.setCode(resultErrorCodeAnaplasma);
				errorAnaplasma.setDescription("Error " + resultErrorCodeAnaplasma);
			} else {
				errorAnaplasma.setCode(0);
				errorAnaplasma.setDescription("success");
			}
			
			resultAnaplasma.setError(errorAnaplasma);
			resultAnaplasma.setInitial('A');
			resultAnaplasma.setNoise(configuration.getScanMultiple().getNoiseAnaplasma());
			resultAnaplasma.setPositive(configuration.getScanMultiple().getPositiveAnaplasma());
			resultAnaplasma.setReliable(true);
			resultAnaplasma.setTestLineValue(configuration.getScanMultiple().getTestLineValueAnaplasma());
			resultAnaplasma.setTestName("Anaplasma");
			
			processResults.add(resultAnaplasma);
			
			ProcessResultTO resultEhrlichia = new ProcessResultTO();
			resultEhrlichia.setBackground("205.9887617021278");
			resultEhrlichia.setColor01("#FF7000");
			resultEhrlichia.setColor02("#FF7000");
			resultEhrlichia.setControl(configuration.getScanMultiple().getControlEhrlichia());
			
			ErrorTO errorEhrlichia = new ErrorTO();
			Integer resultErrorCodeEhrlichia = configuration.getScanMultiple().getTestErrorCodeEhrlichia();
			if (resultErrorCodeEhrlichia != null && resultErrorCodeEhrlichia != 0) {
				errorEhrlichia.setCode(resultErrorCodeEhrlichia);
				errorEhrlichia.setDescription("Error " + resultErrorCodeEhrlichia);
			} else {
				errorEhrlichia.setCode(0);
				errorEhrlichia.setDescription("success");
			}
			
			resultEhrlichia.setError(errorAnaplasma);
			resultEhrlichia.setInitial('E');
			resultEhrlichia.setNoise(configuration.getScanMultiple().getNoiseEhrlichia());
			resultEhrlichia.setPositive(configuration.getScanMultiple().getPositiveEhrlichia());
			resultEhrlichia.setReliable(true);
			resultEhrlichia.setTestLineValue(configuration.getScanMultiple().getTestLineValueEhrlichia());
			resultEhrlichia.setTestName("Ehrlichia");
			
			processResults.add(resultEhrlichia);
			
			ProcessResultTO resultLyme = new ProcessResultTO();
			resultLyme.setBackground("205.9887617021278");
			resultLyme.setColor01("#FF7000");
			resultLyme.setColor02("#FF7000");
			resultLyme.setControl(configuration.getScanMultiple().getControlLyme());
			
			ErrorTO errorLyme = new ErrorTO();
			Integer resultErrorCodeLyme = configuration.getScanMultiple().getTestErrorCodeLyme();
			if (resultErrorCodeLyme != null && resultErrorCodeLyme != 0) {
				errorLyme.setCode(resultErrorCodeLyme);
				errorLyme.setDescription("Error " + resultErrorCodeLyme);
			} else {
				errorLyme.setCode(0);
				errorLyme.setDescription("success");
			}
			
			resultLyme.setError(errorAnaplasma);
			resultLyme.setInitial('L');
			resultLyme.setNoise(configuration.getScanMultiple().getNoiseLyme());
			resultLyme.setPositive(configuration.getScanMultiple().getPositiveLyme());
			resultLyme.setReliable(true);
			resultLyme.setTestLineValue(configuration.getScanMultiple().getTestLineValueLyme());
			resultLyme.setTestName("Lyme");
			
			processResults.add(resultLyme);
			
			ProcessResultTO resultHeartworm = new ProcessResultTO();
			resultHeartworm.setBackground("205.9887617021278");
			resultHeartworm.setColor01("#FF7000");
			resultHeartworm.setColor02("#FF7000");
			resultHeartworm.setControl(configuration.getScanMultiple().getControlHeartworm());
			
			ErrorTO errorHeartworm = new ErrorTO();
			Integer resultErrorCodeHeartworm = configuration.getScanMultiple().getTestErrorCodeHeartworm();
			if (resultErrorCodeHeartworm != null && resultErrorCodeHeartworm != 0) {
				errorHeartworm.setCode(resultErrorCodeHeartworm);
				errorHeartworm.setDescription("Error " + resultErrorCodeHeartworm);
			} else {
				errorHeartworm.setCode(0);
				errorHeartworm.setDescription("success");
			}
			
			resultHeartworm.setError(errorAnaplasma);
			resultHeartworm.setInitial('H');
			resultHeartworm.setNoise(configuration.getScanMultiple().getNoiseHeartworm());
			resultHeartworm.setPositive(configuration.getScanMultiple().getPositiveHeartworm());
			resultHeartworm.setReliable(true);
			resultHeartworm.setTestLineValue(configuration.getScanMultiple().getTestLineValueHeartworm());
			resultHeartworm.setTestName("Heartworm");
			
			processResults.add(resultHeartworm);
			
			break;
			
			

		default:

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
			result1.setPositive(configuration.getScanSingle().getPositive());
			result1.setReliable(true);
			result1.setPosition(1);
			result1.setTestLineValue(configuration.getScanSingle().getTestLineValue());
			result1.setTestName(cassetteTypeRead.getType());
			ArrayList<WarningTO> warningArray = new ArrayList<WarningTO>();
			result1.setWarnings(warningArray);
			processResults.add(result1);
			
			break;

		}

		result.setCassetteType(cassetteType);
		result.setError(error);
		result.setReaderData(readerData);
		result.setProcessResults(processResults);
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

