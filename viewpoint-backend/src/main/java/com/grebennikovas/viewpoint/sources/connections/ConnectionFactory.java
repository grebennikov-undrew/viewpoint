package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import java.sql.Driver;

public class ConnectionFactory {
    public static Executable connect(Source source) throws ClassNotFoundException {
        Executable connection;
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        switch (source.getType()) {
            case POSTGRESQL:
                connection = new PostgreSQLConnection(source);
                break;
            default:
                throw new IllegalArgumentException("Unsupported database type: " + source.getType());

        }
        return connection;
    }
}
