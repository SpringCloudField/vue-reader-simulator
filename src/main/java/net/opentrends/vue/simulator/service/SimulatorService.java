package net.opentrends.vue.simulator.service;

import java.io.IOException;

import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.StatusTO;
import net.opentrends.vue.simulator.exception.AppRuntimeException;

public interface SimulatorService {
	
	public StatusTO getStatusConfig(String serialNumber) throws AppRuntimeException;
	
	public ConfigReaderTO getConfigReader(String serialNumber) throws AppRuntimeException;

	public ResultTO getCassetteProcesses(String serialNumber) throws AppRuntimeException;
	
	public ImagesTO getImage(String serialNumber) throws AppRuntimeException, IOException;


}
