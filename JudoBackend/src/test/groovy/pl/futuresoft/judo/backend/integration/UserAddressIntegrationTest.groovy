package pl.futuresoft.judo.backend.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.dto.UserAddressDto
import pl.futuresoft.judo.backend.exception.EntityNotFoundException
import pl.futuresoft.judo.backend.repository.UserAddressRepository
import pl.futuresoft.judo.backend.service.UserAddressService
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class UserAddressIntegrationTest extends Specification{

	@Autowired
	UserAddressService userAddressService
	
	@Autowired
	UserAddressRepository userAddressRepository

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "add userAddress"() {
		given:
			def data = new UserAddressDto(
					userAddressId: 1,
					street: "Sienna",
					houseNumber: 200,
					city: "Warsaw",
					postcode: "00100",
					userId: 1
			)
		when: 'put data'
			def item = userAddressService.addUserAddress(data);
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.street=="Sienna"
	}

	def "edit userAddress" ()
	{
		given:
		def data = new UserAddressDto(
				userAddressId: 1,
				street: "Wrzosowa",
				houseNumber: 2,
				city: "Warsaw",
				postcode: "01-429",
				userId: 2
		)
		when: 'put data'
			def item = userAddressService.editUserAddress(data);
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.street=="Wrzosowa"
	}

	def "edit userAddress not existing userAddressId" ()
	{
		given:
		def data= new UserAddressDto(
				userAddressId: 3,
				street: "Zlota",
				houseNumber: 200,
				city: "Warsaw",
				postcode: "00-100",
				userId: 1
		);
		when: 'put data'
			def item = userAddressService.editUserAddress(data);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}
	def "list of userAddress" () {
		when:'get userAddress list'
			def results=userAddressService.userAddressList()
		then:'list is not null'
			results!=null
		and:'results contains data'
			results[0].street=="Miodowa"
	}
}
