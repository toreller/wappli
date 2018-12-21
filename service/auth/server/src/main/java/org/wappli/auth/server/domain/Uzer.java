package org.wappli.auth.server.domain;

import org.wappli.auth.api.enums.UzerStatusEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "uzer")
public class Uzer extends AbstractEntity implements HasId {
    private static final String SEQ_NAME = "seq_uzer_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(max = 256)
    @Column(name = "psw")
    private String psw;

    @NotNull
    @Size(max = 128)
    @Column(name = "email")
    private String email;

    @Column(name = "activated")
    private boolean activated;

    @Size(max = 256)
    @Column(name = "activation_hash")
    private String activationHash;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private Instant createDate;

    @UpdateTimestamp
    @Column(name = "modify_date")
    private Instant modifyDate;

    @NotNull
    @Column(name = "activate_deadline")
    private Instant activateDeadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UzerStatusEnum userStatus;

    @Column(name = "last_login")
    private Instant lastLogin;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "role_switch",
            joinColumns = @JoinColumn(name = "uzer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

}
