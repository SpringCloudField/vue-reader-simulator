package net.opentrends.vue.simulator.controller;

import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.opentrends.vue.simulator.dto.ConfigurationTO;
import net.opentrends.vue.simulator.model.User;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.ConfigurationService;
import net.opentrends.vue.simulator.service.CustomUserDetailsService;

@Controller
public class ConfiguratorController {
	
	@Autowired
	private CustomUserDetailsService userService;
	@Autowired
	private CassetteTypeService cassetteTypeService; 
	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
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
	
	@RequestMapping(value = {"/simulator", "/simulator/{id}"}, method = RequestMethod.GET)
	public ModelAndView configSimulator(@PathVariable(value = "id", required = false) String configId) {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("cassetteTypes", cassetteTypeService.getAllCassetteType());
		ConfigurationTO configTo = ofNullable(configId)
			.map(x -> configurationService.getConfigById(configId))
			.orElse(new ConfigurationTO());
	    modelAndView.addObject("config", configTo);	   
	    modelAndView.setViewName("simulator");
	    return modelAndView;
	}
	
	@RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
	public ModelAndView saveConfig(ConfigurationTO config, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Validation in backend
	    }
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
		configurationService.saveConfig(config, user.getId());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("currentUser", user);
		modelAndView.setViewName("saved");
		
		return modelAndView;		
	}

}
