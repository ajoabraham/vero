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
        public String getName() {
            return "PostgreSQL";
        }

        @Override
        public AbstractDB getDBConnection() {
            return new PostgresDB();
        }
    },
    SQL_SERVER {
        @Override
        public String getName() {
            return "MS SQL Server";
        }

        @Override
        public AbstractDB getDBConnection() {
            return new MSSQLServerDB();
        }
    },
    TERADATA {
        @Override
        public String getName() {
            return "Teradata";
        }

        @Override
        public AbstractDB getDBConnection() {
            return new TeradataDB();
        }
    },
    MY_SQL {
        @Override
        public String getName() {
            return "MySQL";
        }

        @Override
        public AbstractDB getDBConnection() {
            return new MySQLDB();
        }
    };

    public abstract String getName();

    public abstract AbstractDB getDBConnection();
}
