package com.beerjournal.infrastructure.security;

import com.beerjournal.breweriana.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class BjPrincipal extends org.springframework.security.core.userdetails.User {

    private final User dbUser;

    @Builder
    private BjPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities, User dbUser) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.dbUser = dbUser;
    }

}
