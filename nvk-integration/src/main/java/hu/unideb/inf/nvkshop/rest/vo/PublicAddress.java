package hu.unideb.inf.nvkshop.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicAddress {

	private Long id;
	
	private String zipCode;

	private String country;

	private String city;

	private String street;

	private String phoneNumber;

	private String houseNumber;
}
