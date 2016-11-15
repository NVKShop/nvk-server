package hu.unideb.inf.rft.nvkshop.entities.product;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
@DiscriminatorValue(value = "MAIN_CATEGORY")
public class ProductCategory extends AbstractProductCategory {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", insertable = false, updatable = false)
	private AbstractProductCategory parent;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@OrderColumn
	private List<AbstractProductCategory> children;

	public AbstractProductCategory getParent() {
		return parent;
	}

	public void setParent(AbstractProductCategory parent) {
		this.parent = parent;
	}

	public List<AbstractProductCategory> getChildren() {
		return children;
	}

	public void setChildren(List<AbstractProductCategory> children) {
		this.children = children;
	}

}
