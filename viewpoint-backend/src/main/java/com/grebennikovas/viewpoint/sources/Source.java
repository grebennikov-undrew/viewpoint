package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Entity
@Table(name = "sources")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
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

}
