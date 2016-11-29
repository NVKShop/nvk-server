package hu.unideb.inf.rft.nvkshop.entities.product;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

// @Entity
// @Table(name = "abstract_category")
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public abstract class AbstractProductCategory extends BaseEntity {

	// @Column(name = "name")
	public String name;

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}

}