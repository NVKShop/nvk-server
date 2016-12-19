package hu.unideb.inf.nvkshop.rest.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicUser {

	private String email;
	private String fistName;
	private String lastName;
	private List<String> roles;
	private String phoneNumber;
	
}
