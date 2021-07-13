package net.opentrends.vue.simulator.controller;

import static java.util.Optional.ofNullable;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.model.User;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.CustomUserDetailsService;
import net.opentrends.vue.simulator.validator.ConfigurationValidator;

@Controller
public class ConfiguratorController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguratorController.class);
	
	private final CustomUserDetailsService userService;
	private final CassetteTypeService cassetteTypeService; 
	private final ConfigurationService configurationService;
	private final ConfigurationValidator configValidator;
	
	public ConfiguratorController(CustomUserDetailsService userService, CassetteTypeService cassetteTypeService, ConfigurationService configurationService,
			ConfigurationValidator configValidator) {
		this.userService = userService;
		this.cassetteTypeService = cassetteTypeService;
		this.configurationService = configurationService;
		this.configValidator = configValidator;
	}
	
	@InitBinder(value = "configurationTO")
    void initStudentValidator(WebDataBinder binder) {
        binder.setValidator(configValidator);
    }
	
	@GetMapping(value = "/dashboard")
	public ModelAndView dashboard() {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("configs", configurationService.getConfigsByUserId(user.getId()));
	    modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
	    modelAndView.setViewName("dashboard");
	    return modelAndView;
	}
	
	@GetMapping(value = {"/simulator", "/simulator/{id}"})
	public ModelAndView configSimulator(@PathVariable(value = "id", required = false) String configId) {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("cassetteTypes", cassetteTypeService.getAllCassetteType());
		ConfigurationTO configTo = ofNullable(configId)
			.map(x -> configurationService.getConfigById(configId))
			.orElse(new ConfigurationTO());
	    modelAndView.addObject("configurationTO", configTo);	   
	    modelAndView.setViewName("simulator");
	    return modelAndView;
	}
	
	@PostMapping(value = "/saveConfig")
	public ModelAndView saveConfig(@Valid ConfigurationTO configurationTO, BindingResult bindingResult, ModelAndView modelAndView) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    
	    modelAndView.addObject("currentUser", user);
	    
	    // Check if serial_number is in used for other user.
	    if (configurationService.existSerialNumber(configurationTO.getSerialNumber(), user.getId())) {
	    	bindingResult.rejectValue("serialNumber", "serialNumber", "serial_number is in used for other users. Please change it.");
	    }
	    if (bindingResult.hasErrors()) {
	    	modelAndView.addObject("cassetteTypes", cassetteTypeService.getAllCassetteType());
			modelAndView.addObject("configurationTO", configurationTO);
			modelAndView.setViewName("simulator");
			return modelAndView;
	    }
	    
		configurationService.saveConfig(configurationTO, user.getId());		
		modelAndView.setViewName("saved");
		
		LOG.info("Config updated. configId: {}", configurationTO.getId());
		return modelAndView;
	}

}
