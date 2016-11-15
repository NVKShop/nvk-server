package hu.unideb.inf.rft.nvkshop.entities.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

@Entity
@Table(name="user_names")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class IdentifyableUserBaseEntity extends BaseEntity {

	@Column(name="user_name",unique=true,nullable=false)
	protected String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
