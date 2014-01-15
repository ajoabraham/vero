/**
 * 
 */
package com.vero.ui.constants;

/**
 * @author Tai Hu
 *
 */
public enum DBType {
    POSTGRE_SQL {
	@Override
	public String getName() {
	    return "PostgreSQL";
	}
    },
    SQL_SERVER {
	@Override
	public String getName() {
	    return "SQL Server";
	}
    },
    TERADATA {
	@Override
	public String getName() {
	    return "Teradata";
	}
    },
    MY_SQL {
	@Override
	public String getName() {
	    return "MySQL";
	}
    };
    
    public abstract String getName();
}
