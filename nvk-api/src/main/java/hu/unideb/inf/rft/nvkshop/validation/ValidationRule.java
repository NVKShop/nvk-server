
package hu.unideb.inf.rft.nvkshop.validation;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

public interface ValidationRule<T> {

	public List<ValidationViolation> validate(T entity);

}
