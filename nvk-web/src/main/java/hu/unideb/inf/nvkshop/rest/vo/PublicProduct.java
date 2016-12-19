package hu.unideb.inf.nvkshop.rest.vo;

public class PublicProduct {

	private Long id;

	private String name;

	private String description;

	private byte[] picture;

	private Double price;

	public PublicProduct(Long id, String name, String description, byte[] picture, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.price = price;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
