package net.opentrends.vue.simulator.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.repository.CassetteTypeRepository;
import net.opentrends.vue.simulator.service.CassetteTypeService;

public class CassetteTypeServiceImpl implements CassetteTypeService {

	private final CassetteTypeRepository cassetteTypeRepository;
	private final ModelMapper mapper;
	
	public CassetteTypeServiceImpl(CassetteTypeRepository cassetteTypeRepository, ModelMapper mapper) {
		this.mapper = mapper;
		this.cassetteTypeRepository = cassetteTypeRepository;
	}

	@Override
	public List<CassetteTypeTO> getAllCassetteType() {
		return cassetteTypeRepository.findByOrderByCodeAsc()
			.stream()
			.map(cassette -> mapper.map(cassette, CassetteTypeTO.class))
			.collect(Collectors.toList());
	}

	@Override
	public CassetteTypeTO getCassetteTypeByCode(Integer code) {
		return cassetteTypeRepository.findByCode(code);
	}

}
