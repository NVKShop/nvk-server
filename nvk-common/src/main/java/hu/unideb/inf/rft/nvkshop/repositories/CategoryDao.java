package hu.unideb.inf.rft.nvkshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category,Long> {

	List<Category> findByParent(Category category);
	
	List<Category> findByParentIsNull();
}
