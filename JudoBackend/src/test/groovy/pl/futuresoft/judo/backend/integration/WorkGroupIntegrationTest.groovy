package pl.futuresoft.judo.backend.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.dto.WorkGroupDto
import pl.futuresoft.judo.backend.exception.DisciplineNotFoundInClubException
import pl.futuresoft.judo.backend.exception.EntityNotFoundException
import pl.futuresoft.judo.backend.exception.IdAlreadyAddedException
import pl.futuresoft.judo.backend.repository.*
import pl.futuresoft.judo.backend.service.LocationService
import pl.futuresoft.judo.backend.service.SubscriptionService
import pl.futuresoft.judo.backend.service.WorkGroupService
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class WorkGroupIntegrationTest extends Specification {
	 
	WorkGroupService workGroupService;
	
	@Autowired
	WorkGroupRepository workGroupRepository
	
	@Autowired
	ClubRepository clubRepository
	
	@Autowired
    DisciplineClubRepository disciplineClubRepository

	@Autowired
	ClubLocationRepository clubLocationRepository

	@Autowired
	LocationRepository locationRepository

	@Autowired
	LocationService locationService

	SubscriptionService mockSubscriptionService = Mock();
	
	def setup(){
		workGroupService= new  WorkGroupService(workGroupRepository, clubLocationRepository, clubRepository, disciplineClubRepository, mockSubscriptionService, locationRepository, locationService)
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "Add group to club when club has two others groups and subscription is paid" (){
		given:
				mockSubscriptionService.ifClubHasPaidSubscription(2) >> true
				def mydate = new Date(2021,03,31)
				def data = new WorkGroupDto(
					workGroupId:3,
					name: "BlueGroup",
					disciplineId:2,
					clubId:2,
					limitOfPlaces: 30,
					startingDate:mydate,
					endDate:null,
					locationDto:
						[ 	locationId: 2,
							street: "Lawendowa",
							houseNumber: 3,
							city: "Warsaw",
							postcode: "08-423",
							description: "sala gimanstyczna"
						],
						monthlyCost: 80.00,
						bankAccountNumber: "PL27 3333 2004 0000 0000 3002 0138"
				)
		when: 'add group'
			def item = workGroupService.addWorkGroupToClubToDiscipline(data,2,2)
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.name=="BlueGroup"
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "Add group to club when club does not have groups" (){
		given:
				mockSubscriptionService.ifClubHasPaidSubscription(2) >> true
				def mydate = new Date(2021,03,31)
				def data = new WorkGroupDto(
					workGroupId:4,
					name: "WhiteGroup",
					disciplineId:1,
					clubId:1,
					limitOfPlaces: 30,
					startingDate:mydate,
					endDate:null,
					locationDto:
						[ 	locationId: 1,
							street: "Miodowa",
							houseNumber: 38,
							city: "Warsaw",
							postcode: "02-421",
							description: "sala gimanstyczna"
						],
					monthlyCost: 80.00,
					bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0135"
				)
		when: 'add group'
			def item = workGroupService.addWorkGroupToClubToDiscipline(data,1,1)
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.name=="WhiteGroup"
	}

	@WithUserDetails(value="judofrontend@gmail.com",userDetailsServiceBeanName="jwtUserDetailsService")
	def "Add group to club when disciplineId 3 is not signed to clubId 1"(){
		given:	
				def mydate = new Date(2021,03,31)
				def data = new WorkGroupDto(
				workGroupId:3,
				name:"BlueGroup",
				disciplineId: 4,
				clubId:1,
				limitOfPlaces: 30,
				startingDate:mydate,
				endDate:null,
				locationDto:
						[ 	locationId: 1,
							street: "Miodowa",
							houseNumber: 38,
							city: "Warsaw",
							postcode: "02-421",
							description: "sala gimanstyczna"
						],
				monthlyCost: 80.00,
				bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0133"
				)
		when: 'put data'
			def item =workGroupService.addWorkGroupToClubToDiscipline(data,4,1)
		then: 'thrown exception'
			thrown DisciplineNotFoundInClubException
	}
	
	@WithUserDetails(value="judofrontend@gmail.com",userDetailsServiceBeanName="jwtUserDetailsService")
	def "Add group to club to discipline when group has been already added"(){
		given:
				def mydate = new Date(2021,03,31)
				def data = new WorkGroupDto(
				workGroupId:1,
				name:"Red Group",
				disciplineId: 1,
				clubId:1,
				limitOfPlaces: 30,
				startingDate:mydate,
				endDate:null,
				locationDto:
						[ 	locationId: 1,
						    street: "Miodowa",
				  			houseNumber: 38,
							city: "Warsaw",
							postcode: "02-421",
							description: "sala gimanstyczna"
						],
				monthlyCost: 80.00,
				bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0134"
				)
		when: 'put data'
			def item =workGroupService.addWorkGroupToClubToDiscipline(data,1,1)
		then: 'thrown IdAlreadyAddedException'
			thrown IdAlreadyAddedException
	
	}
	
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "edit group"(){
		given:
			def mydate = new Date(2021,03,31)
			
			def data=new WorkGroupDto(
				workGroupId:1,
				name:"Red Group",
				disciplineId:1,
				clubId:1,
				limitOfPlaces: 30,
				startingDate: mydate,
				endDate:null,
				locationDto:
					[ 	locationId: 1,
						street: "Miodowa",
						houseNumber: 38,
						city: "Warsaw",
						postcode: "02-421",
						description: "sala gimanstyczna"
					],
				monthlyCost: 80.00,
				bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0137"
				)
		when:'put data'
			workGroupService.editWorkGroup(data,1,1,1)
			def workGroupDb = workGroupRepository.findById(1);
		then: "workGroup is present in DB"
			workGroupDb.isPresent()
		and: "edit workGroup in db has proper name"
			workGroupDb.get().name == "Red Group"
	}
	
	@WithUserDetails(value="judofrontend@gmail.com",userDetailsServiceBeanName="jwtUserDetailsService")
	def "edit not existing group"(){
		given:
			def mydate = new Date(2021,03,31)
			def data=new WorkGroupDto(
				workGroupId: 5,
				name:"Black Group",
				disciplineId:1,
				clubId:1,
				limitOfPlaces:30,
				startingDate: mydate,
				endDate:null,
				locationDto:
				[ 	locationId: 1,
					street: "Miodowa",
					houseNumber: 38,
					city: "Warsaw",
					postcode: "02-421",
					description: "sala gimanstyczna"
				],
				monthlyCost: 80.00,
				bankAccountNumber: "PL27 1140 2004 0000 0000 3002 0137"
				)
		when:'put data'
			workGroupService.editWorkGroup(data,1,1,5)
		then:'thrown Exception'
			thrown EntityNotFoundException
	}
		
	def "get list of group in given club" () {
		when: 'get list '
			def list = workGroupService.workGroupListByClub(2);
		then: 'list is not null'
			list!=null
		and: 'list contains a proper group'
			 list[0].name=="Black Group"
	}
}