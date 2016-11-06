package hu.unideb.inf.rft.nvkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	public User findByUserName(String username);

}
