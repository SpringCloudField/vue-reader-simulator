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
	            Role newAdminRole = new Role();
	            newAdminRole.setRole("ADMIN");
	            roleRepository.save(newAdminRole);
	        }

	        Role userRole = roleRepository.findByRole("USER");
	        if (userRole == null) {
	            Role newUserRole = new Role();
	            newUserRole.setRole("USER");
	            roleRepository.save(newUserRole);
			}
	        
	        List<CassetteType> cassetteTypeList = cassetteTypeRepository.findAll();
	        if (cassetteTypeList.isEmpty()) {
	        	CassetteType newCassetteType = new CassetteType();
	            newCassetteType.setCode(1);
	        	newCassetteType.setType("Anaplasma");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(2);
	        	newCassetteType.setType("FeLV_Fiv");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(3);
	        	newCassetteType.setType("Ehrlichia");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(4);
	        	newCassetteType.setType("Lyme");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(5);
	        	newCassetteType.setType("Parvo");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(6);
	        	newCassetteType.setType("Giardia");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(7);
	        	newCassetteType.setType("Heartworm");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(8);
	        	newCassetteType.setType("cPL");
	            cassetteTypeRepository.save(newCassetteType);
	            
	            newCassetteType = new CassetteType();
	            newCassetteType.setCode(9);
	        	newCassetteType.setType("Flex4");
	            cassetteTypeRepository.save(newCassetteType);
	        }
		};

	}
	
}
