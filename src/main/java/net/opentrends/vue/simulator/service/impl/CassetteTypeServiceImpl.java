package net.opentrends.vue.simulator.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.repository.CassetteTypeRepository;
import net.opentrends.vue.simulator.service.CassetteTypeService;

@Service
public class CassetteTypeServiceImpl implements CassetteTypeService {

	@Autowired
	private CassetteTypeRepository cassetteTypeRepository;
	@Autowired
	private ModelMapper mapper;

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
