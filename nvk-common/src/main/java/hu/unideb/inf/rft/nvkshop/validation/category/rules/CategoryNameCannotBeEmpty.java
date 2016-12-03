package hu.unideb.inf.rft.nvkshop.validation.category.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.category.CategoryCreationViolations;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

@Service
@Qualifier("categoryValidation")
public class CategoryNameCannotBeEmpty implements ValidationRule<Category> {

	@Override
	public List<ValidationViolation> validate(Category entity) {
		if (StringUtils.isBlank(entity.getName())) {
			return Arrays.asList(CategoryCreationViolations.NAME_MUST_NOT_BE_EMPTY);
		}else{
			return Collections.emptyList();
		}
	}

}
