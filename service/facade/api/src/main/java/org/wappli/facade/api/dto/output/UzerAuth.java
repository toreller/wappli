package org.wappli.facade.api.dto.output;

import lombok.Data;

import java.util.Set;

@Data
public class UzerAuth {
    private Long id;
    private String password;
    private String username;
    private Set<String> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
