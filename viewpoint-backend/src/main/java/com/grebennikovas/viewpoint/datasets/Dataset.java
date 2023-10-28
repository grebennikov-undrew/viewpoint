package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "datasets")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Dataset.default",
                attributeNodes = {
                        @NamedAttributeNode(value = "user"),
                        @NamedAttributeNode(value = "source"),
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
//    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<com.grebennikovas.viewpoint.datasets.column.Column> columns;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;
//    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<com.grebennikovas.viewpoint.datasets.parameter.Parameter> parameters;


    public Dataset() {
    }

    public Dataset(Long id) {
        this.id = id;
    }

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

//    public List<com.grebennikovas.viewpoint.datasets.column.Column> getColumns() {
//        return columns;
//    }
//
//    public void setColumns(List<Column> columns) {
//        this.columns = columns;
//    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

//    public List<com.grebennikovas.viewpoint.datasets.parameter.Parameter> getParameters() {
//        return parameters;
//    }
//
//    public void setParameters(List<Parameter> parameters) {
//        this.parameters = parameters;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dataset dataset = (Dataset) o;

        if (!Objects.equals(id, dataset.id)) return false;
        if (!Objects.equals(name, dataset.name)) return false;
        if (!Objects.equals(sqlQuery, dataset.sqlQuery)) return false;
        if (!Objects.equals(user, dataset.user)) return false;
        return Objects.equals(source, dataset.source);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}
