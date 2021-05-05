package net.opentrends.vue.simulator.controller.service;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.exception.AppRuntimeException;
import net.opentrends.vue.simulator.model.Configuration;
import net.opentrends.vue.simulator.repository.ConfigurationRepository;
import net.opentrends.vue.simulator.service.impl.ConfigurationServiceImpl;

@ExtendWith(SpringExtension.class)
public class ConfigurationServiceTest {
	
	@Mock
	private ConfigurationRepository configRespository;
	@Mock
	private ModelMapper mapper;
	@InjectMocks
	private ConfigurationServiceImpl configurationService;
	
	
	@Test
	public void getConfigBySerialNumberTest_SN_not_exist() {
		when(configRespository.findBySerialNumber(any())).thenReturn(null);
		
		assertThrows(AppRuntimeException.class, () -> {
			configurationService.getConfigBySerialNumber("serial_number");
		}, "There is no SN with name serial_number");
	}
	
	@Test
	public void getConfigBySerialNumberTest() {
		when(configRespository.findBySerialNumber(any())).thenReturn(new Configuration());
		when(mapper.map(any(), any())).thenReturn(new ConfigurationTO());
		
		ConfigurationTO confTO = configurationService.getConfigBySerialNumber("serial_number");
		assertNotNull(confTO);
	}
	
	@Test
	public void saveConfigTest() {
		when(mapper.map(any(), any())).thenReturn(new Configuration());
		configurationService.saveConfig(new ConfigurationTO(), "userId");
		verify(configRespository).save(any(Configuration.class));
	}
	
	@Test
	public void getConfigById_NoExistConfig_Test() {
		when(mapper.map(any(), any())).thenReturn(new Configuration());
		when(configRespository.findById(any())).thenReturn(empty());		
		ConfigurationTO configTO = configurationService.getConfigById("configId");
		assertEquals(null, configTO);
	}
	
	@Test
	public void getConfigByIdTest() {
		when(mapper.map(any(), any())).thenReturn(new ConfigurationTO());
		when(configRespository.findById(any())).thenReturn(of(new Configuration()));		
		ConfigurationTO configTO = configurationService.getConfigById("configId");
		assertNotNull(configTO);
	}

}
