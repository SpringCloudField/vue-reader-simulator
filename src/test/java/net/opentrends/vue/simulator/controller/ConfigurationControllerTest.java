package net.opentrends.vue.simulator.controller;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import net.opentrends.vue.simulator.validator.ConfigurationValidator;

@ExtendWith(SpringExtension.class)
public class ConfigurationControllerTest {

	private ConfiguratorController configuratorController;
	private CustomUserDetailsService userService;
	private CassetteTypeService cassetteTypeService;
	private ConfigurationService configurationService;
	private ConfigurationValidator configValidator;
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		userService = mock(CustomUserDetailsService.class);
		cassetteTypeService = mock(CassetteTypeService.class);
		configurationService = mock(ConfigurationService.class);						
		configuratorController = new ConfiguratorController(userService, cassetteTypeService, configurationService, configValidator);
		Authentication auth = Mockito.mock(Authentication.class);
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
	public void should_return_dashboard_page() throws Exception {
		when(userService.findUserByEmail(any())).thenReturn(createUserMock());
		when(configurationService.getConfigsByUserId(any())).thenReturn(asList(new ConfigurationTO()));
		
		mockMvc.perform(get("/dashboard"))
			.andExpect(status().isOk())
			.andExpect(view().name("dashboard"))
			.andExpect(model().attributeExists("currentUser"))
			.andExpect(model().attributeExists("configs"))
			.andExpect(model().attribute("adminMessage", "Content Available Only for Users with Admin Role"));
	}
	
	@Test
	public void should_return_simulator_config_page() throws Exception {
		when(userService.findUserByEmail(any())).thenReturn(createUserMock());
		when(configurationService.getConfigsByUserId(any())).thenReturn(asList(new ConfigurationTO()));
		when(cassetteTypeService.getAllCassetteType()).thenReturn(asList(new CassetteTypeTO()));
		
		mockMvc.perform(get("/simulator/{id}", "configId"))
			.andExpect(status().isOk())
			.andExpect(view().name("simulator"))
			.andExpect(model().attributeExists("currentUser"))
			.andExpect(model().attributeExists("configurationTO"))
			.andExpect(model().attributeExists("cassetteTypes"));
	}
	
	@Test
	public void should_return_error_when_sn_is_in_used() throws Exception {
		when(userService.findUserByEmail(any())).thenReturn(createUserMock());
		when(configurationService.getConfigsByUserId(any())).thenReturn(asList(new ConfigurationTO()));
		when(cassetteTypeService.getAllCassetteType()).thenReturn(asList(new CassetteTypeTO()));
		when(configurationService.existSerialNumber(any(), any())).thenReturn(Boolean.TRUE);

		mockMvc.perform(post("/saveConfig").flashAttr("configurationTO", new ConfigurationTO()))
			.andExpect(status().isOk())
			.andExpect(view().name("simulator"))
			.andExpect(model().attributeExists("currentUser"))
			.andExpect(model().attributeExists("configurationTO"))
			.andExpect(model().attributeExists("cassetteTypes"))
			.andExpect(model().hasErrors())
			.andExpect(model().errorCount(1));
	}
	
	@Test
	public void should_return_saved_page_when_save_config() throws Exception {
		when(userService.findUserByEmail(any())).thenReturn(createUserMock());
		when(configurationService.getConfigsByUserId(any())).thenReturn(asList(new ConfigurationTO()));
		when(cassetteTypeService.getAllCassetteType()).thenReturn(asList(new CassetteTypeTO()));
		when(configurationService.existSerialNumber(any(), any())).thenReturn(Boolean.FALSE);
		
		mockMvc.perform(post("/saveConfig").flashAttr("configurationTO", new ConfigurationTO()))
			.andExpect(status().isOk())
			.andExpect(view().name("saved"))
			.andExpect(model().attributeExists("currentUser"));
	}
	
	@Test
	private User createUserMock() {
		User userMock = new User();
		userMock.setEmail("email");
		userMock.setEnabled(true);
		userMock.setFullName("full name");
		userMock.setId("id");
		return userMock;
	}
	
	

}
