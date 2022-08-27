package pl.futuresoft.judo.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.futuresoft.judo.backend.jwt.JwtAuthenticationEntryPoint;
import pl.futuresoft.judo.backend.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired

	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired

	private UserDetailsService jwtUserDetailsService;

	@Autowired

	private JwtRequestFilter jwtRequestFilter;

	@Autowired

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bcryptPasswordEncoder);
	}
	
	@Autowired
	private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder bcryptPasswordEncoder;
	

	@Bean

	@Override

	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	@Override

	protected void configure(HttpSecurity httpSecurity) throws Exception {

// We don't need CSRF for this example
		httpSecurity.csrf().disable()			 
    	.authorizeRequests().antMatchers(HttpMethod.POST, "/authenticate").permitAll().and()
   	    .authorizeRequests().antMatchers(HttpMethod.POST, "/register").permitAll().and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/activate").permitAll().and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/remainder").permitAll().and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/remainderEnterPassword").permitAll().and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/page").permitAll().and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/page/*").permitAll().and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/checkLoginAvailable").permitAll().and()
		.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()
				.anyRequest().authenticated().and().
				
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);	
	}
}