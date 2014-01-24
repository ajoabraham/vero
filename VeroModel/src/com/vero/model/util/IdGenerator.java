/**
 * 
 */
package com.vero.model.util;

import java.util.UUID;

/**
 * @author Tai Hu
 *
 */
public final class IdGenerator {

    private IdGenerator() {
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
