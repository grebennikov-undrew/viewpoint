package com.grebennikovas.viewpoint.sources.connections.pgsql;

import com.grebennikovas.viewpoint.utils.Alias;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SqlBuilderTest {
    @Test
    public void testBuildQuery() {
        // Arrange
        Alias col1 = new Alias("col1");
        Alias col2 = new Alias("col2", "label");
        List<Alias> aliases = Arrays.asList(col1, col2);
        String tableName = "example_table";
        String conditions = "col1 > 10";
        List<Alias> groupByAliases = Arrays.asList(col1);
        List<Alias> orderByAliases = Arrays.asList(col1,col2);
        int limitCount = 5;

        SqlBuilder builder = new SqlBuilder()
                .select(aliases)
                .from(tableName)
                .where(conditions)
                .groupBy(groupByAliases)
                .orderBy(orderByAliases, true)
                .limit(limitCount);

        // Act
        String query = builder.build();

        // Assert
        String expectedQuery = """
                SELECT col1 as "col1", col2 as "label"
                FROM example_table
                WHERE col1 > 10
                GROUP BY col1
                ORDER BY col1, col2 DESC
                LIMIT 5
                """;

        assertEquals(expectedQuery, query);
    }
}