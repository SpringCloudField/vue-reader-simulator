package net.opentrends.vue.simulator.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.opentrends.vue.simulator.repository.CassetteTypeRepository;
import net.opentrends.vue.simulator.repository.ConfigurationRepository;
import net.opentrends.vue.simulator.repository.RoleRepository;
import net.opentrends.vue.simulator.repository.UserRepository;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.CustomUserDetailsService;
import net.opentrends.vue.simulator.service.SimulatorService;
import net.opentrends.vue.simulator.service.impl.CassetteTypeServiceImpl;
import net.opentrends.vue.simulator.service.impl.ConfigurationServiceImpl;
import net.opentrends.vue.simulator.service.impl.CustomUserDetailsServiceImpl;
import net.opentrends.vue.simulator.service.impl.SimulatorServiceImpl;
import net.opentrends.vue.simulator.service.impl.UserDetailsServiceImpl;

@Configuration
public class ServiceConfig {
	
	@Bean
	public CassetteTypeService cassetteTypeService(CassetteTypeRepository cassetteTypeRepository, ModelMapper mapper) {
		return new CassetteTypeServiceImpl(cassetteTypeRepository, mapper);
	}

	@Bean
	public ConfigurationService configurationService(ConfigurationRepository configRespository, ModelMapper mapper) {
		return new ConfigurationServiceImpl(configRespository, mapper);
	}
	
	@Bean
	public SimulatorService simulatorService(ConfigurationService configurationService, CassetteTypeService cassetteTypeService, ResourceLoader resourceLoader) {
		return new SimulatorServiceImpl(configurationService, cassetteTypeService, resourceLoader);
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
	    return new UserDetailsServiceImpl(userRepository);
	}
	
	@Bean
	public CustomUserDetailsService customUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
	    return new CustomUserDetailsServiceImpl(userRepository, roleRepository, bCryptPasswordEncoder);
	}
}
