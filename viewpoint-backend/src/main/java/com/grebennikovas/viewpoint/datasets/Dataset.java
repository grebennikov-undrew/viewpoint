package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.datasets.column.Column;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "datasets")
@Getter
@Setter
//@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Dataset.default",
                attributeNodes = {
                        @NamedAttributeNode(value = "user"),
                        @NamedAttributeNode(value = "source"),
                        @NamedAttributeNode(value = "columns"),
                }
        )
})
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String sqlQuery;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Column> columns; // Колонки не должны повторяться

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

//    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    private List<Parameter> parameters;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dataset dataset = (Dataset) o;

        if (!Objects.equals(id, dataset.id)) return false;
//        if (!Objects.equals(name, dataset.name)) return false;
//        if (!Objects.equals(sqlQuery, dataset.sqlQuery)) return false;
//        if (!Objects.equals(user, dataset.user)) return false;
//        if (!Objects.equals(columns, dataset.columns)) return false;
//        return Objects.equals(source, dataset.source);
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
//        result = 31 * result + (user != null ? user.hashCode() : 0);
//        result = 31 * result + (columns != null ? columns.hashCode() : 0);
//        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}
