package hu.unideb.inf.rft.nvkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.security.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.userName.userName = :username")
	public User findByUserName(String username);

	public User findById(Long id);

}
