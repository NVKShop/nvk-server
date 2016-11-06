package hu.unideb.inf.rft.nvkshop.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(value = { UserEntityListener.class })
public class User extends BaseEntity {

	@Column(name = "username", nullable = false, unique = true)
	private String userName;

	@Column(name = "passwd", nullable = false)
	private String password;

	@Column(name = "banned")
	private Boolean banned;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "users_roles_sw", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	@Pattern(regexp = "(^$|[0-9]{10})")
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;
}
