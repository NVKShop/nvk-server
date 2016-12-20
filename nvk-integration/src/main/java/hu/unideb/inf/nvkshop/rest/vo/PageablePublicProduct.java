package hu.unideb.inf.nvkshop.rest.vo;

import java.util.List;

public class PageablePublicProduct {

	private Boolean previousPageExists;

	private Boolean nextPageExists;

	private Long totalElements;

	private List<PublicProduct> products;

	public PageablePublicProduct(Boolean previousPageExists, Boolean nextPageExists, Long totalElements,
			List<PublicProduct> products) {
		super();
		this.previousPageExists = previousPageExists;
		this.nextPageExists = nextPageExists;
		this.products = products;
		this.totalElements = totalElements;
	}

	public Boolean getPreviousPageExists() {
		return previousPageExists;
	}

	public void setPreviousPageExists(Boolean previousPageExists) {
		this.previousPageExists = previousPageExists;
	}

	public Boolean getNextPageExists() {
		return nextPageExists;
	}

	public void setNextPageExists(Boolean nextPageExists) {
		this.nextPageExists = nextPageExists;
	}

	public List<PublicProduct> getProducts() {
		return products;
	}

	public void setProducts(List<PublicProduct> products) {
		this.products = products;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

}
