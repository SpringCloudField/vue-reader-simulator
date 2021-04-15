package net.opentrends.vue.simulator.service;

import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.StatusTO;

public interface SimulatorService {
	
	public StatusTO getStatusConfig(String serialNumber);
	
	public ConfigReaderTO getConfigReader(String serialNumber);

	public ResultTO getCassetteProcesses(String serialNumber);


}
