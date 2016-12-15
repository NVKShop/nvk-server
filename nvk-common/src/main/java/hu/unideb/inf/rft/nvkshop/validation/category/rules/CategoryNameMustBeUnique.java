package hu.unideb.inf.rft.nvkshop.validation.category.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.category.CategoryCreationViolations;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

@Service
@Qualifier("categoryValidation")
public class CategoryNameMustBeUnique implements ValidationRule<Category> {

	@Override
	public List<ValidationViolation> validate(Category entity) {
		if (entity.getParent() != null) {
			List<Category> siblings = entity.getParent().getSubCategories();
			for (Category sibling : siblings) {
				if (sibling.getName().equals(entity.getName()) && sibling != entity) {
					return Arrays.asList(CategoryCreationViolations.NAME_MUST_NOT_BE_UNIQUE);
				}
			}
		}
		return Collections.EMPTY_LIST;
	}

}
