package hu.unideb.inf.rft.nvkshop.validation.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.validation.ValidationRule;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

@Service
public class CategoryValidator{

	protected List<ValidationRule<Category>> rules;

	@Autowired
	@Qualifier("categoryValidation")
	public void setRules(List<ValidationRule<Category>> rules) {
		this.rules = rules;
	}

	public void validate(Category object) throws ValidationException {

		List<ValidationViolation> violations = new ArrayList<>();
		for (ValidationRule<Category> rule : this.rules) {
			violations.addAll(rule.validate(object));
		}

		if (!violations.isEmpty()) {
			throw new ValidationException(violations);
		}
	}
}
