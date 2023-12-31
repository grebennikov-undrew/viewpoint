package com.grebennikovas.viewpoint.datasets;

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
@NoArgsConstructor
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

    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Column> columns; // Колонки не должны повторяться

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    public Dataset(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dataset dataset = (Dataset) o;

        if (!Objects.equals(id, dataset.id)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
