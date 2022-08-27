package pl.futuresoft.judo.backend.contract

import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

class TimeSlotContractTest extends Specification {

    @Autowired
    protected MockMvc mvc

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName ="jwtUserDetailsService")
    def"/Add timeSlot with given whichDay"(){
        given:
            Map request=[
                    timeSlotId:3,
                    whichDay: 2021-10-02,
                    lessonStart: "09:00:00",
                    lessonEnd: "10:00:00",
                    whichDayOfWeek: null,
                    validFrom: 2021-10-02,
                    validTo: 2022-06-30,
                    workGroupId: 1,
                    userId: 5,
                    clubLocationOptional: null
            ]
        when:'put data'
            def results= mvc.perform(post('/workGroup/1/timeSlot')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 200 code (OK)'
            results.andExpect(status().isOk())
        and:'response contains workGroupId'
            results.andExpect(jsonPath('$.workGroupId').value(1))
        and:'response contains workGroupId'
            results.andExpect(jsonPath('$.userId').value(5))
        }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName ="jwtUserDetailsService")
    def"/Add timeSlot with given whichDayOfWeek"(){
        given:
        Map request=[
                timeSlotId:2,
                whichDay: null,
                lessonStart: "09:00:00",
                lessonEnd: "10:00:00",
                whichDayOfWeek: 1,
                validFrom: 2021-10-02,
                validTo: 2022-06-30,
                workGroupId: 1,
                userId: 5,
                clubLocationOptional: null
        ]
        when:'put data'
        def results= mvc.perform(post('/workGroup/1/timeSlot')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 200 code (OK)'
             results.andExpect(status().isOk())
        and:'response contains workGroupId'
             results.andExpect(jsonPath('$.workGroupId').value(1))
        and:'response contains workGroupId'
             results.andExpect(jsonPath('$.userId').value(5))
    }

    def"Add timeSlot to workGroup unauthorized"(){
        given:
            Map request=[
                    timeSlotId:3,
                    whichDay: 2021-10-01,
                    lessonStart: "12:13:00",
                    lessonEnd: "13:13:00",
                    whichDayOfWeek: null,
                    validFrom: 2021-10-01,
                    validTo: 2022-06-30,
                    workGroupId: 2,
                    userId: 4,
                    clubLocationOptional: null
            ]
        when:'put data'
        def results= mvc.perform(post('/workGroup/2/timeSlot')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 401 code (unauthorized)'
            results.andExpect(status().isUnauthorized())
    }

   @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "/Add timeSlot when workGroupId not exist"(){
        given:
            Map request=[
                    timeSlotId:3,
                    whichDay: 2021-10-01,
                    lessonStart: "12:13:00",
                    lessonEnd: "13:13:00",
                    whichDayOfWeek: null,
                    validFrom: 2021-10-01,
                    validTo: 2022-06-30,
                    workGroupId: 7,
                    userId: 2,
                    clubLocationOptional: null
            ]
        when:'put data'
            def results= mvc.perform(post('/workGroup/7/timeSlot')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 404 (NotFound)'
            results.andExpect(status().isNotFound())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "/Add timeSlot to workGroup with given whichDayOfWeek when at the same time userId has more then one workGroup"(){
        given:
            Map request=[
                    timeSlotId:4,
                    whichDay: null,
                    lessonStart: "12:13:00",
                    lessonEnd: "13:13:00",
                    whichDayOfWeek: 1,
                    validFrom: 2021-06-01,
                    validTo: 2022-06-30,
                    workGroupId: 2,
                    userId: 4,
                    clubLocationOptional: null
        ]
        when:'put data'
            def results= mvc.perform(post('/workGroup/2/timeSlot')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 409 (Conflict)'
            results.andExpect(status().isConflict())
    }
    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def"/Edit timeSlot with given whichDay"(){
        given:
        Map request=[
                timeSlotId:1,
                whichDay: 2021-10-02,
                lessonStart: "09:00:00",
                lessonEnd: "12:00:00",
                whichDayOfWeek: null,
                validFrom: 2021-10-02,
                validTo: 2022-06-30,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        ]
        when:'put data'
        def results= mvc.perform(put('/workGroup/1/timeSlot/1')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 200 code (OK)'
            results.andExpect(status().isOk())
        and:'response contains workGroupId'
             results.andExpect(jsonPath('$.workGroupId').value(1))
        and:'response contains workGroupId'
             results.andExpect(jsonPath('$.userId').value(4))
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName ="jwtUserDetailsService")
    def"/Edit timeSlot with given whichDayOfWeek"(){
        given:
        Map request=[
                timeSlotId:2,
                whichDay: null,
                lessonStart: "09:00:00",
                lessonEnd: "12:00:00",
                whichDayOfWeek: 1,
                validFrom: 2021-10-02,
                validTo: 2022-06-30,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        ]
        when:'put data'
            def results= mvc.perform(put('/workGroup/1/timeSlot/2')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 200 code (OK)'
            results.andExpect(status().isOk())
        and:'response contains workGroupId'
            results.andExpect(jsonPath('$.workGroupId').value(1))
        and:'response contains workGroupId'
         results.andExpect(jsonPath('$.userId').value(4))
    }

    def"Edit timeSlot to workGroup unauthorized"(){
        given:
        Map request=[
                timeSlotId:2,
                whichDay: null,
                lessonStart: "09:00:00",
                lessonEnd: "12:00:00",
                whichDayOfWeek: 1,
                validFrom: 2021-10-02,
                validTo: 2021-06-30,
                workGroupId: 1,
                userId: 5,
                clubLocationOptional: null
        ]
        when:'put data'
        def results= mvc.perform(put('/workGroup/1/timeSlot/2')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 401 code (unauthorized)'
        results.andExpect(status().isUnauthorized())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "/Edit timeSlot when timeSlotId not exist"(){
        given:
        Map request=[
                timeSlotId:10,
                whichDay: null,
                lessonStart: "09:00:00",
                lessonEnd: "12:00:00",
                whichDayOfWeek: 1,
                validFrom: 2021-10-02,
                validTo: 2021-06-30,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        ]
        when:'put data'
        def results= mvc.perform(put('/workGroup/1/timeSlot/10')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 404 (NotFound)'
        results.andExpect(status().isNotFound())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "/Edit timeSlot with given whichDayOfWeek when at the same time userId has more then one workGroup"(){
        given:
        Map request=[
                timeSlotId:1,
                whichDay: null,
                lessonStart: "12:13:00",
                lessonEnd: "13:13:00",
                whichDayOfWeek: 1,
                validFrom: 2021-06-01,
                validTo: 2022-06-30,
                workGroupId: 1,
                userId: 4,
                clubLocationOptional: null
        ]
        when:'put data'
            def results= mvc.perform(put('/workGroup/1/timeSlot/1')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 409 (Conflict)'
            results.andExpect(status().isConflict())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "/List of timeSlot"(){
        when:'put data'
            def results= mvc.perform(get('/timeSlot'))
        then:'server returns 200 code (Ok)'
            results.andExpect(status().isOk())
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[2].timeSlotId').value(3))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[2].name').value("Red Group"))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[2].description').value("sala gimanstyczna"))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[1].timeSlotId').value(2))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[1].name').value("Red Group"))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[1].description').value("sala gimanstyczna"))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[0].timeSlotId').value(1))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[0].name').value("Red Group"))
        and: 'response contains timeSlot'
            results.andExpect(jsonPath('$[0].description').value("sala gimanstyczna"))
    }
}
