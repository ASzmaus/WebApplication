package pl.futuresoft.judo.backend.contract;

import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired;
import spock.lang.Specification;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc
import org.springframework.security.test.context.support.WithUserDetails;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class ClubControllerTest extends Specification {
	
	  
	@Autowired
	protected MockMvc mvc
	
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Add club" () {
		given:
			Map request = [
				clubId: 1,
				name: "Krakowski Club",
				domain: "pl",
				phoneNumber: "668668668",
				addressEmail: "judofrontend@gmail.com"
				]
		when: 'put data'
			def results = mvc.perform(post('/club')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains data'
			results.andExpect(jsonPath('$.name').value('Krakowski Club'))
	}
	
	def "/Add club unauthorized" () {
		given:
			Map request = [
				clubId: 1,
				name: "Warszawski Club Aikido",
				domain: "pl",
				phoneNumber: "668668668",
				addressEmail: "judofrontend@gmail.com"
				]
		when: 'put data '
			def results = mvc.perform(put('/add')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Edit not existing club" () {
		given:
			Map request = [
				clubId: 8,
				name: "Akademia Judo w Warszawie",
				domain: "pl",
				phoneNumber: "668668668",
				addressEmail: "asdf@o2.pl"
			]
		when: 'put newDto'
			def results = mvc.perform(put('/edit/8')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
			
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Edit club" () {
		given:
			Map request= [
				clubId: 1,
				name: "Warszawski Club Aikido",
				domain: "pl",
				phoneNumber: "505505505",
				addressEmail: "judofrontend@gmail.com"
				]
		when: 'put dto'
			def results = mvc.perform(
				put('/club/1').contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 200 code (OK)'
			   results.andExpect(status().isOk())
		  and: 'response contains data'
			results.andExpect(jsonPath('$.phoneNumber').value('505505505'))
	}
	
	def "/Edit club unauthorized" () {
		given:
				Map request = [
				clubId: 1,
				name: "Warszawski Club Aikido",
				domain: "pl",
				phoneNumber: "505505505",
				addressEmail: "judofrontend@gmail.com"
				]
		when: 'put editDto'
			def results = mvc.perform(post('/edit')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Get list of club" () {
		when: 'get list'
			def results = mvc.perform(get('/club'))
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains club'
			results.andExpect(jsonPath('$[1].name').value("Akademia Judo w Warszawie"))
		and: 'response contains club'
			results.andExpect(jsonPath('$[0].name').value("Warszawski Club Aikido"))
	}		
	
	def "/Get list of club unauthorized" () {
		when: 'get'
			def results = mvc.perform(get('/list'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/softDelete of club " () {
		when: 'delete'
			def results = mvc.perform(delete('/club/1'))
		then: 'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}
	
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/softDelete of club when clubId not exist" () {
		when: 'delete'
			def results = mvc.perform(delete('/club/8'))
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	
	def "/softDelete of club unauthorized" () {
		when: 'delete'
			def results = mvc.perform(delete('/club/1'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
}
