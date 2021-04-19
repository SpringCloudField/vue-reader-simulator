package net.opentrends.vue.simulator.service;

import java.io.IOException;

import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.StatusTO;

public interface SimulatorService {
	
	public StatusTO getStatusConfig(String serialNumber);
	
	public ConfigReaderTO getConfigReader(String serialNumber);

	public ResultTO getCassetteProcesses(String serialNumber);
	
	public ImagesTO getImage(String serialNumber) throws IOException;


}
