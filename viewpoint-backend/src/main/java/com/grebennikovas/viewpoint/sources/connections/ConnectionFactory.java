package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class ConnectionFactory {

    @Autowired
    private List<DbConnection> connections;

    public static Map<String, DbConnection> connectionsCache = new HashMap<>();

    public ConnectionFactory(List<DbConnection> connections) {
        this.connections = connections;
    }

    @PostConstruct
    void initCache() {
        connections.forEach(c -> connectionsCache.put(c.getType(),c));
    }

    public DbConnection getConnection(Source source) {
        if (connectionsCache.containsKey(source.getType().getCredit())) {
            DbConnection connection = connectionsCache.get(source.getType().getCredit());
            connection.setSource(source);
            return connection;
        }
        throw new NoSuchElementException("Unsupported database type " + source.getType().getCredit());
    }

//    public static DbConnection connect(Source source) {
//        DbConnection connection;
//        switch (source.getType()) {
//            case POSTGRESQL:
//                connection = new PgConnection();
//                connection.setSource(source);
//                break;
//            case MYSQL:
//                connection = new MySQLConnection();
//                connection.setSource(source);
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported database type: " + source.getType());
//
//        }
//        return connection;
//    }
}
