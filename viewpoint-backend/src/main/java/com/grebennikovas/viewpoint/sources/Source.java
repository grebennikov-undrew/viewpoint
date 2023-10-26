package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.BaseEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sources")
public class Source extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private DatabaseType type;
    private String netloc;
    private String port;
    private String dbname;
    private String params;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatabaseType getType() {
        return type;
    }

    public void setType(DatabaseType type) {
        this.type = type;
    }

    public String getNetloc() {
        return netloc;
    }

    public void setNetloc(String netloc) {
        this.netloc = netloc;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        if (!id.equals(source.id)) return false;
        if (!Objects.equals(name, source.name)) return false;
        if (type != source.type) return false;
        if (!Objects.equals(netloc, source.netloc)) return false;
        if (!Objects.equals(port, source.port)) return false;
        if (!Objects.equals(dbname, source.dbname)) return false;
        if (!Objects.equals(params, source.params)) return false;
        if (!Objects.equals(username, source.username)) return false;
        return Objects.equals(password, source.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + (netloc != null ? netloc.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (dbname != null ? dbname.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
