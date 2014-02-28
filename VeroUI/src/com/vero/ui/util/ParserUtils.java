/**
 * 
 */
package com.vero.ui.util;

import frmw.dialect.Dialect;
import frmw.dialect.GenericSQL;
import frmw.model.Formula;
import frmw.model.Join;
import frmw.parser.Parsing;

/**
 * @author Tai Hu
 *
 */
public final class ParserUtils {
    public static final Parsing PARSER = new Parsing();
    public final static Dialect GENERIC_SQL = new GenericSQL();
    
    /**
     * 
     */
    private ParserUtils() {
    }

    public static Formula parse(String formula) {
        return PARSER.parse(formula);
    }
    
    public static Join parseJoin(String formula) {
	return PARSER.parseJoin(formula);
    }
}
