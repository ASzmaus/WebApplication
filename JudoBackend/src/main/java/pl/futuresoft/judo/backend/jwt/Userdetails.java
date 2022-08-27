package pl.futuresoft.judo.backend.jwt;

import org.springframework.security.core.GrantedAuthority;
import pl.futuresoft.judo.backend.dto.UserDto;

import java.io.Serializable;
import java.util.Collection;

public class Userdetails extends org.springframework.security.core.userdetails.User implements Serializable {

	public Userdetails(UserDto user, Collection<? extends GrantedAuthority> authorities)
	{
		super(user.getEmail(), user.getPassword(), authorities);
		this.setUser(user);

	}

	private UserDto user;

	@Override
	public String getPassword() {
		return getUser().getPassword();
	}

	@Override
	public String getUsername() {
		return getUser().getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return getUser().isActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return getUser().isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return getUser().isActive();
		}

	@Override
	public boolean isEnabled() {
		return getUser().isActive();
}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
