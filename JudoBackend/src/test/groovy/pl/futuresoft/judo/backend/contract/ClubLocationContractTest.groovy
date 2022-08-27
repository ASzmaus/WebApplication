package pl.futuresoft.judo.backend.contract

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import groovy.json.JsonBuilder
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.is;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)


class ClubLocationContractTest extends Specification {

	@Autowired
	protected MockMvc mvc
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def"Add location to club"(){
		given:
			Map request= [
				clubId: 1,
				locationId:3
				]
		when:'put location to club'
			def results=mvc.perform(post("/club/1/location/3")
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
				)
		then:'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and:'response contais clubId'
			results.andExpect(jsonPath('$.clubId').value(1))
		and:'response contains locationId'
			results.andExpect(jsonPath('$.locationId').value(3))
	}
	
	def"Add location to club unauthorized "(){
		given:
			Map request= [
				clubId: 1,
				locationId:3
				]
		when:'put location to club'
			def results=mvc.perform(post("/club/1/location/3")
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
				)
		then:'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
		}
		
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def"Add location to club when locationId has been already added "(){
		given:
			Map request= [
				clubId: 1,
				locationId:1
				]
		when:'put location to club'
			def results=mvc.perform(post("/club/1/location/1")
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
				)
		then:'server returns 409 code (Conflict)'
			results.andExpect(status().isConflict())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def"Add location to club when locationId does not exist "(){
		given:
			Map request= [
				clubId: 1,
				locationId:8
				]
		when:'add location to club'
			def results=mvc.perform(post("/club/1/location/8")
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
				)
		then:'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "Delete location from club"(){
		when:'delete location'
			def results = mvc.perform(delete("/club/2/location/2")
			)
		then:'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def"Delete location from club when locationId does not exist"(){
		when:'delete location from club'
			def results=mvc.perform(delete("/club/8/location/8")
			)
		then:'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	
	def"Delete location from club unauthorized"(){
		when:'delete location from club'
			def results=mvc.perform(delete("/club/2/location/2")
			)
		then:'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def"Get list of location from club"(){
		when:'get location list'
			def results=mvc.perform(get("/club/2/location"))
		then:'server returns 200 code (Ok)'
			results.andExpect(status().isOk())
		and:'response contains list of location'
			results.andExpect(jsonPath('$[0].street').value("Lawendowa"))
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def"Get list of location from club when clubId does not exist"(){
			when:'get location list'
				def results=mvc.perform(get("/club/8/location")
				)
			then:'server returns 404 code (NotFound)'
				results.andExpect(status().isNotFound())
	}
	
	def"Get list of location from club unauthorized"(){
		when:'get location list'
			def results=mvc.perform(get("/club/1/location")
			)
		then:'server returns 401 code (Unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
}
