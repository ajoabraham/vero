/**
 * 
 */
package com.vero.ui.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.vero.ui.model.UIData;

/**
 * @author Tai Hu
 *
 */
public final class ValidationUtils {

    private ValidationUtils() {
    }
    
    private static Validator createValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
    
    public static <T extends UIData> Set<ConstraintViolation<T>> validate(T data, String...properties) {
        Validator validator = createValidator();
        Set<ConstraintViolation<T>> violations = null;
        
        for (String property : properties) {
            violations = validator.validateProperty(data, property);
            if (!violations.isEmpty()) {
                break;
            }
        }
        
        return violations;
    }
    
    public static <T extends UIData> Set<ConstraintViolation<T>> validate(T data) {
        Validator validator = createValidator();
        
        return validator.validate(data);
    }
}
