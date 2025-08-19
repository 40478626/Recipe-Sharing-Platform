package com.example.recipe_sharing_platform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {


    private static final Logger logger = LoggerFactory.getLogger(CustomJwtGrantedAuthoritiesConverter.class);

    private static final String REALM_ACCESS_CLAIM_NAME = "realm_access";
    private static final String ROLES_CLAIM_NAME = "roles";
    private static final String AUTHORITY_PREFIX = "ROLE_";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        Map<String, Object> realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM_NAME);
        if (realmAccess != null && realmAccess.containsKey(ROLES_CLAIM_NAME)) {
            List<String> realmRoles = (List<String>) realmAccess.get(ROLES_CLAIM_NAME);
            for (String role : realmRoles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + role));
            }
        }

        logger.info("JWT Authorities: {}", grantedAuthorities);
        return grantedAuthorities;
    }
}
