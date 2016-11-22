package hu.unideb.inf.rft.nvkshop.entities.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

public class UserPasswordRecovery extends BaseEntity {

	@OneToOne
	private User user;

	@Column(name = "activation_code")
	private String activationCode;

	@Column(name = "due_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

}
