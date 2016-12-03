package hu.unideb.inf.rft.nvkshop.validation.product;

import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

public enum ProductValidationViolation implements ValidationViolation {

	NAME_CANNOT_BE_EMPTY, PRICE_MUST_BE_VALID, DESCRIPTION_CANNOT_BE_NULL, CATEGORY_CANNOT_BE_NULL;

}
