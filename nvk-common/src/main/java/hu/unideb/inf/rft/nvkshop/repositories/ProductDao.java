package hu.unideb.inf.rft.nvkshop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

	List<Product> findByCategory(Category category);

	@Query("SELECT p FROM Product p WHERE p.name like %:searchTerm% AND p.category IN :categories AND p.price > :lowerLimit AND p.price < :upperLimit")
	Page<Product> search(@Param("searchTerm") String searchTerm, @Param("categories") List<Category> categories,
			@Param("lowerLimit") Double lowerLimit, @Param("upperLimit") Double upperLimit, Pageable pageable);

}
