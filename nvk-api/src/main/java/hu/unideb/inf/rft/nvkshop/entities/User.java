package hu.unideb.inf.rft.nvkshop.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@EntityListeners(value = { UserEntityListener.class })
public class User extends BaseEntity {

	@Column(name = "username", nullable = false, unique = true)
	private String userName;

	@Column(name = "passwd", nullable = false)
	private String password;

	@Column(name = "banned")
	private Boolean banned;

	@ManyToMany(cascade = { CascadeType.ALL },fetch=FetchType.EAGER)
	@JoinTable(name = "users_roles_sw", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	@Pattern(regexp = "(^$|[0-9]{10})")
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	public User(String userName, String password, Boolean banned, List<Role> roles, String phoneNumber, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.banned = banned;
		this.roles = roles;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((banned == null) ? 0 : banned.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (banned == null) {
			if (other.banned != null) {
				return false;
			}
		} else if (!banned.equals(other.banned)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (phoneNumber == null) {
			if (other.phoneNumber != null) {
				return false;
			}
		} else if (!phoneNumber.equals(other.phoneNumber)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", banned=" + banned + ", roles=" + roles
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}

}
