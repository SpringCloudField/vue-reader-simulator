package net.opentrends.vue.simulator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.opentrends.vue.simulator.model.CassetteType;

public interface CassetteTypeRepository  extends MongoRepository<CassetteType, String> {
	
	List<CassetteType> findByOrderByCodeAsc();
}
