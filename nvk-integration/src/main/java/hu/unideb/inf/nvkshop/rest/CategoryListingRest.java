package hu.unideb.inf.nvkshop.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.nvkshop.rest.vo.PublicCategory;
import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.service.CategoryService;

@RestController
public class CategoryListingRest {

	@Autowired
	private CategoryService categoryService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET, value = "/listCategories")
	public ResponseEntity<List<PublicCategory>> listCategories() {
		List<Category> categories = categoryService.findAll();

		List<PublicCategory> result = mapToPublicVo(categories);

		return new ResponseEntity<List<PublicCategory>>(result, HttpStatus.OK);
	}

	private List<PublicCategory> mapToPublicVo(List<Category> categories) {
		List<PublicCategory> result = new ArrayList<>();

		for (Category c : categories) {
			result.add(mapToPublicVo(c));
		}

		return result;
	}

	private PublicCategory mapToPublicVo(Category c) {
		PublicCategory publicCategory = new PublicCategory(c.getId(), c.getName(),
				c.getParent() == null ? null : c.getParent().getId());
		return publicCategory;
	}

}
