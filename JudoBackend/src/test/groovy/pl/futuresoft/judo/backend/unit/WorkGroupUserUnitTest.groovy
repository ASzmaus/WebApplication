package pl.futuresoft.judo.backend.unit

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.entity.User
import pl.futuresoft.judo.backend.entity.WorkGroup
import pl.futuresoft.judo.backend.repository.UserRepository
import pl.futuresoft.judo.backend.repository.WorkGroupRepository
import pl.futuresoft.judo.backend.repository.WorkGroupUserRepository
import pl.futuresoft.judo.backend.service.UserService
import pl.futuresoft.judo.backend.service.WorkGroupUserService
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class WorkGroupUserUnitTest extends Specification{

    WorkGroupUserService workGroupUserService

    WorkGroupUserRepository workGroupUserRepository=Mock()
    WorkGroupRepository workGroupRepository=Mock()
    UserRepository userRepository=Mock()
    UserService userService=Mock()

   def setup(){
             workGroupUserService = new WorkGroupUserService(workGroupRepository,workGroupUserRepository, userRepository,userService )
    }
    @Unroll
        def "Add user to workGroup for workGroupId=#workGroupId and userId=#userId"(){
            given:
                workGroupRepository.findById(workGroupId) >> Optional.of(WorkGroup.builder()
                     .workGroupId(workGroupId)
                     .name("test")
                     .clubId(1)
                     .disciplineId(1)
                     .limitOfPlaces(1)
                     .startingDate(null)
                     .endDate(null)
                     .monthlyCost(80.00)
                     .build())
                userRepository.findById(userId) >> Optional.of(User.builder()
                    .userId(userId)
                    .email("test")
                    .password("test")
                    .roleId("s")
                    .active(true)
                    .clubId(1)
                    .deleted(false)
                    .activationToken("test")
                    .activationTokenExpirationDate(null)
                    .reminderToken("test")
                    .reminderTokenExpirationDate(null)
                    .beneficiaryId(null)
                    .firstName("test")
                    .lastName("test")
                    .position("test")
                    .birthdate(null)
                    .build())
        when:'put data'
                def item=workGroupUserService.addUserToWorkGroup(userId,workGroupId)
        then:'result is not null'
                item.workGroupId == workGroupId
                item.userId == userId
                1 * workGroupUserRepository.findAllByUserIdAndWorkGroupId(userId, workGroupId)
                1 * workGroupUserRepository.save(_)
        where:
                    workGroupId     |       userId
                    1       |       1
        }

    @Unroll
    def "Add user to workGroup when workGroupId or userId is null for workGroupId=#workGroupId or userId=#userId"(){
        given:
        workGroupRepository.findById(workGroupId) >> Optional.of(WorkGroup.builder()
                .workGroupId(workGroupId)
                .name("test")
                .clubId(1)
                .disciplineId(1)
                .limitOfPlaces(1)
                .startingDate(null)
                .endDate(null)
                .monthlyCost(80.00)
                .build())
        userRepository.findById(userId) >> Optional.of(User.builder()
                .userId(userId)
                .email("test")
                .password("test")
                .roleId("s")
                .active(true)
                .clubId(1)
                .deleted(false)
                .activationToken("test")
                .activationTokenExpirationDate(null)
                .reminderToken("test")
                .reminderTokenExpirationDate(null)
                .beneficiaryId(null)
                .firstName("test")
                .lastName("test")
                .position("test")
                .birthdate(null)
                .build())
        when:'put data'
                 def item=workGroupUserService.addUserToWorkGroup(userId,workGroupId)
        then: 'thrown exception'
                 thrown IllegalArgumentException
        where:
        workGroupId     |       userId
        1       |       null
        null        |       3
        null        |       null
    }
}
