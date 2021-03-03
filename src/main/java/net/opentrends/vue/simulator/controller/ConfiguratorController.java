package net.opentrends.vue.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.opentrends.vue.simulator.model.User;
import net.opentrends.vue.simulator.service.impl.CustomUserDetailsService;

@Controller
public class ConfiguratorController {
	
	@Autowired
	private CustomUserDetailsService userService;

		
	@RequestMapping(value = "/simulator", method = RequestMethod.GET)
	public ModelAndView simulator() {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    modelAndView.addObject("currentUser", user);
	    modelAndView.setViewName("simulator");
	    return modelAndView;
	}

}
