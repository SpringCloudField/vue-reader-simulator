package net.opentrends.vue.simulator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.opentrends.vue.simulator.model.Configuration;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
	
	public List<Configuration> findByUserId(String idUser);
	
	public Optional<Configuration> findBySerialNumber(String serialNumber);

}
