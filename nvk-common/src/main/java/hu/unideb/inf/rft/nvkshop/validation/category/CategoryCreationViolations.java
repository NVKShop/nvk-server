package hu.unideb.inf.rft.nvkshop.validation.category;

import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

public enum CategoryCreationViolations implements ValidationViolation {

	NAME_MUST_NOT_BE_EMPTY, CIRCLE, NAME_MUST_NOT_BE_UNIQUE;

}
