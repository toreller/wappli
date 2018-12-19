package org.wappli.auth.server.domain;

import lombok.Data;
import org.wappli.common.api.rest.util.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "role")
public class Role implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "key")
    private String key;

    @Size(max = 100)
    @Column(name = "description")
    private String description;
}
