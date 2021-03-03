package net.opentrends.vue.simulator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.opentrends.vue.simulator.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	User findByEmail(String email);

}
