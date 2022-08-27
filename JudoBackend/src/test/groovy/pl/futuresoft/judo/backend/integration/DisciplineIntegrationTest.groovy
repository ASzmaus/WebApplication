package pl.futuresoft.judo.backend.integration


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.repository.DisciplineRepository
import pl.futuresoft.judo.backend.service.DisciplineService
import spock.lang.Specification;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class DisciplineIntegrationTest extends Specification {
	   
	@Autowired
	DisciplineService disciplineService
	
	@Autowired
	DisciplineRepository disciplineRepository
	
	def "get list of discipline" ()
	{
		when: 'get list '
			def list = disciplineService.disciplineList();
		then: 'list is not null'
			list!=null
		and: 'list contains a proper discipline'
			list[2].name=="karate"
		and: 'list contains a proper discipline'
			 list[1].name=="judo"
		and: 'list contains a proper discipline'
			 list[0].name=="aikido"
	}
}