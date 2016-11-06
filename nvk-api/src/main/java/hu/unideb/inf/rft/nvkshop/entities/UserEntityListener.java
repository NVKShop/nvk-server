package hu.unideb.inf.rft.nvkshop.entities;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserEntityListener {

	@PreUpdate
	@PrePersist
	private void setBannedIfNull(final User user) {
		user.setBanned(Boolean.FALSE);
	}

}
