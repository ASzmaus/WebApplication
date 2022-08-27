package pl.futuresoft.judo.backend.integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.exception.IdAlreadyAddedException;
import pl.futuresoft.judo.backend.repository.DisciplineClubRepository;
import pl.futuresoft.judo.backend.service.DisciplineClubService;
import spock.lang.Specification;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class DisciplineClubIntegrationTest extends Specification {
	   
	@Autowired
    DisciplineClubService disciplineClubService

	@Autowired
    DisciplineClubRepository disciplineClubRepository

	def "Add discipline to club" () {
		when: 'put data'
			def item = disciplineClubService.addDisciplineToClub(2,1);
		then: 'result is not null'
			item!=null
		and: 'item contains clubId'
			item.clubId==2
		and: 'item contains disciplineId'
			item.disciplineId==1
	}
	
	def "Add discipline to club when discipline and club Id does not exist" () {
		when: 'put data'
			def item = disciplineClubService.addDisciplineToClub(4,5);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}
	
	def "add discipline to club when disciplineId has been already added" () {
		when: 'put data'
			def item = disciplineClubService.addDisciplineToClub(1,1);
		then: 'thrown exception'
			thrown IdAlreadyAddedException
	}
	
	def "delete discipline from clubService" () {
		given: 
			def oldCount = disciplineClubRepository.count();
		when: 'delete'
			def result = disciplineClubService.deleteDisciplineFromClub(2,2);
			def	item = disciplineClubRepository.findAllByClubIdByDisciplineId(1,1);
		then:'repository contains 1 object'
			disciplineClubRepository.count()==oldCount-1
		and:'item contains non deleted clubId'
			item.clubId==1
		and: 'item contains non deleted disciplineId'
			item.disciplineId==1
	}
	
	def "delete discipline from club when disciplineId and clubId does not exist" () {
		when: 'delete'
			disciplineClubService.deleteDisciplineFromClub(8, 8);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}
	
	def "get list of discipline by clubId" () {
		when: 'get'
			def list = disciplineClubService.disciplineListByClub(1);
		then: 'result is not null'
			list!=null
		and: 'items contains a proper discipline'
			list[0].name=="aikido"
	}
	
	def "get list of discipline by clubId when clubId does not exist" () {
		when: 'get'
			def list = disciplineClubService.disciplineListByClub(8);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}
}