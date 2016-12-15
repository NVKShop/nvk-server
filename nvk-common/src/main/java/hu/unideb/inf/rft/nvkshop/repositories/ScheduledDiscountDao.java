package hu.unideb.inf.rft.nvkshop.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.product.ScheduledDiscount;

@Repository
public interface ScheduledDiscountDao extends JpaRepository<ScheduledDiscount, Long> {

	@Query("SELECT s From ScheduledDiscount s WHERE s.starts< :date AND s.expires > :date")
	public List<ScheduledDiscount> findScheduledDiscountsAtTime(@Param("date") Date date);

}
