package com.chessrating.config.security;

import com.chessrating.api.model.Role;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class WebSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider =
                keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: Clear error messages?? Currently we have:
//        {
//            "timestamp": "2024-08-13T08:50:37.286+00:00",
//                "status": 403,
//                "error": "Forbidden",
//                "path": "/api/chess-rating/player/2"
//        }
        super.configure(http);
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/chess-rating/players/rating").hasAnyRole(Role.USER.toString(), Role.MANAGER.toString())
                .antMatchers(HttpMethod.POST, "/api/chess-rating/player").hasAnyRole(Role.MANAGER.toString())
                .antMatchers(HttpMethod.DELETE, "/api/chess-rating/player/*").hasRole(Role.MANAGER.toString())
                .antMatchers(HttpMethod.PUT, "/api/chess-rating/player/rating/*").hasAnyRole(Role.USER.toString(), Role.MANAGER.toString())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}