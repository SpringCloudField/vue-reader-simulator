package net.opentrends.vue.simulator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.opentrends.vue.simulator.model.Configuration;

public interface ConfigurationRespository extends MongoRepository<Configuration, String> {

}
