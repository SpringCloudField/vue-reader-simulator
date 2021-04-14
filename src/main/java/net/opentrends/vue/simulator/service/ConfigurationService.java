package net.opentrends.vue.simulator.service;

import java.util.List;

import net.opentrends.vue.simulator.dto.ConfigTO;
import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.StatusTO;

public interface ConfigurationService {

	public void saveConfig(ConfigurationTO config, String userId); 
	
	public ConfigurationTO getConfigById(String configId);
	
	public List<ConfigurationTO> getConfigsByUserId(String idUser);
	
	public boolean existSerialNumber(String serialNumber, String userId);
	public StatusTO getStatusConfig(String serialNumber);
	
	public ConfigTO getConfig(String serialNumber);

	public ResultTO getCassetteProcesses(String serialNumber);
}
