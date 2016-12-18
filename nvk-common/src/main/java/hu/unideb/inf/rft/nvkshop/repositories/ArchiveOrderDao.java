package hu.unideb.inf.rft.nvkshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.product.archive.ArchiveOrder;
import hu.unideb.inf.rft.nvkshop.entities.security.User;

@Repository
public interface ArchiveOrderDao extends JpaRepository<ArchiveOrder, Long>{
	
	List<ArchiveOrder> findByUser(User u);

}
