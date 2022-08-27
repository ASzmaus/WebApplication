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
class AgreementContractTest extends Specification {

	@Autowired
	protected MockMvc mvc

	@WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
	def "/Add agreement"() {
		given:
		Map request = [
				type  : "MONTHLY",
				userId: 6,
				agreementDate: "2022-01-07"
		]
		when: 'put data'
		def results = mvc.perform(post('/agreement')
				.contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
		)
		then: 'server returns 200 code (OK)'
		    results.andExpect(status().isOk())
		and: 'response contains data'
		    results.andExpect(jsonPath('$.userId').value(6))
	}
}