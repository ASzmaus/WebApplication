package pl.futuresoft.judo.backend.contract

import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class UserAddressContractTest extends Specification{
	
	@Autowired
	protected MockMvc mvc

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Add userAddress" () {
		given:
			Map request = [
				userAddressId: 2,
				street: "Sienna",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				userId: 2
		]
		when: 'put data'
			def results = mvc.perform(post('/userAddress')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains data'
			results.andExpect(jsonPath('$.street').value('Sienna'))
	}

	def "/Add userAddress unauthorized" () {
		given:
			Map request = [
					userAddressId: 1 ,
				street: "Sienna",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				uerId: 1
		]
		when: 'put data'
			def results = mvc.perform(post('/userAddress')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}

	def "/edit userAddress unauthorized " () {
		given:
			Map request = [
					userAddressId: 4,
				street: "Zlota",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				userId: 1
		]
		when: 'put data '
			def results = mvc.perform(put('/userAddress')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit userAddress not existing userAddressId" () {
		given:
			Map request = [
					userAddressId: 5,
				street: "Zlota",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				userId: 1
		]
		when: 'put newDto'
			def results = mvc.perform(put('/userAddress/5')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit userAddress" () {
		given:
		Map request= [
				userAddressId: 1,
				street: "Wrzosowa",
				houseNumber: 2,
				city: "Warsaw",
				postcode: "01-429",
				userId: 2
		]
		when: 'put dto'
		def results = mvc.perform(put('/userAddress/3')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
		results.andExpect(status().isOk())
		and: 'response contains data'
		results.andExpect(jsonPath('$.street').value('Wrzosowa'))
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/userAddress list" (){
	  when: 'get userAddress list'
		  def results = mvc.perform(get('/userAddress'))
	  then: 'server returns 200 code (OK)'
		  results.andExpect(status().isOk())
		and: 'response contains serAddress'
			results.andExpect(jsonPath('$[0].street').value("Miodowa"))
  }
  
  def "/userAddress list unauthorized" () {
	  when: 'get userAddress list'
		  def results = mvc.perform(get('/userAddress'))
	  then: 'server returns 401 code (unauthorized)'
		  results.andExpect(status().isUnauthorized())
  }
}
