package hu.unideb.inf.rft.nvkshop.validation.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

@Service
public class ProductValidator {

	protected List<ValidationRule<Product>> rules;

	@Autowired
	@Qualifier("productValidation")
	public void setRules(List<ValidationRule<Product>> rules) {
		this.rules = rules;
	}
	
	public void validate(Product object) throws ValidationException {

		List<ValidationViolation> violations = new ArrayList<>();
		for (ValidationRule<Product> rule : this.rules) {
			violations.addAll(rule.validate(object));
		}

		if (!violations.isEmpty()) {
			throw new ValidationException(violations);
		}
	}
	
}
