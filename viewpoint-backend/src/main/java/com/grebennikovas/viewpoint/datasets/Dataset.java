package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "datasets")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Dataset.default",
                attributeNodes = {
                        @NamedAttributeNode(value = "user"),
                        @NamedAttributeNode(value = "columns"),
                        @NamedAttributeNode(value = "source"),
                        @NamedAttributeNode(value = "parameters")
                }
        )
})
public class Dataset extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String sqlQuery;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Column> columns;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;
    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Parameter> parameters;

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

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
