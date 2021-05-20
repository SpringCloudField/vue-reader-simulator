package net.opentrends.vue.simulator.service;

import java.util.List;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;

public interface CassetteTypeService {

	List<CassetteTypeTO> getAllCassetteType();
	
	CassetteTypeTO getCassetteTypeByCode(Integer code);
}
