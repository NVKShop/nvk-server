package hu.unideb.inf.rft.nvkshop.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [roleName=" + roleName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		if (roleName == null) {
			if (other.roleName != null) {
				return false;
			}
		} else if (!roleName.equals(other.roleName)) {
			return false;
		}
		return true;
	}

}
