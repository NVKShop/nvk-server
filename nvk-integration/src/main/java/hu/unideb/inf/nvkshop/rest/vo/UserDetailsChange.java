package hu.unideb.inf.nvkshop.rest.vo;

import hu.unideb.inf.rft.nvkshop.entities.security.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsChange {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Language language;

}
