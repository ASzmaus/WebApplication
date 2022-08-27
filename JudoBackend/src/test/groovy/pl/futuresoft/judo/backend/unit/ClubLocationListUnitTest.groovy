package pl.futuresoft.judo.backend.unit
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.repository.ClubLocationRepository
import pl.futuresoft.judo.backend.repository.ClubRepository
import pl.futuresoft.judo.backend.repository.LocationRepository
import spock.lang.Specification;
import spock.lang.Unroll
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import pl.futuresoft.judo.backend.entity.Club
import pl.futuresoft.judo.backend.entity.Location
import pl.futuresoft.judo.backend.service.ClubLocationService
import pl.futuresoft.judo.backend.service.LocationService

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class ClubLocationListUnitTest extends Specification {
	   
	ClubLocationService clubLocationService

	ClubRepository clubRepository=Mock()
	LocationRepository locationRepository=Mock()
	ClubLocationRepository clubLocationRepository=Mock()
	LocationService locationService = Mock()

	
	def setup() {
		clubLocationService = new ClubLocationService(
				clubLocationRepository,
				clubRepository,
				locationRepository,
				locationService)
	}
	@Unroll
	def "Add location to club for clubId=#clubId and locationId=#locationId" ()
	{
		given:
			clubRepository.findById(clubId) >> Optional.of(Club.builder()
				.clubId(clubId)
				.name("test")
				.addressEmail("test")
				.deleted(false)
				.domain("test")
				.phoneNumber("test")
				.build())
			locationRepository.findById(locationId) >> Optional.of(Location.builder()
				.locationId(locationId)
				.street("test")
				.houseNumber(1)
				.city("test")
				.postcode("test")
				.description("test")
				.build())
		when: 'put data'
				def item = clubLocationService.addLocationToClub(clubId,locationId);
		then: 'result is not null'
			item.clubId == clubId
			item.locationId == locationId
			1 * clubLocationRepository.findAllByClubIdLocationId(clubId, locationId)
			1 * clubLocationRepository.save(_)
		
		where:
			clubId	|	locationId
			1	|	1
			2	|	2
	}
	
	@Unroll
	def "Add location to club when clubId or disciplineId is null for clubId=#clubId and locationId=#locationId" ()
	{
		given:
			clubRepository.findById(clubId) >> Optional.of(Club.builder()
				.clubId(clubId)
				.name("test")
				.addressEmail("test")
				.deleted(false)
				.domain("test")
				.phoneNumber("test")
				.build())
			locationRepository.findById(locationId) >> Optional.of(Location.builder()
				.locationId(locationId)
				.street("test")
				.houseNumber(1)
				.city("test")
				.postcode("test")
				.description("test")
				.build())
		when: 'put data'
				def item = clubLocationService.addLocationToClub(clubId,locationId);
		then: 'thrown exception'
			thrown IllegalArgumentException
		where:
			clubId	|	locationId
			1	|	null
			null	|	1
			null	|	null
		
	}
}