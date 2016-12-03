package hu.unideb.inf.rft.nvkshop.validation.product.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.product.ProductValidationViolation;

@Service
@Qualifier("productValidation")
public class ProductNameValidationRule implements ValidationRule<Product> {

	@Override
	public List<ValidationViolation> validate(Product entity) {
		if (StringUtils.isBlank(entity.getName())) {
			return Arrays.asList(ProductValidationViolation.NAME_CANNOT_BE_EMPTY);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

}
