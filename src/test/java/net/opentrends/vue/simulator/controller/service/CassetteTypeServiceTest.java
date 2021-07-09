package net.opentrends.vue.simulator.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.model.CassetteType;
import net.opentrends.vue.simulator.repository.CassetteTypeRepository;
import net.opentrends.vue.simulator.service.CassetteTypeService;
import net.opentrends.vue.simulator.service.impl.CassetteTypeServiceImpl;


public class CassetteTypeServiceTest {
	
	private CassetteTypeRepository cassetteTypeRepository;
	private ModelMapper mapper;
	private CassetteTypeService cassetteTypeService;
	
	@BeforeEach
	public void init() {
		cassetteTypeRepository = mock(CassetteTypeRepository.class);
		mapper = mock(ModelMapper.class);
		cassetteTypeService = new CassetteTypeServiceImpl(cassetteTypeRepository, mapper);
	}
	
	
	@Test
	public void test001_retrieveCassetteTypeList() {
		final List<CassetteType> cassetteList = createCassetteTypeList();		
		when(cassetteTypeRepository.findByOrderByCodeAsc()).thenReturn(cassetteList);		
		List<CassetteTypeTO> dtoList = cassetteTypeService.getAllCassetteType();
		assertEquals(2, dtoList.size());		
	}

	private List<CassetteType> createCassetteTypeList() {
		List<CassetteType> list = new ArrayList<>();
		
		CassetteType ct1 = new CassetteType();
		ct1.setType("Anaplasma");
		ct1.setCode(2);
		list.add(ct1);
		
		CassetteType ct2 = new CassetteType();
		ct2.setType("FeLV_Fiv");
		ct2.setCode(1);
		list.add(ct2);
		
		return list;
	}

}
