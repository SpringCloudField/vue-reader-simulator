package net.opentrends.vue.simulator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.opentrends.vue.simulator.dto.ConfigReaderResponseTO;
import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.service.SimulatorService;
import net.opentrends.vue.simulator.service.impl.SimulatorServiceImpl;

@ExtendWith(SpringExtension.class)
public class SimulatorControllerTest {
	
	@Mock
	private SimulatorService simulatorService;
	@InjectMocks
	private SimulatorController simulatorController;
	
	@Test
	public void configTest() throws Exception {		
		when(simulatorService.getConfigReader(any())).thenReturn(new ConfigReaderTO() );
		ResponseEntity<ConfigReaderResponseTO> response = simulatorController.config("serial_number");
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
