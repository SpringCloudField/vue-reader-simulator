package net.opentrends.vue.simulator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.opentrends.vue.simulator.model.Configuration;
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
		
	@RequestMapping(value = "/simulator", method = RequestMethod.GET)
	public ModelAndView simulator() {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("cassetteTypes", cassetteTypeService.getAllCassetteType());
	    
	    // TODO: retrieve from db, or from user directly
	    Configuration co = new Configuration();
	    co.setSerialNumber("rogertest");
	    modelAndView.addObject("config", co);
	    
	    modelAndView.setViewName("simulator");
	    return modelAndView;
	}
	
	@RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Configuration config, BindingResult bindingResult) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());	    
		configurationService.saveConfig(user, config);	
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("currentUser", user);
		modelAndView.setViewName("saved");
		
		return modelAndView;
		
	}

}
