package hu.unideb.inf.rft.nvkshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.entities.security.UserPasswordRecovery;

@Repository
public interface UserPasswordRecoveryDao extends JpaRepository<UserPasswordRecovery, Long> {

	UserPasswordRecovery findByActivationCode(String activationCode);

	UserPasswordRecovery findByUser(User user);

	List<UserPasswordRecovery> findAllByUser(User user);

}
