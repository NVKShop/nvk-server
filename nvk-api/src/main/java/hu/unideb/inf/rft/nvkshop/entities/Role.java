package hu.unideb.inf.rft.nvkshop.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="roles")
@Data
@EqualsAndHashCode(callSuper=false)
public class Role extends BaseEntity {

	private String roleName;

}
