package net.opentrends.vue.simulator.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.model.Configuration;
import net.opentrends.vue.simulator.repository.ConfigurationRepository;
import net.opentrends.vue.simulator.service.ConfigurationService;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	@Autowired
	private ConfigurationRepository configRespository;
	@Autowired
	private ModelMapper mapper;

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
	public boolean existSerialNumber(String serialNumber, String userId) {
		return configRespository.existsBySerialNumberAndUserIdNot(serialNumber, userId);
	}	

}
