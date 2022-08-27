package pl.futuresoft.judo.backend.unit
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.repository.DisciplineClubRepository
import pl.futuresoft.judo.backend.repository.DisciplineRepository
import pl.futuresoft.judo.backend.repository.ClubRepository
import spock.lang.Specification;
import spock.lang.Unroll
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import pl.futuresoft.judo.backend.entity.Club
import pl.futuresoft.judo.backend.service.DisciplineClubService
import pl.futuresoft.judo.backend.service.DisciplineService
import pl.futuresoft.judo.backend.entity.Discipline
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class DisciplineClubServiceUnitTest extends Specification {
	   
	DisciplineClubService disciplineClubService

	ClubRepository clubRepository=Mock()
	DisciplineRepository disciplineRepository=Mock()
	DisciplineClubRepository disciplineClubRepository=Mock()
	DisciplineService disciplineService = Mock()

	
	def setup() {
		disciplineClubService = new DisciplineClubService(
				disciplineClubRepository,
				clubRepository,
				disciplineRepository,
				disciplineService)
	}
	@Unroll
	def "Add discipline to club for clubId=#clubId and disciplineId=#disciplineId" () {
		given:
			clubRepository.findById(clubId) >> Optional.of(Club.builder()
				.clubId(clubId)
				.name("test")
				.addressEmail("test")
				.deleted(false)
				.domain("test")
				.phoneNumber("test")
				.build())
			disciplineRepository.findById(disciplineId) >> Optional.of(Discipline.builder()
				.disciplineId(disciplineId)
				.name("test")
				.description("test")
				.build())
		when: 'put data'
				def item = disciplineClubService.addDisciplineToClub(clubId,disciplineId);
		then: 'result is not null'
			item.clubId == clubId
			item.disciplineId == disciplineId
			1 * disciplineClubRepository.findAllByClubIdByDisciplineId(clubId, disciplineId)
			1 * disciplineClubRepository.save(_)
		
		where:
			clubId	|	disciplineId
			2	|	1
			2	|	2
	}
	
	@Unroll
	def "Add discipline to club when clubId or disciplineId is null for clubId=#clubId and disciplineId=#disciplineId" () {
		given:
			clubRepository.findById(clubId) >> Optional.of(Club.builder()
				.clubId(clubId)
				.name("test")
				.addressEmail("test")
				.deleted(false)
				.domain("test")
				.phoneNumber("test")
				.build())
			disciplineRepository.findById(disciplineId) >> Optional.of(Discipline.builder()
				.disciplineId(disciplineId)
				.name("test")
				.description("test")
				.build())
		when: 'put data'
				def item = disciplineClubService.addDisciplineToClub(clubId,disciplineId);
		then: 'thrown exception'
			thrown IllegalArgumentException
		where:
			clubId	|	disciplineId
			1	|	null
			null	|	1
			null	|	null
		
	}
	
	@Unroll
	def "Add not existing discipline to club for clubId=#clubId and disciplineId=#disciplineId" () {
		given:
			clubRepository.findById(clubId) >> Optional.of(Club.builder()
				.clubId(clubId)
				.name("test")
				.addressEmail("test")
				.deleted(false)
				.domain("test")
				.phoneNumber("test")
				.build())
			disciplineRepository.findById(disciplineId) >> Optional.empty()
		when: 'put data'
				def item = disciplineClubService.addDisciplineToClub(clubId,disciplineId);
		then: 'thrown exception'
			thrown EntityNotFoundException
		where:
			clubId	|	disciplineId
			1	|	8
	}
}