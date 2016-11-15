package hu.unideb.inf.rft.nvkshop.validation.userregistration;

import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationViolation;

public enum UserValidationViolations implements ValidationViolation{

	USERNAME_EXISTS, PASSWORD_MISMATCH, EMAIL_NOT_VALID, EMAIL_EXISTS

}
