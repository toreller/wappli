package org.wappli.admin.server.domain;

import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Customer extends AbstractEntity implements HasId, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQ_NAME = "seq_customer_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
