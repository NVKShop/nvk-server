package hu.unideb.inf.rft.nvkshop.entities.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hu.unideb.inf.rft.nvkshop.entities.base.BaseEntity;

//@Entity
//@Table(name = "address")
public class Address extends BaseEntity {

//	@Column(name = "country")
	private String country;

//	@Column(name = "zip_code")
	private String zip;

//	@Column(name = "street")
	private String street;

//	@Column(name = "house_number")
	private String houseNumber;
//
//	@Column(name = "city")
	private String city;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Address(String country, String zip, String street, String houseNumber, String city) {
		super();
		this.country = country;
		this.zip = zip;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
	}

	public Address() {
		super();
	}

}
