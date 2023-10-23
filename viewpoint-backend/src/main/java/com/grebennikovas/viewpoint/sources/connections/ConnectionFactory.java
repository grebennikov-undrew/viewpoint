package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.DatabaseType;

public class ConnectionFactory {
    public static Executable connect(DatabaseType type) {
        switch (type) {
            case POSTGRESQL:
                return new PostgreSQLConnection();
            case MYSQL:
                return new MySQLConnection();
            default:
                throw new IllegalArgumentException("Unsupported database type: " + type);
        }
    }
}
