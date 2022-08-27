package pl.futuresoft.judo.backend.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.futuresoft.judo.backend.dto.UserDto;
import pl.futuresoft.judo.backend.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service(value = "jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto user = userService.getUserByEmail(username);

		if (user!=null) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			{
				SimpleGrantedAuthority ga = new SimpleGrantedAuthority("LOGGED");
				authorities.add(ga);
			}
			{
				SimpleGrantedAuthority ga = new SimpleGrantedAuthority("ROLE_"+user.getRole().getRoleId());
				authorities.add(ga);
			}
			return new Userdetails(user,
					authorities);
		} else {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}
	}
}