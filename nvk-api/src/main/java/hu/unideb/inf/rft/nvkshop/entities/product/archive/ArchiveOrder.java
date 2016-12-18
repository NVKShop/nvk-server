package hu.unideb.inf.rft.nvkshop.entities.product.archive;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;
import hu.unideb.inf.rft.nvkshop.entities.security.User;

@Entity
@Table(name = "archive_orders")
public class ArchiveOrder extends BaseEntity {

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ArchiveItem> items;

	@ManyToOne(cascade = {CascadeType.MERGE },fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private ArchiveAddress archiveAddress;

	public List<ArchiveItem> getItems() {
		return items;
	}

	public void setItems(List<ArchiveItem> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// public ArchiveOrder(List<ArchiveItem> items, User user) {
	// super();
	// this.items = items;
	// this.user = user;
	// }

	public ArchiveOrder() {
		super();
	}

	public ArchiveAddress getArchiveAddress() {
		return archiveAddress;
	}

	public void setArchiveAddress(ArchiveAddress archiveAddress) {
		this.archiveAddress = archiveAddress;
	}

}
