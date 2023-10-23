package com.grebennikovas.viewpoint.sources;

public enum DatabaseType {
    POSTGRESQL ("postgresql"),
    MYSQL ("mysql");

    private String credit;

    DatabaseType(String credit) {
        this.credit = credit;
    }

    public String getCredit() {
        return credit;
    }
}
