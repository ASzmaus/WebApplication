package pl.futuresoft.judo.backend.contract

import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.security.test.context.support.WithUserDetails
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.annotation.DirtiesContext.ClassMode

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc 
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class UserControllerTest extends Specification {

    @Autowired
    protected MockMvc mvc    
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/add admin by backoffice" () {
		given:
			Map request = [
				email:"alfa@makplus.pl",
				password :"alfa!234",
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener",
				birthdate:"1984-03-31"
			]
		when: 'post administrator'
			def results = mvc.perform(post('/club/1/administrator')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains true'
			results.andExpect(jsonPath("userId").isNotEmpty())
	}
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/add admin by backoffice when login is already used" () {
		given:
			Map request = [
				"email":"test@test.pl",
				"password" :"alfa!234"
			]
		when: 'post administrator'
			def results = mvc.perform(post('/club/1/administrator')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 409 code (Conflict)'
			results.andExpect(status().isConflict())
	}
		
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/soft delete of admin by backoffice" () {
		when: 'delete'
			def results = mvc.perform(delete('/administrator/1'))
		then: 'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/soft delete of admin by backoffice " () {
		when: 'delete'
			def results = mvc.perform(delete('/administartor/8'))
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	
	def "/soft delete of admin by backoffice unauthorized" () {
		when: 'delete'
			def results = mvc.perform(delete('/administrator/1'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/soft delete of admin by backoffice forbidden contract test" () {
		when: 'delete'
			def results = mvc.perform(delete('/administrator/1'))
		then: 'server returns 403 code (forbidden)'
			results.andExpect(status().isForbidden())
	}
	
	def "/add admin by backoffice unauthorized" () {
		given:
			Map request = [
				"email":"test@test.pl",
				"password" :"alfa!234"
			]
		when: 'post administrator'
			def results = mvc.perform(post('/club/1/administrator')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="teststaffcoach@test.pl", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/add admin when non-backoffice try to add" () {
		given:
			Map request = [
				"email":"xyz@test.pl",
				"password" :"test1"
			]
		when: 'post administrator'
			def results = mvc.perform(post('/club/2/administrator')
			.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 403 code (forbidden)'
			results.andExpect(status().isForbidden())
	}		

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/get list of admin by backoffice" () {
		when: 'get administrator'
			def results = mvc.perform(get('/club/2/administrator'))
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains administrator'
			results.andExpect(jsonPath('$[1].email').value("test@test.pl"))
		and: 'response contains administrator'
			results.andExpect(jsonPath('$[0].email').value("judofrontend@gmail.com"))
	}

	
	@WithUserDetails(value="test@test.pl", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/get list of admin when not backoffice try to get list" () {
		when: 'get administrator'
			def results = mvc.perform(get('/club/1/administrator'))
		then: 'server returns 403 code (forbidden)'
			results.andExpect(status().isForbidden())
	}
	
	def "/get list of admin by backoffice unauthorized" ()
	{
		when: 'get'
			def results = mvc.perform(get('/club/1/administrator'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit admin by backoffice when userId does not exist" () {
		given:
			Map request = [
				    	userId: 30,
						email: "uwaga@test.pl",
						password: "abc",
						active: true,
						role: [roleId: "a",
							name: "Administrator"]
						]
		when: 'put data'
			def results = mvc.perform(put('/club/1/administrator/30')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
			
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit admin by backoffice" () {
		given:
			Map request= [
				 	 	userId: 1,
						email: "uwaga@test.pl",
						password: "test@test.pl",
						active: true,
						role: [roleId: "a",
							name: "Administrator"]
						]
		when: 'put dto'
			def results = mvc.perform(put('/club/2/administrator/1')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}
	
	def "/edit admin by backoffice unauthorized" () {
		given:
				Map request = [
						userId: 1,
						email: "uwaga@test.pl",
						password: "test@test.pl",
						active: true,
						role: [roleId: "a",
							name: "Administrator"]
				]
		when: 'put administrator'
			def results = mvc.perform(put('/club/1/administrator/1')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit admin when not backoffice try to add" () {
		given:
				Map request = [
						userId: 1,
						email: "uwaga@test.pl",
						password: "test@test.pl",
						active: true,
						role: [roleId: "a",
							name: "Administrator"]
				]
		when: 'put data'
			def results = mvc.perform(put('/club/1/administrator/1')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 403 code (forbidden)'
			results.andExpect(status().isForbidden())
	}
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit admin by backoffice when login is already used" () {
		given:
			Map request = [
					userId: 3,
					email: "judofrontend@gmail.com",
					password: "test@test.pl",
					active: true,
					role: [roleId: "a",
						name: "Administrator"]
					]
		when: 'put data'
			def results = mvc.perform(put('/club/2/administrator/3')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 409 code (Conflict)'
			results.andExpect(status().isConflict())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/add staff by admin" () {
		given:
			Map request = [
				email: "beta@makplus.pl",
				password: "abcde",
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener",
				birthdate:"1984-03-31"
		]
		when: 'post staff'
			def results = mvc.perform(post('/club/1/staff')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains true'
			results.andExpect(jsonPath('$.lastName').value("Kowalski"))
	}
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/add staff by admin when login is already used" () {
		given:
			Map request = [
				email:"test@test.pl",
				password:"alfa!234",
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener",
				birthdate:"1984-03-31"
		]
		when: 'post administrator'
			def results = mvc.perform(post('/club/1/staff')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 409 code (Conflict)'
			results.andExpect(status().isConflict())
	}
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/add staff by admin when not admin try to add" () {
		given:
			Map request = [
				email: "test@test.pl",
				password: "test@test.pl",
				active: true,
				role: [roleId: "s",
					   name: "Staff"],
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener kadry",
				birthdate: "1984-07-31"
		]
		when: 'put data'
			def results = mvc.perform(post('/club/1/staff')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 403 code (forbidden)'
			results.andExpect(status().isForbidden())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit staff by admin" () {
		given:
			Map request= [
				userId: 4,
				email: "uwaga@test.pl",
				password: "test@test.pl",
				active: true,
				role: [roleId: "s",
					   name: "Staff"],
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener kadry",
				birthdate: "1984-07-31"
		]
		when: 'put data'
		def results = mvc.perform(
				put('/club/1/staff/4')
						.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit staff by admin when userId does not exist" () {
		given:
			Map request = [
				userId: 10,
				email: "uwaga@test.pl",
				password: "test@test.pl",
				active: true,
				role: [roleId: "s",
					   name: "Staff"],
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener kadry",
				birthdate: "1984-07-31"
		]
		when: 'put data'
			def results = mvc.perform(put('/club/1/staff/10')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}
	def "/edit staff by admin unauthorized" () {
		given:
			Map request = [
				userId: 4,
				email: "uwaga@test.pl",
				password: "test@test.pl",
				active: true,
				role: [roleId: "s",
					   name: "Staff"],
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener kadry",
				birthdate: "1984-07-31"
		]
		when: 'put staff'
			def results = mvc.perform(put('/club/1/staff/4')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit staff by admin when login is already used" () {
		given:
			Map request = [
				userId: 4,
				email: "test@test.pl",
				password: "test@test.pl",
				active: true,
				role: [roleId: "s",
					   name: "Staff"],
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener kadry",
				birthdate: "1984-07-31"
		]
		when: 'put data'
			def results = mvc.perform(put('/club/1/staff/4')
					.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			)
		then: 'server returns 409 code (Conflict)'
			results.andExpect(status().isConflict())
	}

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/edit staff when not admin try to add" () {
		given:
			Map request = [
				userId: 4,
				email: "test@test.pl",
				password: "test@test.pl",
				active: true,
				role: [roleId: "s",
					   name: "Staff"],
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener kadry",
				birthdate: "1984-07-31"
				]
		when: 'put data'
				def results = mvc.perform(put('/club/1/staff/4')
						.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
				)
		then: 'server returns 403 code (forbidden)'
				results.andExpect(status().isForbidden())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/get list of staff by admin" () {
		when: 'get staff'
			def results = mvc.perform(get('/club/1/staff'))
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains administrator'
			results.andExpect(jsonPath('$[0].email').value("teststaff@test.pl"))
	}

	@WithUserDetails(value="aga.test@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/get list of staff when not institution try to get list" () {
		when: 'get administrator'
		def results = mvc.perform(get('/club/1/staff'))
		then: 'server returns 403 code (forbidden)'
		results.andExpect(status().isForbidden())
	}

	def "/get list of staff by admin unauthorized" () {
		when: 'get'
			def results = mvc.perform(get('/club/1/staff'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/soft delete staff by admin when staff id not found" () {
		when: 'delete'
			def results = mvc.perform(delete('/staff/101'))
		then: 'server returns 404 code (NotFound)'
			results.andExpect(status().isNotFound())
	}

	def "/soft delete of staff by admin unauthorized" () {
		when: 'delete'
			def results = mvc.perform(delete('/staff/4'))
		then: 'server returns 401 code (unauthorized)'
			results.andExpect(status().isUnauthorized())
	}

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/soft delete of staff by admin forbidden contract test" () {
		when: 'delete'
			def results = mvc.perform(delete('/staff/4'))
		then: 'server returns 403 code (forbidden)'
			results.andExpect(status().isForbidden())
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "/soft delete of staff by admin" () {
		when: 'delete'
			def results = mvc.perform(delete('/staff/4'))
		then: 'server returns 204 code (NoContent)'
			results.andExpect(status().isNoContent())
	}

	def "/checkLoginAvailable POST post for not existing user contract test" ()
	{
		given:
		Map request = [
				"login":"alfa@makplus.pl"
		]
		when: 'get'
			def results = mvc.perform(post('/checkLoginAvailable')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains true'
			results.andExpect(jsonPath("[0].available").value("true"))
	}
	def "/checkLoginAvailable POST post for existing user contract test" ()
	{
		given:
		Map request = [
				"login":"test@test.pl"
		]
		when: 'get'
		def results = mvc.perform(post('/checkLoginAvailable')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains true'
			results.andExpect(jsonPath("[0].available").value("false"))
	}

	def "/register POST post for not existing user contract test" ()
	{
		given:
		Map request = [
				"email":"alfa@makplus.pl",
				"password" :"alfa!234"
		]
		when: 'get'
		def results = mvc.perform(post('/register')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
			results.andExpect(status().isOk())
		and: 'response contains true'
			results.andExpect(jsonPath("userId").isNotEmpty())
	}
	def "/register POST post for existing user contract test" ()
	{
		given:
		Map request = [
				"email":"test@test.pl",
				"password" :"test"
		]
		when: 'post'
			def results = mvc.perform(post('/register')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 409 code (Conflict)'
			results.andExpect(status().isConflict())
	}

}
