package pl.futuresoft.judo.backend.integration

import org.springframework.security.test.context.support.WithUserDetails
import pl.futuresoft.judo.backend.dto.TimeSlotDto
import pl.futuresoft.judo.backend.exception.ThisCoachAtThisTimeHasAlreadyGroup
import pl.futuresoft.judo.backend.exception.AtThisTimeThisGroupHasAlreadyLocation
import pl.futuresoft.judo.backend.repository.TimeSlotRepository
import pl.futuresoft.judo.backend.service.TimeSlotService;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import spock.lang.Specification;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)

class TimeSlotIntegrationTest extends Specification {
    @Autowired
    TimeSlotService timeSlotService;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    def "Add timeSlot with given whichDay"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 02);
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(10, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 1)
        then: 'result is not null'
        result != null
        and: 'result contains data'
        result.workGroupId == 1
        and: 'result contains data'
        result.userId == 4
    }

    def "Add timeSlot with given whichDay when work_group Id does not exist"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 02);
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(10, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 7,
                userId: 2,
                clubLocationOptional: ""
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 7)
        then: 'thrown exception'
        thrown EntityNotFoundException
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Add user to workGroup with given whichDay when at the same time userId has more then one workGroup"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 01);
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 1)
        then: 'thrown exception'
        thrown ThisCoachAtThisTimeHasAlreadyGroup
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Add user with given whichDay when at the same time and the same location has more then one workGroup"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 01);
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 3,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 1)
        then: 'thrown exception'
        thrown AtThisTimeThisGroupHasAlreadyLocation
    }

    def "Add timeSlot with given whichDayOfWeek"() {
        given:
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(10, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 1)
        then: 'result is not null'
        result != null
        and: 'result contains data'
        result.workGroupId == 1
        and: 'result contains data'
        result.userId == 4
    }

    def "Add user with given whichDayOfWeek when work_group Id does not exist"() {
        given:
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(10, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 7,
                userId: 2,
                clubLocationOptional: ""
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 7)
        then: 'thrown exception'
        thrown EntityNotFoundException
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Add user to workGroup with given whichDayOfWeek when at the same time userId has more then one workGroup"() {
        given:
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 1)
        then: 'thrown exception'
        thrown ThisCoachAtThisTimeHasAlreadyGroup
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Add user with given whichDayOfWeek when at the same time and the same location has more then one workGroup"() {
        given:
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 3,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.addTimeSlot(data, 1)
        then: 'thrown exception'
        thrown AtThisTimeThisGroupHasAlreadyLocation
    }

    def "Edit timeSlot with given whichDay"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 02);
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(12, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 5,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 2, 1)
        then: 'result is not null'
        result != null
        and: 'result contains data'
        result.workGroupId == 1
        and: 'result contains data'
        result.userId == 5
    }

    def "Edit timeSlot with given whichDay when user does not exist"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 02);
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(10, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 10,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 3,
                userId: 7,
                clubLocationOptional: ""
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 10, 3)
        then: 'thrown exception'
        thrown EntityNotFoundException
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Edit user to workGroup with given whichDay when at the same time userId has more then one workGroup"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 01);
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 2, 1)
        then: 'thrown exception'
        thrown ThisCoachAtThisTimeHasAlreadyGroup
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Edit user with given whichDay when at the same time and the same location has more then one workGroup"() {
        given:
        def timeSlotWhichDay = LocalDate.of(2021, 10, 01);
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: timeSlotWhichDay,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: null,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 3,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 2, 1)
        then: 'thrown exception'
        thrown AtThisTimeThisGroupHasAlreadyLocation
    }

    def "Edit timeSlot with given whichDayOfWeek"() {
        given:
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(10, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 2, 1)
        then: 'result is not null'
        result != null
        and: 'result contains data'
        result.workGroupId == 1
        and: 'result contains data'
        result.userId == 4
    }

    def "Edit timeSlot with given whichDayOfWeek when user Id does not exist"() {
        given:
        def timeSlotLessonStart = LocalTime.of(9, 00, 01);
        def timeSlotLessonEnd = LocalTime.of(10, 00, 01);
        def timeSlotValidFrom = LocalDate.of(2021, 10, 02);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 10,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 3,
                userId: 7,
                clubLocationOptional: ""
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 10, 3)
        then: 'thrown exception'
        thrown EntityNotFoundException
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Edit user to workGroup with given whichDayOfWeek when at the same time userId has more then one workGroup"() {
        given:
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 2, 1)
        then: 'thrown exception'
        thrown ThisCoachAtThisTimeHasAlreadyGroup
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "Edit user with given whichDayOfWeek when at the same time and the same location has more then one workGroup"() {
        given:
        def timeSlotLessonStart = LocalTime.of(12, 13, 00);
        def timeSlotLessonEnd = LocalTime.of(13, 13, 00);
        def timeSlotValidFrom = LocalDate.of(2021, 06, 01);
        def timeSlotValidTo = LocalDate.of(2022, 06, 30);
        def data = new TimeSlotDto(
                timeSlotId: 2,
                whichDay: null,
                lessonStart: timeSlotLessonStart,
                lessonEnd: timeSlotLessonEnd,
                whichDayOfWeek: 1,
                validFrom: timeSlotValidFrom,
                validTo: timeSlotValidTo,
                workGroupId: 1,
                userId: 3,
                clubLocationOptional: null
        )
        when: 'put data'
        def result = timeSlotService.editTimeSlot(data, 2, 1)
        then: 'thrown exception'
        thrown AtThisTimeThisGroupHasAlreadyLocation
    }

    def "List of timeSlot with given whichDay"() {
        when: 'put data'
        def list = timeSlotService.schemaPrintingCheckIfWhichDayIsHoliday()
        then: 'result count is 3'
        list.size() == 3
         and: 'items contains a proper timeSlot'
        list[2].timeSlotId == 3
        list[2].getFirstName() == "Katarzyna"
        list[2].name == "Red Group"
        list[2].description == "sala gimanstyczna"
        list[2].whichDay == LocalDate.of(2021, 10, 02);
        list[2].lessonStart == LocalTime.of(12, 13, 00);
        list[2].lessonEnd == LocalTime.of(13, 13, 00);
        and: 'items contains a proper timeSlot'
        list[1].timeSlotId == 2
        list[1].getFirstName() == "Katarzyna"
        list[1].name == "Red Group"
        list[1].description == "sala gimanstyczna"
        list[1].whichDayOfWeek == 1;
        list[1].lessonStart == LocalTime.of(12, 13, 00);
        list[1].lessonEnd == LocalTime.of(13, 13, 00);
        and: 'items contains a proper timeSlot'
            list[0].timeSlotId == 1
             list[0].getFirstName() == "Katarzyna"
        list[0].name == "Red Group"
        list[0].description == "sala gimanstyczna"
        list[0].whichDay == LocalDate.of(2021, 10, 01);
        list[0].lessonStart == LocalTime.of(12, 13, 00);
        list[0].lessonEnd == LocalTime.of(13, 13, 00);
    }
}