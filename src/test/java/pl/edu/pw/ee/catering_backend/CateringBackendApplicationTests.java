package pl.edu.pw.ee.catering_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class CateringBackendApplicationTests {
	
	ApplicationModules modules = ApplicationModules.of(CateringBackendApplication.class);

	@Test
	void contextLoads() {
	}
	
	@Test
	void verifiesModularStructure() {
		modules.verify();
	}
	
	@Test
	void createModuleDocumentation() {
		new Documenter(modules)
				.writeDocumentation()
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}
}
