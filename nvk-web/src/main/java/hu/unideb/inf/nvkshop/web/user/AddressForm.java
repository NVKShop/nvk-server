package hu.unideb.inf.nvkshop.web.user;

public class AddressForm {

	private String zipCode;
	private String country;
	private String city;
	private String street;
	// private boolean isPrimary;
	private String phoneNumber;
	private String recipient;
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

	// public boolean isPrimary() {
	// return isPrimary;
	// }
	//
	// public void setPrimary(boolean isPrimary) {
	// this.isPrimary = isPrimary;
	// }

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

}
