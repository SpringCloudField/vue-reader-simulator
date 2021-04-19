package net.opentrends.vue.simulator.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.impl.SimulatorServiceImpl;

@ExtendWith(SpringExtension.class)
public class SimulatorServiceTest {
	
	@Mock
	private ConfigurationService configurationService;
	
	@Mock
	private CassetteTypeService cassetteTypeService;
	
	@InjectMocks
	private SimulatorServiceImpl simulatorService;
	
	@Test
	public void test001_getImages() throws IOException {		
		ImagesTO to = simulatorService.getImage("dummy");
		assertNotNull(to);
		assertNotNull(to.getImage());
		assertEquals(1, to.getId());		
	}

}
