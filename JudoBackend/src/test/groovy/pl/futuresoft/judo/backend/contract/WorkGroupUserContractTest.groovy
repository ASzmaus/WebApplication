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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

class WorkGroupUserContractTest extends Specification {

    @Autowired
    protected MockMvc mvc

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName ="jwtUserDetailsService")
    def"/Add user to workGroup"(){
        given:
            Map request=[
                    workGroupId:1,
                    userId:5
            ]
        when:'put data'
            def results= mvc.perform(post('/workGroup/1/user/5')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 200 code (OK)'
            results.andExpect(status().isOk())
        and:'response contains workGroupId'
            results.andExpect(jsonPath('$.workGroupId').value(1))
        and:'response contains userId'
            results.andExpect(jsonPath('$.userId').value(5))
    }

    def"Add user to workGroup unauthorized"(){
        given:
            Map request=[
                    workGroupId:1,
                    userId: 4
            ]
        when:'put data'
            def results=mvc.perform(post('/workGroup/1/user/4')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 401 code (unauthorized)'
            results.andExpect(status().isUnauthorized())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "/Add user to workgroup when workGroupId not exist"(){
        given:
            Map request=[
                    workGroupId: 8,
                    userId:4
            ]
        when:'put data'
            def results= mvc.perform(post('/workGroup/8/user/4')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 404 (NotFound)'
            results.andExpect(status().isNotFound())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def "/Add user to workgroup when userId is already added"(){
        given:
            Map request=[
                workGroupId: 2,
                userId:1
        ]
        when:'put data'
            def results= mvc.perform(post('/workGroup/2/user/1')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 409 (Conflict)'
            results.andExpect(status().isConflict())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def"/Delete user from work group"(){
        given:
            def Map request=[
                 workGroupId: 2,
                    userId: 7
            ]
        when:'delete data'
            def results=mvc.perform(delete('/workGroup/2/user/7')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 204 (NoContent)'
            results.andExpect(status().isNoContent())
    }

    @WithUserDetails(value="judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def"/Delete user from workGroup when userId does not exist"(){
        given:
            def Map request=[
                    workGroupId: 1,
                    userId: 8
            ]
        when:'delete data'
            def results=mvc.perform(delete('/workGroup/1/user/8')
            .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
            )
        then:'server returns 404 (NotFound)'
            results.andExpect(status().isNotFound())
    }

    def"/Delete user from workGroup unauthorized "(){
        given:
            def Map request=[
                workGroupId: 1,
                userId: 1
        ]
        when:'delete data'
            def results=mvc.perform(delete('/workGroup/1/user/1')
                .contentType(MediaType.APPLICATION_JSON).content(new JsonBuilder(request).toPrettyString())
        )
        then:'server returns 401 (Unauthorized)'
            results.andExpect(status().isUnauthorized())
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def"Get user list by specific workGroup"(){
        when:'get data'
            def results=mvc.perform(get('/workGroup/2/user')
            )
        then:'server returns 200 (Ok)'
            results.andExpect(status().isOk())
        and:'response contains first user'
            results.andExpect(jsonPath('$[0].position').value('system administrator'))
        and:'response contains second user'
            results.andExpect(jsonPath('$[1].position').value('parent'))
    }

    def"Get user list by specific workGroup unauthorized"(){
        when:'get data'
            def results=mvc.perform(get('/workGroup/2/user')
            )
        then:'server returns 401 (Unauthorized)'
            results.andExpect(status().isUnauthorized())
    }

    @WithUserDetails(value = "judofrontend@gmail.com", userDetailsServiceBeanName = "jwtUserDetailsService")
    def"Get user list by specific workGroup when workGroupId does nor exist"(){
        when:'get data'
            def results=mvc.perform(get('/workGroup/8/user')
              )
        then:'server returns 404 (NotFound)'
            results.andExpect(status().isNotFound())
    }
}
