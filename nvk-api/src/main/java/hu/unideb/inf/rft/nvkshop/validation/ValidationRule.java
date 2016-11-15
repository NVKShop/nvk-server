
package hu.unideb.inf.rft.nvkshop.validation;

import java.util.List;

public interface ValidationRule<T> {

	public List<ValidationViolation> validate(T entity);

}
