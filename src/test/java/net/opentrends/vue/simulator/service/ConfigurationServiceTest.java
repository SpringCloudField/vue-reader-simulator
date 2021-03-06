package net.opentrends.vue.simulator.service;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.exception.AppRuntimeException;
import net.opentrends.vue.simulator.model.Configuration;
import net.opentrends.vue.simulator.repository.ConfigurationRepository;
import net.opentrends.vue.simulator.service.impl.ConfigurationServiceImpl;

public class ConfigurationServiceTest {
	
	private ConfigurationRepository configRespository;
	private ModelMapper mapper;
	private ConfigurationService configurationService;
	
	@BeforeEach
	public void init() {
		configRespository = mock(ConfigurationRepository.class);
		mapper = mock(ModelMapper.class);
		configurationService = new ConfigurationServiceImpl(configRespository, mapper);
	}
	
	@Test
	public void should_not_return_reader_config_by_sn_not_exists() {
		when(configRespository.findBySerialNumber(any())).thenReturn(null);
		
		assertThrows(AppRuntimeException.class, () -> {
			configurationService.getConfigBySerialNumber("serial_number");
		}, "There is no SN with name serial_number");
	}
	
	@Test
	public void should_return_reader_config_by_sn_not() {
		when(configRespository.findBySerialNumber(any())).thenReturn(new Configuration());
		when(mapper.map(any(), any())).thenReturn(new ConfigurationTO());
		
		ConfigurationTO confTO = configurationService.getConfigBySerialNumber("serial_number");
		assertNotNull(confTO);
	}
	
	@Test
	public void should_save_reader_config() {
		when(mapper.map(any(), any())).thenReturn(new Configuration());
		configurationService.saveConfig(new ConfigurationTO(), "userId");
		verify(configRespository).save(any(Configuration.class));
	}
	
	@Test
	public void should_not_return_reader_config_by_config_id_not_exists() {
		when(mapper.map(any(), any())).thenReturn(new Configuration());
		when(configRespository.findById(any())).thenReturn(empty());		
		ConfigurationTO configTO = configurationService.getConfigById("configId");
		assertEquals(null, configTO);
	}
	
	@Test
	public void should_return_reader_config_by_config_id() {
		when(mapper.map(any(), any())).thenReturn(new ConfigurationTO());
		when(configRespository.findById(any())).thenReturn(of(new Configuration()));		
		ConfigurationTO configTO = configurationService.getConfigById("configId");
		assertNotNull(configTO);
	}

}
