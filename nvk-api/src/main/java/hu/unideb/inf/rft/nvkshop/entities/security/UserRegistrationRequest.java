package hu.unideb.inf.rft.nvkshop.entities.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "registration_requests")
public class UserRegistrationRequest extends IdentifyableUserBaseEntity {

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "passwd")
	private String password;

	@Transient
	private String passwordConfirmation;

	@Column(name = "activation_code")
	private String activationCode;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public UserRegistrationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRegistrationRequest(String email, String firstName, String lastName, String password,
			String passwordConfirmation) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

}
