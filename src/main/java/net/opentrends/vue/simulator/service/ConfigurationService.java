package net.opentrends.vue.simulator.service;

import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.model.Configuration;
import net.opentrends.vue.simulator.model.User;

public interface ConfigurationService {

	public void saveConfig(User user, Configuration config); 
	
	public ConfigurationTO getConfig(String username);
}
