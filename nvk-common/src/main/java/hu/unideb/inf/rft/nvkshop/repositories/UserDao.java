package hu.unideb.inf.rft.nvkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.security.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	public User findByUserName(String userName);

	public User findByEmail(String email);

}
