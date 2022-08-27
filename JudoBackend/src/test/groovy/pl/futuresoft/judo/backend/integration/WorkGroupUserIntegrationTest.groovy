package pl.futuresoft.judo.backend.integration

import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles
import pl.futuresoft.judo.backend.exception.IdAlreadyAddedException
import pl.futuresoft.judo.backend.repository.WorkGroupUserRepository
import pl.futuresoft.judo.backend.service.WorkGroupUserService
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)

class WorkGroupUserIntegrationTest extends Specification{
    @Autowired
    WorkGroupUserService workGroupUserService;

    @Autowired
    WorkGroupUserRepository workGroupUserRepository;

    def "Add user to work group"(){
        when:'put data'
            def result= workGroupUserService.addUserToWorkGroup(4,1);
        then:'result is not null'
            result!=null
        and:'workGroupUserDto contains userId'
            result.userId==4
        and:'workGroupUserDto contains workGroupId'
            result.workGroupId==1
    }

    def"Add user to work_group when user or work_group Id does not exist"(){
        when:'put data'
            def result = workGroupUserService.addUserToWorkGroup(10,1);
        then:'thrown exception'
            thrown EntityNotFoundException
     }

    def "Add user to workGroup when userId has been already added" () {
        when: 'put data'
            def result = workGroupUserService.addUserToWorkGroup(1,2);
        then: 'thrown exception'
            thrown IdAlreadyAddedException
    }

    def"Delete user from workGroup"(){
        given:
            def oldCount=workGroupUserRepository.count();
        when:'delete data'
            def result=workGroupUserService.deleteUserFromWorkGroup(2,7);
            def list= workGroupUserRepository.findAllByWorkGroupId(2);
        then:'repository contains -1 object'
            list.size()==2
            workGroupUserRepository.count()==oldCount-1;
        and:'result contains data'
            list[1].getUserId()==6
    }

    def"Delete user from workGroup when  userId does not exist "(){
        when:'delete data'
            def result=workGroupUserService.deleteUserFromWorkGroup(2,5);
        then:'thrown Exception'
            thrown EntityNotFoundException;
    }

    def"get list of users by workGroupId"(){
        when:'get data'
            def result = workGroupUserService.userListByWorkGroup(2);
        then:'result is not null'
            result!=null
        and:'result contains a proper user'
             result[0].position=="system administrator";
        and:'result contains a proper user'
            result[1].position=="parent";
    }

    def "get list of users by workGroupId when workGroupId does not exist" () {
        when: 'get'
            def list = workGroupUserService.userListByWorkGroup(8);
        then: 'thrown exception'
            thrown EntityNotFoundException
    }
}
