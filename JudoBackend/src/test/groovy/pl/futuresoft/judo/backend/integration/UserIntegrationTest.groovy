package pl.futuresoft.judo.backend.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.dto.UserDto
import pl.futuresoft.judo.backend.exception.EntityNotFoundException
import pl.futuresoft.judo.backend.exception.LoginIsUsedException
import pl.futuresoft.judo.backend.exception.ObjectNotFoundException
import pl.futuresoft.judo.backend.repository.UserRepository
import pl.futuresoft.judo.backend.service.UserService
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class UserIntegrationTest extends Specification {
	   
	@Autowired
	UserService userService
	
	@Autowired
	UserRepository userRepository
	
	def "get role by email" () {
		when: 'get Role by id'
			def item = userService.getUserByEmail("judofrontend@gmail.com")
		then: 'result is not null'
			item!=null
		and: 'item contains a proper email'
			item.email=="judofrontend@gmail.com"
		and: 'item contains a proper role'
			item.role.name=="Administrator"
	}
	
	def "get role by email when not exist " () {
		when: 'get Unit by id'
			def item = userService.getUserByEmail("xxx")
		then: 'item is null'
			item==null
	}

	def "userService register not existing integration test" ()
	{
		given:
		def oldCount = userRepository.count();
		def dto = new UserDto (
				email: "alfa@makplus.pl",
				password: "abcde"
		)
		when: 'register user'
			def item = userService.register(dto)
		then: 'item is not null'
			item!=null
		and: 'item contains id'
			item.userId!=null
		and: 'repository contains 2 users'
			userRepository.count()==oldCount+1
	}

	def "userService register existing integration test" ()
	{
		given:
		def dto = new UserDto (
				email: "test@test.pl",
				password: "abcde"
		)
		when: 'register user'
			def item = userService.register(dto)
		then: 'throws exception'
			thrown LoginIsUsedException
	}

	def "userService get not existing user integration test"()
	{
		when: 'get user by id'
			def item =userService.getUser(1,101)
		then: 'throws exception'
			thrown ObjectNotFoundException
	}

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "add admin by backoffice" () {
		given:
			def mydate = new Date(1984,03,31)
		    def oldCount = userRepository.count();
		    def dto = new UserDto (
				email: "alfa@makplus.pl",
				password: "abcde",
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener",
				birthdate: mydate
			)
		when: 'add admin'
			def item = userService.addAdmin(dto,1)
		then: 'item is not null'
			item!=null
		and: 'item contains id'
		    item.userId!=null
		and: 'repository contains 2 users'
			userRepository.count()==oldCount+1
	}
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "add admin by backoffice when login is already used" () {
		given:
		    def dto = new UserDto (
				email: "judofrontend@gmail.com",
				password: "abcde"
			)
		when: 'add admin'
			def item = userService.addAdmin(dto,1)
		then: 'throws exception'
			thrown LoginIsUsedException
	}

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "soft delete admin by backoffice" () {
		when: 'delete'
			def item1 = userService.softDeleteAdmin(1);
			def list = userService.adminList(2);
			def	item2 = userRepository.findById(1).get();
		then: 'list count is 1'
			list.size()==1
		and: 'list contains a non deleted administrator'
			list[0].email=="test@test.pl"
		and: 'item2 contains a softdeleted administartor'
			item2.deleted==true
	}
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "soft delete admin by backoffice when userId does not existing" () {
		when: 'delete'
			userService.softDeleteAdmin(10);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "get admin for club" () {
		when: 'get administrator '
			def item = userService.getUser( 2, 1);
		then: 'result is not null'
			item!=null
		and: 'item contains data'
			item.email=="judofrontend@gmail.com"
	}
	
	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "get list of admin" () {
		when: 'get administrator '
			def list = userService.adminList(2);
		then: 'result count is 2'
			list.size()==2
		and: 'items contains a proper administrator'
			list[1].email=="test@test.pl"
		and: 'items contains a proper administrator'
			list[0].email=="judofrontend@gmail.com"
	}

	@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "edit admin by backoffice" () {
			given:
					def editData = new UserDto(
						userId: 1,
						email: "uwaga@test.pl"
						);
			when: 'put editData'
				userService.editAdmin(editData, 2, 1);
				def userDb = userRepository.findById(1)
			then: "edited user in db has changed email"
				userDb.get().email == "uwaga@test.pl"
		}

		@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
		def "edit admin by backoffice when user id does not exist" () {
			given:
				def editData = new UserDto(
						userId: 5,
						email: "uwaga@test.pl",
						)
			when: 'put data'
				userService.editAdmin(editData,5,5);
			then: 'thrown exception'
				thrown EntityNotFoundException
		}
		
		@WithUserDetails(value="judobackend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
		def "edit admin by backoffice when login is already used" () {
			given:
				def editData = new UserDto(
					userId: 3,
					email: "judofrontend@gmail.com"
				)
			when: 'edit admin'
			userService.editAdmin(editData,2,3);
			then: 'throws exception'
				thrown LoginIsUsedException
		}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "add staff by admin" () {
		given:
			def mydate = new Date(1984,03,31)
			def oldCount = userRepository.count();
			def dto = new UserDto (
				email: "beta@makplus.pl",
				password: "abcde",
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener",
				birthdate: mydate
		)
		when: 'add staff'
			def item = userService.addStaffByAdmin(dto,2)
		then: 'item is not null'
			item!=null
		and: 'item contains data'
			item.firstName=="Jan"
		and: 'repository contains 2 users'
			userRepository.count()==oldCount+1
	}
	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "add staff by admin when login is already used" () {
		given:
		def mydate = new Date(1984,03,31)
		def dto = new UserDto (
				email: "judofrontend@gmail.com",
				password: "abcde",
				firstName: "Jan",
				lastName:"Kowalski",
				position: "trener",
				birthdate: mydate
		)
		when: 'add staff'
			def item = userService.addStaffByAdmin(dto,1)
		then: 'throws exception'
			thrown LoginIsUsedException
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "edit staff by admin" () {
		given:
			def data = new UserDto(
				userId: 4,
				email: "uwaga@test.pl"
		);
		when: 'edit staff'
			userService.editStaffByAdmin(data, 1, 4);
			def userDb = userRepository.findById(4)
		then: "edited user in db has changed"
			userDb.get().email== "uwaga@test.pl"
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "edit staff by admin when user id does not exist" () {
		given:
		def data = new UserDto(
				userId: 4,
				email: "uwaga@test.pl"
			);
		when: 'edit staff'
			userService.editStaffByAdmin(data, 1, 10);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "edit staff by admin when login is already used" () {
		given:
		def data = new UserDto(
				userId: 4,
				email: "test@test.pl"
		);
		when: 'edit staff'
			userService.editStaffByAdmin(data,1,4);
		then: 'throws exception'
			thrown LoginIsUsedException
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "soft delete staff by admin" () {
		when: 'delete'
		def item1 = userService.softDeleteStaffByAdmin(4);
		def list = userService.staffList(1);
		def	item2 = userRepository.findById(4).get();
		then: 'list count is 1'
		list.size()==1
		and: 'list contains a non deleted staff'
			list[0].email=="teststaffcoach@test.pl"
		and: 'item2 contains a softdeleted staff'
			item2.email=="teststaff@test.pl"
		and: 'item2 contains a softdeleted staff'
			item2.deleted==true
	}

	@WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName="jwtUserDetailsService")
	def "soft delete staff by admin when userId does not existing" () {
		when: 'delete'
			userService.softDeleteStaffByAdmin(10);
		then: 'thrown exception'
			thrown EntityNotFoundException
	}
}
