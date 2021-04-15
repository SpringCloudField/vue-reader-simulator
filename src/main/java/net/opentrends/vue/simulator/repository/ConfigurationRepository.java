package net.opentrends.vue.simulator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.opentrends.vue.simulator.model.Configuration;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
	
	public List<Configuration> findByUserId(String idUser);
	
	public Configuration findBySerialNumber(String serialNumber);
	
	public boolean existsBySerialNumberAndUserIdNot(String seriaNumber, String idUser);

}
