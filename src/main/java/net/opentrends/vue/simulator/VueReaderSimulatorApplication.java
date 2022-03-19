package net.opentrends.vue.simulator;

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

	        if (roleRepository.findByRole("ADMIN") == null) {
	            Role newAdminRole = Role.builder().role("ADMIN").build();
	            roleRepository.save(newAdminRole);
	        }

	        if (roleRepository.findByRole("USER") == null) {
	            Role newUserRole = Role.builder().role("USER").build();
	            roleRepository.save(newUserRole);
			}
	        
	        if (roleRepository.findByRole("API") == null) {
	            Role newApiRole = Role.builder().role("API").build();
	            roleRepository.save(newApiRole);
			}
	        
	        if (cassetteTypeRepository.findAll().isEmpty()) {
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
