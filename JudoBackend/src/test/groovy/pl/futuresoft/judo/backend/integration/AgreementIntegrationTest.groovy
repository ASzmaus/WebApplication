package pl.futuresoft.judo.backend.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.command.AgreementCommand
import pl.futuresoft.judo.backend.service.AgreementService

import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class AgreementIntegrationTest extends Specification {
	   
	@Autowired
	AgreementService agreementService

@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
def "save agreement" () {
		given:
		def agreementDateValue = LocalDate.of(2021, 10, 02);
	        def data = new AgreementCommand(
					type: 'MONTHLY',
					userId: 6,
					agreementDate: agreementDateValue
			);
		when: 'put data'
			def item = agreementService.saveAgreement(data);
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.getAgreementId() == 1
		and: 'item contains data'
			item.getUserId() == 6
		and: 'item contains data'
			item.getGrossAmount() == 80.00
		and: 'item contains data'
			item.getDateTo() == LocalDate.now().plusMonths(1)
		and: 'item contains data'
			item.content.toString().substring(0,10) == '[37, 80, 68, 70, 45, 49, 46, 52, 10, 37, -30, -29, -49, -45, 10, 49, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 83, 47, 74, 97, 118, 97, 83, 99, 114, 105, 112, 116, 47, 74, 83, 40, 116, 104, 105, 115, 46, 112, 114, 105, 110, 116, 92, 40, 92, 41, 59, 116, 104, 105, 115, 46, 99, 108, 111, 115, 101, 92, 40, 92, 41, 59, 41, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 53, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 70, 105, 108, 116, 101, 114, 47, 70, 108, 97, 116, 101, 68, 101, 99, 111, 100, 101, 47, 76, 101, 110, 103, 116, 104, 32, 55, 57, 52, 62, 62, 115, 116, 114, 101, 97, 109, 10, 120, -100, -75, 87, 91, 111, -45, 48, 20, 126, -49, -81, 56, -113, 67, -22, -126, 115, 109, -69, -73, 17, 49, -115, 49, -74, -63, 34, 33, -127, 120, 112, 19, -81, 120, 109, -20, 96, 59, -108, -18, -111, 95, -114, -99, -82, 107, -53, 58, -121, 45, -58, -83, -38, -60, -79, -49, 119, -66, 115, 117, 126, 120, 111, 114, 47, 74, 97, -124, 82, -56, 75, -17, 109, -18, 125, -12, 66, 56, 51, -77, 1, 32, -3, 49, -65, -93, 56, -124, -68, -14, 94, -97, 4, 16, 32, -56, 111, -68, -125, 87, -7, -83, 89, -69, 89, -126, -96, -88, -74, 55, 5, 122, -31, 8, 37, 126, 52, 94, -19, 12, 33, 72, -51, 78, -44, 62, 23, 83, -17, 32, -69, -68, -56, 63, 29, 103, 57, 28, 66, 118, 121, -100, -99, 26, -111, 8, -90, 123, -60, 126, -3, -90, -1, -53, 86, -83, -89, 1, 67, 4, -61, 81, -32, -89, 127, 105, -70, -63, 59, -98, 10, 66, 42, 70, 20, 20, -100, 21, -13, -90, 36, 37, 112, -42, 19, 53, 64, 8, -46, -60, 6, -5, 1, 51, 74, 102, 125, 97, -110, 46, 118, 33, 10, -125, -61, 0, -23, 111, 95, 59, 14, 19, 24, 14, -57, 22, 40, -38, -41, 104, -38, 85, -23, 120, 67, 38, 124, -124, -128, 89, 9, -22, 59, -127, -116, 51, 37, 112, -95, -72, 112, -31, -90, -48, 102, -65, 47, -65, -71, -62, 14, 120, 89, 65, -82, -107, 14, 65, -43, 31, 37, 25, -37, 80, 78, 121, 35, 9, 48, -18, 59, 48, -102, 29, -87, 127, -80, 105, -124, -44, -122, -16, 25, 11, -119, 23, 46, -120, -92, -55, -1, -122, 49, 100, 98, 27, -103, 43, 46, 85, -58, 75, -30, -126, -114, 21, 8, 5, -121, 33, 114, -32, -101, -95, -67, -78, 45, 51, 94, -43, -104, 45, -31, -70, -10, -31, -50, -25, 62, 119, 81, -26, -84, -104, 88, 65, 91, 25, 112, 89, 10, 34, -91, 3, -118, -42, -124, 125, -49, -27, -35, 114, -58, 23, 24, 86, -87, 11, 65, -120, 6, -128, 66, 99, 94, 112, 21, 52, -10, -2, 113, -114, -91, 2, -122, -85, -66, 81, 19, -58, 93, 72, 39, 84, -72, -127, -118, -122, 93, 80, 103, -72, -48, 86, -107, 51, -38, 23, 41, -24, -22, -117, 78, -100, 20, -57, 93, 48, 19, -94, 22, -124, 56, 104, -116, 73, 16, -38, 26, 72, 51, -71, 37, -123, 2, 126, -45, 102, 65, 113, -33, 31, -113, -42, -72, -37, -126, 16, -14, 81, 108, 4, 109, -74, -21, 126, 90, 54, -123, -94, 108, 10, -78, -26, 66, 73, -48, -37, 41, -45, -9, 71, 15, 87, 80, -52, -79, -108, 68, 14, -116, 116, -39, -52, 21, 86, 84, 95, 0, 101, -6, 73, 51, -47, -13, -75, 32, 53, 22, -19, -76, 81, -92, -28, 69, 83, 17, -74, 90, 7, 55, 92, -64, 84, -16, -90, -42, -94, 36, -24, 70, -66, 71, -75, 120, 52, -12, -29, 116, 87, -75, 29, -80, 1, 76, 27, 90, -110, 57, 101, 68, -74, 18, 13, -39, 5, 23, -77, -106, -72, 81, -108, -120, 86, -72, -42, 74, 42, -47, -104, 19, -126, -20, -37, -18, -116, 98, 81, 106, 49, 126, -66, 101, 113, -96, 114, -21, 24, -71, 86, -79, 38, -126, 114, 125, 43, 120, -43, 87, -103, -92, 75, 27, 103, 7, -66, 40, -18, -126, 82, 125, -21, 122, -116, 12, -124, -19, 76, -87, -39, -124, 110, -114, -81, 8, -94, 113, -105, 23, 117, -8, 42, -86, 67, 11, -101, 87, 2, -3, 18, -48, 58, 79, 71, -83, -108, 32, 72, -43, -24, -24, 122, -120, -18, 117, -28, 73, -29, 92, -19, -25, -118, -104, -96, 91, -11, -96, -118, 55, -52, -28, -30, 75, 84, 126, -111, 25, -125, 46, 114, 58, -23, -5, 26, 48, -23, 4, -71, 58, -65, 112, -112, 107, -111, -11, 4, -109, 81, -75, 4, 127, -1, 24, 64, -119, 21, 121, -22, -23, -50, -40, 83, 124, -94, 112, -4, -88, 46, -66, 3, 92, 20, -92, 86, 43, -73, 78, -8, 79, 29, 20, -94, 36, -94, 77, 108, -14, -117, 20, -115, 9, -121, 125, -62, -126, -12, 81, 37, -5, 23, -67, -98, 55, 96, 53, -100, -55, -37, 71, 4, 69, -2, 104, -76, 75, -28, -102, 78, 25, 86, -115, 62, 103, -103, 68, -48, -31, 47, 77, 31, -48, -59, 95, -49, -24, 122, 111, 122, -123, -79, -41, -107, -96, -84, -96, 53, -98, -33, -85, -7, -80, 109, -99, 62, -49, 124, -117, -5, 3, 1, 15, -87, -46, 10, 101, 110, 100, 115, 116, 114, 101, 97, 109, 10, 101, 110, 100, 111, 98, 106, 10, 50, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 84, 97, 98, 115, 47, 83, 47, 71, 114, 111, 117, 112, 60, 60, 47, 83, 47, 84, 114, 97, 110, 115, 112, 97, 114, 101, 110, 99, 121, 47, 84, 121, 112, 101, 47, 71, 114, 111, 117, 112, 47, 67, 83, 47, 68, 101, 118, 105, 99, 101, 82, 71, 66, 62, 62, 47, 67, 111, 110, 116, 101, 110, 116, 115, 32, 53, 32, 48, 32, 82, 47, 84, 121, 112, 101, 47, 80, 97, 103, 101, 47, 82, 101, 115, 111, 117, 114, 99, 101, 115, 60, 60, 47, 67, 111, 108, 111, 114, 83, 112, 97, 99, 101, 60, 60, 47, 67, 83, 47, 68, 101, 118, 105, 99, 101, 82, 71, 66, 62, 62, 47, 80, 114, 111, 99, 83, 101, 116, 32, 91, 47, 80, 68, 70, 32, 47, 84, 101, 120, 116, 32, 47, 73, 109, 97, 103, 101, 66, 32, 47, 73, 109, 97, 103, 101, 67, 32, 47, 73, 109, 97, 103, 101, 73, 93, 47, 70, 111, 110, 116, 60, 60, 47, 70, 49, 32, 51, 32, 48, 32, 82, 47, 70, 50, 32, 52, 32, 48, 32, 82, 62, 62, 62, 62, 47, 80, 97, 114, 101, 110, 116, 32, 54, 32, 48, 32, 82, 47, 77, 101, 100, 105, 97, 66, 111, 120, 91, 48, 32, 48, 32, 53, 57, 53, 32, 56, 52, 50, 93, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 55, 32, 48, 32, 111, 98, 106, 10, 91, 50, 32, 48, 32, 82, 47, 88, 89, 90, 32, 48, 32, 56, 53, 50, 32, 48, 93, 10, 101, 110, 100, 111, 98, 106, 10, 51, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 83, 117, 98, 116, 121, 112, 101, 47, 84, 121, 112, 101, 49, 47, 84, 121, 112, 101, 47, 70, 111, 110, 116, 47, 66, 97, 115, 101, 70, 111, 110, 116, 47, 72, 101, 108, 118, 101, 116, 105, 99, 97, 47, 69, 110, 99, 111, 100, 105, 110, 103, 47, 87, 105, 110, 65, 110, 115, 105, 69, 110, 99, 111, 100, 105, 110, 103, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 52, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 83, 117, 98, 116, 121, 112, 101, 47, 84, 121, 112, 101, 49, 47, 84, 121, 112, 101, 47, 70, 111, 110, 116, 47, 66, 97, 115, 101, 70, 111, 110, 116, 47, 72, 101, 108, 118, 101, 116, 105, 99, 97, 45, 66, 111, 108, 100, 47, 69, 110, 99, 111, 100, 105, 110, 103, 47, 87, 105, 110, 65, 110, 115, 105, 69, 110, 99, 111, 100, 105, 110, 103, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 54, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 75, 105, 100, 115, 91, 50, 32, 48, 32, 82, 93, 47, 84, 121, 112, 101, 47, 80, 97, 103, 101, 115, 47, 67, 111, 117, 110, 116, 32, 49, 47, 73, 84, 88, 84, 40, 50, 46, 49, 46, 55, 41, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 56, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 78, 97, 109, 101, 115, 91, 40, 74, 82, 95, 80, 65, 71, 69, 95, 65, 78, 67, 72, 79, 82, 95, 48, 95, 49, 41, 32, 55, 32, 48, 32, 82, 93, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 57, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 78, 97, 109, 101, 115, 91, 40, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 41, 32, 49, 32, 48, 32, 82, 93, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 49, 48, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 68, 101, 115, 116, 115, 32, 56, 32, 48, 32, 82, 47, 74, 97, 118, 97, 83, 99, 114, 105, 112, 116, 32, 57, 32, 48, 32, 82, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 49, 49, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 78, 97, 109, 101, 115, 32, 49, 48, 32, 48, 32, 82, 47, 84, 121, 112, 101, 47, 67, 97, 116, 97, 108, 111, 103, 47, 80, 97, 103, 101, 115, 32, 54, 32, 48, 32, 82, 47, 86, 105, 101, 119, 101, 114, 80, 114, 101, 102, 101, 114, 101, 110, 99, 101, 115, 60, 60, 47, 80, 114, 105, 110, 116, 83, 99, 97, 108, 105, 110, 103, 47, 65, 112, 112, 68, 101, 102, 97, 117, 108, 116, 62, 62, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 49, 50, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 77, 111, 100, 68, 97, 116, 101, 40, 68, 58, 50, 48, 50, 49, 49, 50, 49, 48, 48, 57, 52, 57, 50, 51, 43, 48, 49, 39, 48, 48, 39, 41, 47, 67, 114, 101, 97, 116, 111, 114, 40, 74, 97, 115, 112, 101, 114, 82, 101, 112, 111, 114, 116, 115, 32, 76, 105, 98, 114, 97, 114, 121, 32, 118, 101, 114, 115, 105, 111, 110, 32, 54, 46, 52, 46, 48, 41, 47, 67, 114, 101, 97, 116, 105, 111, 110, 68, 97, 116, 101, 40, 68, 58, 50, 48, 50, 49, 49, 50, 49, 48, 48, 57, 52, 57, 50, 51, 43, 48, 49, 39, 48, 48, 39, 41, 47, 80, 114, 111, 100, 117, 99, 101, 114, 40, 105, 84, 101, 120, 116, 32, 50, 46, 49, 46, 55, 32, 98, 121, 32, 49, 84, 51, 88, 84, 41, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 120, 114, 101, 102, 10, 48, 32, 49, 51, 10, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 32, 54, 53, 53, 51, 53, 32, 102, 32, 10, 48, 48, 48, 48, 48, 48, 48, 48, 49, 53, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 48, 57, 52, 52, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 50, 50, 57, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 51, 49, 55, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 48, 48, 56, 51, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 52, 49, 48, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 49, 57, 52, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 52, 55, 51, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 53, 50, 55, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 53, 55, 57, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 54, 50, 57, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 48, 48, 48, 48, 49, 55, 51, 52, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 116, 114, 97, 105, 108, 101, 114, 10, 60, 60, 47, 73, 110, 102, 111, 32, 49, 50, 32, 48, 32, 82, 47, 73, 68, 32, 91, 60, 52, 98, 55, 98, 52, 49, 48, 99, 101, 52, 48, 55, 97, 53, 49, 50, 53, 54, 52, 50, 102, 101, 101, 57, 97, 48, 48, 99, 102, 52, 98, 49, 62, 60, 51, 53, 52, 102, 98, 55, 102, 98, 55, 57, 56, 54, 99, 53, 56, 50, 53, 56, 53, 55, 101, 57, 50, 53, 48, 101, 51, 48, 101, 52, 101, 53, 62, 93, 47, 82, 111, 111, 116, 32, 49, 49, 32, 48, 32, 82, 47, 83, 105, 122, 101, 32, 49, 51, 62, 62, 10, 115, 116, 97, 114, 116, 120, 114, 101, 102, 10, 49, 57, 48, 50, 10, 37, 37, 69, 79, 70, 10]'.substring(0,10)
	}

}