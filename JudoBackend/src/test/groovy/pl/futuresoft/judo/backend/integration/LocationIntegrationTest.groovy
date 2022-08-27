package pl.futuresoft.judo.backend.integration

import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import pl.futuresoft.judo.backend.dto.LocationDto;
import pl.futuresoft.judo.backend.service.LocationService;
import pl.futuresoft.judo.backend.repository.LocationRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class LocationIntegrationTest extends Specification{
	@Autowired
	LocationService locationService
	
	@Autowired
	LocationRepository locationRepository

	def "add location" () {
		given:
			def data = new LocationDto(
					locationId: 4,
					street: "Sienna",
					houseNumber: 200,
					city: "Warsaw",
					postcode: "00100",
					description: "dom kultury"
			)
		when: 'put data'
			def item = locationService.addLocation(data);
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.street=="Sienna"
	}

	def "edit location" ()
	{
		given:
		def data = new LocationDto(
				locationId: 3,
				street: "Wrzosowa",
				houseNumber: 2,
				city: "Warsaw",
				postcode: "01-429",
				description: "sala gimanstyczna"
		)
		when: 'put data'
			def item = locationService.editLocation(data);
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.street=="Wrzosowa"
	}

	def "edit location not existing locationId" ()
	{
		given:
		def data= new LocationDto(
				locationId: 5,
				street: "Zlota",
				houseNumber: 200,
				city: "Warsaw",
				postcode: "00-100",
				description: "dom kultury"
		);
		when: 'put data'
			def item = locationService.editLocation(data);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}
	def "list of location" () {
		when:'get location list'
			def results=locationService.locationList()
		then:'list is not null'
			results!=null
		and:'results contains data'
			results[0].street=="Miodowa"
		and:'results contains data'
			results[1].street=="Lawendowa"
		and:'results contains data'
			results[2].street=="Wrzosowa"
	}
}
