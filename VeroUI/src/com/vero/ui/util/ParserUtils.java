/**
 * 
 */
package com.vero.ui.util;

import frmw.model.Formula;
import frmw.parser.Parsing;

/**
 * @author Tai Hu
 *
 */
public final class ParserUtils {
    public static final Parsing PARSER = new Parsing();
    /**
     * 
     */
    private ParserUtils() {
    }

    public static Formula parse(String formula) {
        return PARSER.parse(formula);
    }
}
