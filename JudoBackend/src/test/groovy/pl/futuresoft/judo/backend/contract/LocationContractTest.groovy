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
class LocationContractTest extends Specification{
	
	@Autowired
	protected MockMvc mvc

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Add location" () {
		given:
			Map request = [
				locationId: 4,
				street: "Sienna",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				description: "dom kultury"
		]
		when: 'put data'
			def results = mvc.perform(post('/location')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains data'
			results.andExpect(jsonPath('$.street').value('Sienna'))
	}

	def "/Add location unauthorized" () {
		given:
			Map request = [
				locationId: 4,
				street: "Sienna",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				description: "dom kultury"
		]
		when: 'put data'
			def results = mvc.perform(post('/location')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}

	def "/edit location unauthorized " () {
		given:
			Map request = [
				locationId: 4,
				street: "Zlota",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				description: "dom kultury"
		]
		when: 'put data '
			def results = mvc.perform(put('/location')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit location not existing locationId" () {
		given:
			Map request = [
				locationId: 5,
				street: "Zlota",
				houseNumber: "200",
				city: "Warsaw",
				postcode: "00-100",
				description: "dom kultury"
		]
		when: 'put newDto'
			def results = mvc.perform(put('/location/5')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit location" () {
		given:
		Map request= [
				locationId: 3,
				street: "Wrzosowa",
				houseNumber: 2,
				city: "Warsaw",
				postcode: "01-429",
				description: "sala gimanstyczna"
		]
		when: 'put dto'
			def results = mvc.perform(put('/location/3')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/location list" (){
	  when: 'get location list'
		  def results = mvc.perform(get('/location'))
	  then: 'server returns 200 code (OK)'
		  results.andExpect(status().isOk())
	  and: 'response contains location'
		  results.andExpect(jsonPath('$[2].street').value("Wrzosowa"))
		and: 'response contains location'
		results.andExpect(jsonPath('$[1].street').value("Lawendowa"))
		and: 'response contains location'
		results.andExpect(jsonPath('$[0].street').value("Miodowa"))
  }
  
  def "/location list unauthorized" () {
	  when: 'get location list'
		  def results = mvc.perform(get('/location'))
	  then: 'server returns 401 code (unauthorized)'
		  results.andExpect(status().isUnauthorized())
  }
}
