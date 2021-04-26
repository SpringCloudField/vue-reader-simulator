package net.opentrends.vue.simulator.controller.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
		when(configRespository.findBySerialNumber(any())).thenReturn(new Configuration());
				
		assertThrows(AppRuntimeException.class, () -> {
			configurationService.getConfigBySerialNumber("serial_number");
		}, "There is no SN with name serial_number");
	}

}
