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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class WorkGroupContractTest extends Specification {
	
  @Autowired
  protected MockMvc mvc

	@WithUserDetails( value= "judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
  def "/Add group to club when club does not have group" () {
	  given:
	  Map request=[
		  workGroupId:4,
		  name:"BlueGroup",
		  disciplineId: 1,
		  clubId: 1,
		  limitOfPlaces: 30,
		  startingDate:"2021-03-31",
		  endDate:null,
		  locationDto:
				  [ locationId: 1,
					street: "Miodowa",
					houseNumber: 38,
					city: "Warsaw",
					postcode: "02-421",
					description: "sala gimanstyczna"
				  ],
		  monthlyCost: 80.00,
		  bankAccountNumber: "PL27 4444 2004 0000 0000 3002 0137"
		  ]
	  when: 'put workGroup'
		  def results=mvc.perform(post('/club/1/discipline/1/workGroup')
		  .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		  )
	  then: 'server returns 200 code (OK)'
		  results.andExpect(status().isOk())
	  and: 'response contains data'
		  results.andExpect(jsonPath('$.name').value('BlueGroup'))
  
  }
  @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
  def "/Add group to club when club already has added this group"(){
	  given:
		  Map request= [
			  workGroupId:1,
			  name:"Red Group",
			  disciplineId:1,
			  clubId: 1,
			  limitOfPlaces: 30,
			  startingDate:"2021-03-31",
			  endDate:null,
			  locationDto:
					  [ locationId: 1,
						street: "Miodowa",
						houseNumber: 38,
						city: "Warsaw",
						postcode: "02-421",
						description: "sala gimanstyczna"
					  ],
			  monthlyCost: 80.00,
			  bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0138"
			  ]
	  when:'put workGroup'
		  def results=mvc.perform(post('/club/1/discipline/1/workGroup')
		  .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		  )
	  then:'server returns 409 code (Conflict)'
		 results.andExpect(status().isConflict())
  }
  
  @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
  def "/Add group to club when disciplineId does not exist"(){
	  given:
		  Map request= [
			  workGroupId:3,
			  name:"Blue Group",
			  disciplineId:4,
			  clubId: 1,
			  limitOfPlaces: 30,
			  startingDate:"2021-03-31",
			  endDate:null,
			  locationDto:
					  [ locationId: 1,
						street: "Miodowa",
						houseNumber: 38,
						city: "Warsaw",
						postcode: "02-421",
						description: "sala gimanstyczna"
					  ],
			  monthlyCost: 80.00,
			  bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0139"
			  ]
	  when:'put workGroup'
		  def results=mvc.perform(post('/club/1/discipline/4/workGroup')
		  .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		  )
	  then:'server returns 404 code (NotFound)'
		 results.andExpect(status().isNotFound())
  }

  def "/Add group to club unauthorized"(){
	  given:
		  Map request=[
			  workGroupId:2,
			  name:"Blue Group",
			  disciplineId:1,
			  clubId: 1,
			  limitOfPlaces: 30,
			  startingDate:"2021-03-31",
			  endDate:null,
			  locationDto:
					  [ locationId: 1,
						street: "Miodowa",
						houseNumber: 38,
						city: "Warsaw",
						postcode: "02-421",
						description: "sala gimanstyczna"
					  ],
			  monthlyCost: 80.00,
			  bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0139"
		  ]
	  when:'put workGroup'
		  def results=mvc.perform(post('/club/1/discipline/1/workGroup')
		  .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		  )
	  then:'server returns 401 code (unauthorized)'
		  results.andExpect(status().isUnauthorized())
  }
  
  @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
  def "/edit group" () {
	  given:
		  Map request=[
			  workGroupId:1,
			  name:"Green Group",
			  disciplineId:1,
			  clubId: 1,
			  limitOfPlaces: 30,
			  startingDate:"2021-03-31",
			  endDate:null,
			  locationDto:
					  [ locationId: 1,
						street: "Miodowa",
						houseNumber: 38,
						city: "Warsaw",
						postcode: "02-421",
						description: "sala gimanstyczna"
					  ],
			  monthlyCost: 80.00,
			  bankAccountNumber: "PL27 6666 2004 0000 0000 3002 0139"
			  ]
	  when: 'put dto'
		  def results = mvc.perform(put('/club/1/discipline/1/workGroup/1')
				  .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		  )
	  then: 'server returns 204 code (NoContent)'
	  		results.andExpect(status().isNoContent())
  }
  
  def "/edit group unauthorized"(){
	  given:
		  Map request=[
			  workGroupId:1,
			  name:"Green Group",
			  disciplineId:1,
			  clubId: 1,
			  limitOfPlaces: 30,
			  startingDate:"2021-03-31",
			  endDate:null,
			  locationDto:
					  [ locationId: 1,
						street: "Miodowa",
						houseNumber: 38,
						city: "Warsaw",
						postcode: "02-421",
						description: "sala gimanstyczna"
					  ],
			  monthlyCost: 80.00,
			  bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0139"
			  ]
	  when:'put workGroup'
		  def results=mvc.perform(put('/club/1/discipline/1/workGroup/1')
			  .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			  )
	  then:'server returns 401 code (unauthorized)'
		  results.andExpect(status().isUnauthorized())
	  }
	  
	  @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	  def "/edit group not existing groupId"(){
		  given:
			  Map request=[
				  workGroupId:4,
				  name:"Green Group",
				  disciplineId:1,
				  clubId: 1,
				  limitOfPlaces: 30,
				  startingDate:"2021-03-31",
				  endDate:null,
				  locationDto:
						  [ locationId: 1,
							street: "Miodowa",
							houseNumber: 38,
							city: "Warsaw",
							postcode: "02-421",
							description: "sala gimanstyczna"
						  ],
				  monthlyCost: 80.00,
				  bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0139"
				  ]
		  when:'put workGroup'
			  def results=mvc.perform(put('/club/1/discipline/1/workGroup/4')
				  .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
			   )
		  then:'server returns 404 code (NotFound)'
			  results.andExpect(status().isNotFound())
	  }
  

  @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
  def "/group list" () {
	  when:'get workGroupList'
		  def results = mvc.perform(get('/club/2/workGroup'))
	  then: 'server returns 200 code (OK)'
		  results.andExpect(status().isOk())
	  and: 'response contains discipline'
		  results.andExpect(jsonPath('$[1].name').value("Black Group"))
	  and: 'response contains discipline'
		  results.andExpect(jsonPath('$[0].name').value("Red Group"))
  }
  
  def "/group list unauthorized" () {
	  when:'get workGroupList'
		  def results = mvc.perform(get('/club/1/workGroup'))
	  then: 'server returns 401 code (unauthorized)'
		  results.andExpect(status().isUnauthorized())
  }
}