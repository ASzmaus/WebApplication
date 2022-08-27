package pl.futuresoft.judo.backend.contract

import org.springframework.beans.factory.annotation.Autowired;
import spock.lang.Specification;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc
import org.springframework.security.test.context.support.WithUserDetails
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class DisciplineControllerTest extends Specification {
	
  @Autowired
  protected MockMvc mvc

  @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
  def "/get list of discipline" () {
	  when: 'get list of discipline'
	  	def results = mvc.perform(get('/discipline'))
	  then: 'server returns 200 code (OK)'
		  results.andExpect(status().isOk())
	  and: 'response contains discipline'
		  results.andExpect(jsonPath('$[2].name').value("karate"))
	  and: 'response contains discipline'
		  results.andExpect(jsonPath('$[1].name').value("judo"))
	   and: 'response contains discipline'
		  results.andExpect(jsonPath('$[0].name').value("aikido"))
  }
  
  def "/get lis of discipline unauthorized" () {
	  when: 'get list of discipline'
		  def results = mvc.perform(get('/discipline'))
	  then: 'server returns 401 code (unauthorized)'
		  results.andExpect(status().isUnauthorized())
  }
}