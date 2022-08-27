package pl.futuresoft.judo.backend.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import javassist.NotFoundException
import pl.futuresoft.judo.backend.service.ClubLocationService;
import pl.futuresoft.judo.backend.repository.ClubLocationRepository;
import pl.futuresoft.judo.backend.exception.IdAlreadyAddedException;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode= ClassMode.BEFORE_EACH_TEST_METHOD)

class ClubLocationIntegrationTest extends Specification {

	@Autowired
	ClubLocationService clubLocationService;
	
	@Autowired
	ClubLocationRepository clubLocationRepository;
	
	def"Add location to club"(){
		when:'put data'
			def results = clubLocationService.addLocationToClub(1, 3)
		then:'results is not null'
			results!=null
		and:'results contains data'
			results.clubId==1
		and:'results contains data'
			results.locationId==3
		}	
		
	def"Add location to club when location has been already added"(){
		when:'put data'
			def results = clubLocationService.addLocationToClub(1, 1)
		then:'thrown exception'
			thrown IdAlreadyAddedException
	}
	
	def"Add location to club when locationId does not exist"(){
		when:'put data'
			def results = clubLocationService.addLocationToClub(1, 4)
		then:'thrown exception'
			thrown EntityNotFoundException
	}
	
	def"Delete location from club "(){
		given:
			def oldCount = clubLocationRepository.count();
		when:'delete data'
			def results = clubLocationService.deleteLocationFromClub(2, 2)
			def item = clubLocationRepository.findAllByClubIdLocationId(1,1)
		then:'repository contains 1 object'
			clubLocationRepository.count()==oldCount-1
		and:'item contains non deleted clubId'
			item.clubId==1
		and:'results contains data'
			item.locationId==1
	}
	def"Delete location from club when locationId does not exist "(){
		when:'delete data'
			def results = clubLocationService.deleteLocationFromClub(2, 4)
		then:'thrown exceptions'
			thrown EntityNotFoundException
	}
	
	def"Get list of location from club"(){
		when:'get list'
			def results=clubLocationService.LocationListByClub(1)
		then:'results is not null'
			results!=null
		and:'results contains data'
			results[0].street=="Miodowa"
	}
	
	def"Get list of location from club when location clubId does not exist"(){
		when:'get list'
			def results=clubLocationService.LocationListByClub(8)
		then:'thrown exception'
			thrown EntityNotFoundException
	}
}
