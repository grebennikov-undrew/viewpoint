package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;

public class ConnectionFactory {
    public static Executable connect(Source source) {
        Executable connection;
        switch (source.getType()) {
            case POSTGRESQL:
                connection = new PostgreSQLConnection();
                connection.setSource(source);
                break;
            case MYSQL:
                connection = new MySQLConnection();
                connection.setSource(source);
                break;
            default:
                throw new IllegalArgumentException("Unsupported database type: " + source.getType());

        }
        return connection;
    }
}
