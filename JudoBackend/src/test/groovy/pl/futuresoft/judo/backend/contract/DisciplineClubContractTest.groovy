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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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

class DisciplineClubContractTest extends Specification {
	
  @Autowired
  protected MockMvc mvc

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Add discipline to club" () {
		given:
			Map request = [
				clubId: 2,
				disciplineId: 1
				]
		when: 'put disciplineClub'
			def results = mvc.perform(post('/club/2/discipline/1')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains clubId'
			results.andExpect(jsonPath('$.clubId').value(2))
		and: 'response contains disciplineId'
			results.andExpect(jsonPath('$.disciplineId').value(1))
	}
	
	def "/Add discipline to club unauthorized" () {
		given:
			Map request = [
				clubId: 2,
				disciplineId: 2
				]
		when: 'put disciplineClub'
			def results = mvc.perform(post('/club/2/discipline/2')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Add discipline to club when clubId not exist" () {
		given:
			Map request = [
				clubId: 8,
				disciplineId: 3
				]
		when: 'put disciplineClub'
			def results = mvc.perform(post('/club/8/discipline/3')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Add discipline to club when discipline has been already added" () {
		given:
			Map request = [
				clubId: 1,
				disciplineId: 1
				]
		when: 'put disciplineClub'
			def results = mvc.perform(post('/club/1/discipline/1')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 409 code (Conflict)'
			results.andExpect(status().isConflict())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/delete discipline from club" () {
		when: 'delete'
			def results = mvc.perform(delete('/club/2/discipline/2'))
		then: 'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/delete discipline from club when clubId not exist" () {
		when: 'delete'
			def results = mvc.perform(delete('/club/8/discipline/8'))
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	
	def "/delete discipline from club unauthorized" () {
		when: 'delete'
			def results = mvc.perform(delete('/club/2/discipline/2'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Get list of discipline from club" () {
		when: 'get list'
			def results = mvc.perform(get('/club/1/discipline'))
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains discipline club'
			results.andExpect(jsonPath('$[0].name').value("aikido"))
	}
	
	def "/Get list of discipline from club unauthorized" () {
		when: 'get'
			def results = mvc.perform(get('/club/1/discipline'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/Get list of discipline from club  when club not exist" () {
		when: 'get list'
			def results = mvc.perform(get('/club/8/discipline'))
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	
}