package net.opentrends.vue.simulator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/home").setViewName("home");
	    registry.addViewController("/").setViewName("home");
		registry.addViewController("/dashboard").setViewName("dashboard");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/simulator").setViewName("simulator");
	}
}
