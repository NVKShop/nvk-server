package hu.unideb.inf.rft.nvkshop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.security.Role;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@Service(value = "customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("No such user found: " + username);
		}

		if (user.getBanned()) {
			throw new UsernameNotFoundException("The user " + username + " is banned");
		}

		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), mapRoles(user.getRoles()));

		return details;
	}

	private List<GrantedAuthority> mapRoles(List<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}
}
