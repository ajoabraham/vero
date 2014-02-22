/**
 * 
 */
package com.vero.ui.constants;

import com.vero.db.AbstractDB;
import com.vero.db.MSSQLServerDB;
import com.vero.db.MySQLDB;
import com.vero.db.PostgresDB;
import com.vero.db.TeradataDB;

/**
 * @author Tai Hu
 * 
 */
public enum DBType {
    POSTGRE_SQL {
        @Override
        public String getVendorName() {
            return "PostgreSQL";
        }

        @Override
        public AbstractDB getMetadataDBConnection() {
            return new PostgresDB();
        }

	@Override
        public String getDriver() {
	    return "org.postgresql.Driver";
        }

	@Override
        public String getDBUrl() {
	    return "jdbc:postgresql://%s:%d/%s?user=%s&password=%s";
        }

	@Override
        public int getDefaultPort() {
	    return 5432;
        }
    },
    SQL_SERVER {
        @Override
        public String getVendorName() {
            return "Microsoft SQL Server";
        }

        @Override
        public AbstractDB getMetadataDBConnection() {
            return new MSSQLServerDB();
        }

	@Override
        public String getDriver() {
	    return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }

	@Override
        public String getDBUrl() {
	    return "jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s";
        }

	@Override
        public int getDefaultPort() {
	    return 1433;
        }
    },
    TERADATA {
        @Override
        public String getVendorName() {
            return "Teradata";
        }

        @Override
        public AbstractDB getMetadataDBConnection() {
            return new TeradataDB();
        }

	@Override
        public String getDriver() {
	    return "com.teradata.jdbc.TeraDriver";
        }

	@Override
        public String getDBUrl() {
	    return "jdbc:teradata://%s/DBS_PORT=%d,DATABASE=%s,USER=%s,PASSWORD=%s";
        }

	@Override
        public int getDefaultPort() {
	    return 1025;
        }
    },
    MY_SQL {
        @Override
        public String getVendorName() {
            return "MySQL";
        }

        @Override
        public AbstractDB getMetadataDBConnection() {
            return new MySQLDB();
        }

	@Override
        public String getDriver() {
	    return "com.mysql.jdbc.Driver";
        }

	@Override
        public String getDBUrl() {
	    return "jdbc:mysql://%s:%d/%s?user=%s&password=%s";
        }

	@Override
        public int getDefaultPort() {
	    return 3306;
        }
    };

    public abstract String getVendorName();

    public abstract AbstractDB getMetadataDBConnection();
    
    public abstract String getDriver();
    
    public abstract String getDBUrl();
    
    public abstract int getDefaultPort();
}
