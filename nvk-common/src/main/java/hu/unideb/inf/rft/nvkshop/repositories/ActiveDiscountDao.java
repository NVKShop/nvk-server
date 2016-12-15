package hu.unideb.inf.rft.nvkshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.product.ActiveDiscount;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.entities.product.ScheduledDiscount;

@Repository
public interface ActiveDiscountDao extends JpaRepository<ActiveDiscount, Long> {

	List<ActiveDiscount> findByScheduledDiscount(ScheduledDiscount scheduledDiscount);

	@Query("SELECT a.product FROM ActiveDiscount a")
	List<Product> findDiscountedProducts();

}
