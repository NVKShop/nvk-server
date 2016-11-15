package hu.unideb.inf.rft.nvkshop.entities.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

//@Entity
//@DiscriminatorValue(value="LEAF_CATEGORY")
public class LeafProductCategory extends AbstractProductCategory {

//	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=false,mappedBy="category_id")
	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
