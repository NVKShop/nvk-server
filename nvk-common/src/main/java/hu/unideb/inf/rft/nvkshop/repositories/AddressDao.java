package hu.unideb.inf.rft.nvkshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.User;

@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

	public List<Address> findAllByUser(User user);

	public Address findByUserAndIsPrimary(User user, boolean isPrimary);

	public List<Address> findAllByUserAndIsPrimary(User user, boolean isPrimary);

}
