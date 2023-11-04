package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PgConnectionTest {

    @Test
    void getUrl() {
        Source source = new Source();
        String dbname = "some_dbname";
        String driver = DatabaseType.POSTGRESQL.getCredit();
        String netloc = "1.1.1.1";
        String params = "";
        String username = "user";
        String password = "password";
        String port = "5432";
        source.setDbname(dbname);
        source.setType(DatabaseType.POSTGRESQL);
        source.setNetloc(netloc);
        source.setParams(params);
        source.setUsername(username);
        source.setPassword(password);
        source.setPort(port);
        DbConnection pgConn = new PgConnection();
        pgConn.setSource(source);
        String expected = String.format("jdbc:%s://%s:%s/%s?user=%s&password=%s%s",driver,netloc,port,dbname,username,password,params);
        String actual = pgConn.getUrl();
        assertEquals(expected,actual);
    }

    @Test
    void getColtypes() {
    }

    @Test
    void getJavaColType() {
    }
}
