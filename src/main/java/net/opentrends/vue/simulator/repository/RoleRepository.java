package net.opentrends.vue.simulator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.opentrends.vue.simulator.model.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
	
	Role findByRole(String role);
}
