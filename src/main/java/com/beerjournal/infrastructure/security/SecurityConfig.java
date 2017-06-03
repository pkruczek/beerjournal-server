package com.beerjournal.infrastructure.security;

import com.beerjournal.infrastructure.security.facebook.FacebookConnectionSignup;
import com.beerjournal.infrastructure.security.facebook.FacebookSignInAdapter;
import com.beerjournal.infrastructure.security.handlers.BjAuthenticationFailureHandler;
import com.beerjournal.infrastructure.security.handlers.BjAuthenticationSuccessHandler;
import com.beerjournal.infrastructure.security.handlers.BjLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

@Configuration
@Profile({"dev", "prod"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.beerjournal.infrastructure.security"})
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final UsersConnectionRepository usersConnectionRepository;
    private final FacebookConnectionSignup facebookConnectionSignup;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        configureRequestAuthorization(http);
        configureCacheHeaders(http);
        configureLogin(http);
        configureLogout(http);
        configureMisc(http);
    }

    private void configureRequestAuthorization(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login*", "/signin/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
                        "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/account/password/reset").permitAll()
                .anyRequest().hasAnyRole("USER", "FACEBOOK_USER");
    }

    private void configureCacheHeaders(HttpSecurity http) throws Exception {
        http
                .headers()
                .cacheControl()
                .disable();
    }

    private void configureLogin(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(new BjAuthenticationSuccessHandler())
                .failureHandler(new BjAuthenticationFailureHandler());
    }

    private void configureLogout(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutSuccessHandler(new BjLogoutSuccessHandler());
    }

    private void configureMisc(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();
        http
                .exceptionHandling();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp(facebookConnectionSignup);

        return new ProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository,
                new FacebookSignInAdapter());
    }
}
