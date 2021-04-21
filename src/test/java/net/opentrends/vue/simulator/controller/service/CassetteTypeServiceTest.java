package net.opentrends.vue.simulator.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.model.CassetteType;
import net.opentrends.vue.simulator.repository.CassetteTypeRepository;
import net.opentrends.vue.simulator.service.impl.CassetteTypeServiceImpl;


@ExtendWith(SpringExtension.class)
public class CassetteTypeServiceTest {
	
	@Mock
	private CassetteTypeRepository cassetteTypeRepository;
	@Mock
	private ModelMapper mapper;
	@InjectMocks
	private CassetteTypeServiceImpl cassetteTypeService;
	
	
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
