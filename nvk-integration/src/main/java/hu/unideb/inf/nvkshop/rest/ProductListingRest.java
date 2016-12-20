package hu.unideb.inf.nvkshop.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.nvkshop.rest.vo.OrderDirection;
import hu.unideb.inf.nvkshop.rest.vo.PageablePublicProduct;
import hu.unideb.inf.nvkshop.rest.vo.PublicPicture;
import hu.unideb.inf.nvkshop.rest.vo.PublicProduct;
import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;
import hu.unideb.inf.rft.nvkshop.service.ProductService;
import hu.unideb.inf.rft.nvkshop.service.impl.util.PagingUtils;
import hu.unideb.inf.rft.nvkshop.util.ProductSearch;

@RestController
public class ProductListingRest {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryServcice;

	@RequestMapping(method = RequestMethod.GET, value = "/detailedProduct/{productId}")
	public Product getProductById(@PathVariable Long productId) {
		Product product = productService.findById(productId);
		return product;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/productSearch")
	public PageablePublicProduct searchProduct(
			@RequestParam(value = "searchTerm", defaultValue = "", required = false) String searchTerm,
			@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize,
			@RequestParam("orderBy") String orderBy, @RequestParam("direction") OrderDirection direction,
			@RequestParam(value = "categories", required = false) Long[] categories) throws IOException {

		List<Category> inCategories = null;
		if (ArrayUtils.isEmpty(categories)) {
			inCategories = categoryServcice.findAll();
		} else {
			inCategories = categoryServcice.findByIds(Arrays.asList(categories));
		}
		ProductSearch search = ProductSearch.builder().from(pageNumber).pageSize(pageSize).sortBy(orderBy)
				.sortDirection(Direction.valueOf(direction.toString())).inCategories(inCategories).build();
		Page<Product> products = productService.search(search);

		PageablePublicProduct pageable = new PageablePublicProduct(products.hasPrevious(), products.hasNext(),
				mapToPublicVo(products.getContent()));

		return pageable;
	}

	@RequestMapping(value = "/productsByCategory", method = RequestMethod.GET)
	public PageablePublicProduct searchInCategory(@RequestParam("categoryId") Long categoryId,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) throws IOException {
		Category category = categoryServcice.findById(categoryId);
		ProductSearch search = ProductSearch.builder().inCategories(Arrays.asList(category)).sortBy("name")
				.pageSize(pageSize).sortDirection(Direction.ASC)
				.from(pageNumber).build();

		Page<Product> products = productService.search(search);

		PageablePublicProduct pageable;
		pageable = new PageablePublicProduct(new Boolean(products.hasPrevious()), new Boolean(products.hasNext()),
				mapToPublicVo(products.getContent()));
		return pageable;
	}

	private List<PublicProduct> mapToPublicVo(List<Product> products) throws IOException {
		List<PublicProduct> result = new ArrayList<>();

		for (Product product : products) {
			result.add(mapToPublicVo(product));
		}
		return result;
	}

	private PublicProduct mapToPublicVo(Product product) throws IOException {
		PublicProduct publicProduct = new PublicProduct(product.getId(), product.getName(), product.getDescription(),
				product.getPictureAsByte(), product.getPrice());
		return publicProduct;
	}

	@ExceptionHandler(NullPointerException.class)
	private ResponseEntity<?> nullPointerExceptionHandler() {
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IOException.class)
	private ResponseEntity<?> serverSideExceptionHandler() {
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
