package hu.unideb.inf.rft.nvkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;

@Repository
public interface UserRegistrationRequestDao extends JpaRepository<UserRegistrationRequest, Long> {

	UserRegistrationRequest findByActivationCode(String activationCode); 

	@Query("SELECT u FROM UserRegistrationRequest u WHERE u.userName.userName = :userName")
	UserRegistrationRequest findByUserName(String userName);
	
	UserRegistrationRequest findByEmail(String email);
	
}
