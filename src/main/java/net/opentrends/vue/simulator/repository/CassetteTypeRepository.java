package net.opentrends.vue.simulator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.model.CassetteType;

public interface CassetteTypeRepository extends MongoRepository<CassetteType, String> {

	List<CassetteType> findByOrderByCodeAsc();

	CassetteTypeTO findByCode(Integer code);
}
