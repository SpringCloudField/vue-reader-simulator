package net.opentrends.vue.simulator;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.opentrends.vue.simulator.model.CassetteType;
import net.opentrends.vue.simulator.model.Role;
import net.opentrends.vue.simulator.repository.CassetteTypeRepository;
import net.opentrends.vue.simulator.repository.RoleRepository;

@SpringBootApplication
public class VueReaderSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VueReaderSimulatorApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(RoleRepository roleRepository, CassetteTypeRepository cassetteTypeRepository) {

	    return args -> {

	        Role adminRole = roleRepository.findByRole("ADMIN");
	        if (adminRole == null) {
	            Role newAdminRole = Role.builder().role("ADMIN").build();
	            roleRepository.save(newAdminRole);
	        }

	        Role userRole = roleRepository.findByRole("USER");
	        if (userRole == null) {
	            Role newUserRole = Role.builder().role("USER").build();
	            roleRepository.save(newUserRole);
			}
	        
	        Role apiRole = roleRepository.findByRole("API");
	        if (apiRole == null) {
	            Role newApiRole = Role.builder().role("API").build();
	            roleRepository.save(newApiRole);
			}
	        
	        List<CassetteType> cassetteTypeList = cassetteTypeRepository.findAll();
	        if (cassetteTypeList.isEmpty()) {
	        	CassetteType newCassetteType = CassetteType.builder().code(1).type("Anaplasma").build();
	        	cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(2).type("FeLV_Fiv").build();
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(3).type("Ehrlichia").build();
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(4).type("Lyme").build();
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(5).type("Parvo").build();
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(6).type("Giardia").build();
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(7).type("Heartworm").build();
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(8).type("cPL").build();
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = CassetteType.builder().code(9).type("Flex4").build();
	            cassetteTypeRepository.save(newCassetteType);
	        }
		};

	}
	
}
