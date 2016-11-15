package hu.unideb.inf.rft.nvkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.security.IdentifyableUserBaseEntity;

@Repository
public interface IdentifyableUserBaseEntityDao extends JpaRepository<IdentifyableUserBaseEntity, Long> {

	IdentifyableUserBaseEntity findByUserName(String userName);

	IdentifyableUserBaseEntity findByEmail(String email);
	
}
