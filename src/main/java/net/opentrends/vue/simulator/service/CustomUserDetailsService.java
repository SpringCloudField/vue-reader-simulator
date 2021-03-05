package net.opentrends.vue.simulator.service;

import net.opentrends.vue.simulator.model.User;

public interface CustomUserDetailsService {

	public User findUserByEmail(String email);
	
	public void saveUser(User user);

}
