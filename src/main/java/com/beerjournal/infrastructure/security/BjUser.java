package com.beerjournal.infrastructure.security;

import com.beerjournal.breweriana.persistence.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class BjUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private final User dbUser;

    @Builder
    public BjUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked,
                  Collection<? extends GrantedAuthority> authorities, User dbUser) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.dbUser = dbUser;
    }

}
