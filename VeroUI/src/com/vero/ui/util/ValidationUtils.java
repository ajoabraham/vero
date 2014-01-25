/**
 * 
 */
package com.vero.ui.util;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.UIData;
import com.vero.ui.service.MetadataPersistentService;
import com.vero.ui.service.ServiceException;
import com.vero.ui.service.ServiceManager;

/**
 * @author Tai Hu
 *
 */
public final class ValidationUtils {
    private static final Logger logger = Logger.getLogger(ValidationUtils.class.getName());

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
    
    public static boolean isUniqueName(ObjectType type, String name) {
        MetadataPersistentService service = ServiceManager.getMetadataPersistentService();
        boolean isUnique = true;
        
        try {
            switch (type) {
                case DATASOURCE:
                    isUnique = service.isUniqueDatasourceName(name);
                    break;
                default:
                    logger.log(Level.WARNING, "Invalid object type - {0}", type);
            }
        }
        catch (ServiceException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        
        return isUnique;
    }
}
