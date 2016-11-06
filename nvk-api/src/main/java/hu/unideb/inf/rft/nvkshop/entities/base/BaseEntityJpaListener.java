package hu.unideb.inf.rft.nvkshop.entities.base;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityJpaListener {

	@PrePersist
	public void setCreatedAt(final BaseEntity entity) {
		entity.setDateOfCreation(new Date());
	}

	@PreUpdate
	public void setUpdated(final BaseEntity entity) {
		entity.setDateOfModification(new Date());
	}

}
