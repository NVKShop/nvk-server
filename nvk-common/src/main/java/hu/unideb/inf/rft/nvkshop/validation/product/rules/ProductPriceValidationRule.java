package hu.unideb.inf.rft.nvkshop.validation.product.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;
import hu.unideb.inf.rft.nvkshop.validation.product.ProductValidationViolation;

@Service
@Qualifier("productValidation")
public class ProductPriceValidationRule implements ValidationRule<Product> {

	@Override
	public List<ValidationViolation> validate(Product entity) {
		if (entity.getPrice()<=0D){
			return Arrays.asList(ProductValidationViolation.PRICE_MUST_BE_VALID);
		}else{
			return Collections.emptyList();
		}
	}

}
