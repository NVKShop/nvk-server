package hu.unideb.inf.rft.nvkshop.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import hu.unideb.inf.rft.nvkshop.entities.security.User;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

	/** UUID */
	private static final long serialVersionUID = -8132232611664701152L;
	private User user;

	public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPassword(), authorities);
		this.user = user;
	}

	public CustomUserDetails(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
