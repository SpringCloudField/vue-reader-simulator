package net.opentrends.vue.simulator.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.opentrends.vue.simulator.model.CassetteType;
import net.opentrends.vue.simulator.repository.CassetteTypeRepository;
import net.opentrends.vue.simulator.service.CassetteTypeService;

@Service
public class CassetteTypeServiceImpl implements CassetteTypeService {
	
	@Autowired
	private CassetteTypeRepository cassetteTypeRepository;

	@Override
	public List<CassetteType> getAllCassetteType() {
		// TODO to TO		
		return cassetteTypeRepository.findByOrderByCodeAsc();		
	}

}
