package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Table(name = "sources")
@Getter
@Setter
@NoArgsConstructor
public class Source {

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

    public Source(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        return Objects.equals(id, source.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
