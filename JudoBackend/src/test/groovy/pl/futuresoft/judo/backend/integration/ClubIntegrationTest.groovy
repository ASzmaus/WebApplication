package pl.futuresoft.judo.backend.integration

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles
import org.springframework.security.test.context.support.WithUserDetails
import pl.futuresoft.judo.backend.service.ClubService;
import spock.lang.Specification;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import pl.futuresoft.judo.backend.repository.ClubRepository;
import pl.futuresoft.judo.backend.dto.ClubDto;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ClubIntegrationTest extends Specification {
	   
	@Autowired
	ClubService clubService
	
	@Autowired
	ClubRepository clubRepository
	
@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
def "add club" () {
		given:
				def data = new ClubDto(
						clubId: 1,
						name: "Warszawski Club Aikido",
						domain: "pl",
						phoneNumber: "505505505",
						addressEmail: "judofrontend@gmail.com"
				)
		when: 'put data'
			def item = clubService.addClub(data);
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.name=="Warszawski Club Aikido"
	}
	
	def "Edit club" () {
			given:
					def editData = new ClubDto(
						clubId: 1,
						name: "Warszawski Club Aikido",
							domain: "pl",
							phoneNumber: "505505505",
						addressEmail: "judofrontend@gmail.com"
						)
			when: 'put editData'
				def item = clubService.editClub(editData);
			then: 'result is not null'
				item!=null
			and: 'item contains data'
				item.phoneNumber=="505505505"
		}

	
	def "Edit club when clubId does not exist" () {
		given:
				def editData = new ClubDto(
						clubId: 4,
						name: "Akademia Judo w Warszawie",
						domain: "pl",
						phoneNumber: "505505505",
						addressEmail: "asdf@o2.pl"
						);
		when: 'put editData'
			def item = clubService.editClub(editData);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}

	def "get list of club" () {
		when: 'get data '
			def item = clubService.addClubList();
		then: 'result is not null'
			item!=null
		and: 'items contains a proper club'
			item[1].name=="Akademia Judo w Warszawie"
		and: 'items contains a proper club'
			 item[0].name=="Warszawski Club Aikido"
	}

	def "softDelete of club Id " () {
		when: 'softDelete'
			def result = clubService.softDeleteClub(2);
			def list = clubService.addClubList();
			def	item2 = clubRepository.findById(2).get();
		then: 'item1 count is 1'
			list.size()==2
		and: 'list contains a non deleted club'
			list[0].name=="Warszawski Club Aikido"
		and: 'item2 contains a softdeleted club'
			item2.name=="Akademia Judo w Warszawie"
	}
	
	def "softDelete of clubId when clubId does not exist" () {
		when: 'softDelete'
			clubService.softDeleteClub(8);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}
}