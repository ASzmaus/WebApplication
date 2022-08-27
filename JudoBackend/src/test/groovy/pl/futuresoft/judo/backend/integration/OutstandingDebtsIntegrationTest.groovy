package pl.futuresoft.judo.backend.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.service.MailService
import pl.futuresoft.judo.backend.service.OutstandingDebtsSchedulerService
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class OutstandingDebtsIntegrationTest extends Specification {
	@Autowired
	MailService mailService;
	@Autowired
	OutstandingDebtsSchedulerService outstandingDebtsSchedulerService;

	def " outstanding debts list" () {
		when: 'put data'
			 outstandingDebtsSchedulerService.trackOverduePayment();
		then: 'result count is 2'
			mailService.confSmtpHostAndSendEmail("cc", "aa", "vv");
	}
}