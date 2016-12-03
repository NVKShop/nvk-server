package hu.unideb.inf.rft.nvkshop.util;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearch {
	
	private String searchTerm;
	
	private int pageSize;
	
	private int from;
	
	private List<Category> inCategories;
	
	private Double lowerPrice;
	
	private Double upperPrice;
	
	private String sortBy;
	
	private Direction sortDirection;
}
