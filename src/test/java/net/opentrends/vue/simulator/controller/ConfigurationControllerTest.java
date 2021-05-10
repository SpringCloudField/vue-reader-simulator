package net.opentrends.vue.simulator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.model.User;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.CustomUserDetailsService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ConfigurationControllerTest {

	@InjectMocks
	private ConfiguratorController configuratorController;
	@Mock
	private CustomUserDetailsService userService;
	@Mock
	private CassetteTypeService cassetteTypeService;
	@Mock
	private ConfigurationService configurationService;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		Authentication auth = Mockito.mock(Authentication.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(auth);
		when(auth.getName()).thenReturn("mockedUserName");
		SecurityContextHolder.setContext(securityContext);
		//Reconfigure the view resolver in test
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB_INF/views");
		resolver.setSuffix(".jsp");
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(configuratorController).setViewResolvers(resolver).build();
	}

	@Test
	public void getDashboardTest() throws Exception {
		User userMock = new User();
		userMock.setEmail("email");
		userMock.setEnabled(true);
		userMock.setFullName("full name");
		userMock.setId("id");
		when(userService.findUserByEmail(any())).thenReturn(userMock);
		when(configurationService.getConfigsByUserId(any())).thenReturn(Arrays.asList(new ConfigurationTO()));
		mockMvc.perform(get("/dashboard"))
			.andExpect(status().isOk())
			.andExpect(view().name("dashboard"))
			.andExpect(model().attributeExists("currentUser"))
			.andExpect(model().attributeExists("configs"))
			.andExpect(model().attribute("adminMessage", "Content Available Only for Users with Admin Role"));
	}
	
	@Test
	public void getSimulatorTest() throws Exception {
		User userMock = new User();
		userMock.setEmail("email");
		userMock.setEnabled(true);
		userMock.setFullName("full name");
		userMock.setId("id");
		when(userService.findUserByEmail(any())).thenReturn(userMock);
		when(configurationService.getConfigsByUserId(any())).thenReturn(Arrays.asList(new ConfigurationTO()));
		when(cassetteTypeService.getAllCassetteType()).thenReturn(Arrays.asList(new CassetteTypeTO()));
		mockMvc.perform(get("/simulator/{id}", "configId"))
			.andExpect(status().isOk())
			.andExpect(view().name("simulator"))
			.andExpect(model().attributeExists("currentUser"))
			.andExpect(model().attributeExists("configurationTO"))
			.andExpect(model().attributeExists("cassetteTypes"));
	}
	
	@Test
	public void getSaveConfigTest() throws Exception {
		
	}

}
