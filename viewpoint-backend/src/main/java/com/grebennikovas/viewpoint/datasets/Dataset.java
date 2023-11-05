package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "datasets")

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Dataset.default",
                attributeNodes = {
                        @NamedAttributeNode(value = "user"),
                        @NamedAttributeNode(value = "source"),
                        @NamedAttributeNode(value = "parameters"),
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
//    private List<Column> columns;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Parameter> parameters;

}
