package hu.unideb.inf.rft.nvkshop.entities.product.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

@Entity
@Table(name = "archive_address")
public class ArchiveAddress extends BaseEntity {

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "coutry")
	private String country;

	@Column(name = "city")
	private String city;

	@Column(name = "street")
	private String street;

	@Column(name = "primary_address")
	private boolean isPrimary;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "recipient")
	private String recipient;

	@Column(name = "description")
	private String description;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ArchiveAddress(String zipCode, String country, String city, String street, boolean isPrimary,
			String phoneNumber, String recipient, String description) {
		super();
		this.zipCode = zipCode;
		this.country = country;
		this.city = city;
		this.street = street;
		this.isPrimary = isPrimary;
		this.phoneNumber = phoneNumber;
		this.recipient = recipient;
		this.description = description;
	}

	public ArchiveAddress() {
		super();
	}

}
