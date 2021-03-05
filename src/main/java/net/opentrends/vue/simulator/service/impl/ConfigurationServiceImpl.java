package net.opentrends.vue.simulator.service.impl;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.model.Configuration;
import net.opentrends.vue.simulator.model.User;
import net.opentrends.vue.simulator.repository.ConfigurationRepository;
import net.opentrends.vue.simulator.repository.UserRepository;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.CustomUserDetailsService;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	@Autowired
	private UserRepository userRespository;
	@Autowired
	private ConfigurationRepository configRespository;
	@Autowired
	private CustomUserDetailsService userService;

	@Override
	public void saveConfig(User user, Configuration config) {
		configRespository.save(config);
//		user.getConfigs().clear();
		user.setConfigs(new HashSet<>(Arrays.asList(config)));
		userRespository.save(user);		
	}

	@Override
	public ConfigurationTO getConfig(String username) {
		User user = userService.findUserByEmail(username);
		// TODO: get config to TO		
		return null;
	}

}
