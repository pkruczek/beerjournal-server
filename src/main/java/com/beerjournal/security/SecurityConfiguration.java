package com.beerjournal.security;

import com.beerjournal.security.handler.BjAuthenticationFailureHandler;
import com.beerjournal.security.handler.BjAuthenticationSuccessHandler;
import com.beerjournal.security.handler.BjLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic();

        configureRequestAuthorization(http);
        configureLogin(http);
        configureLogout(http);
        configureMisc(http);

    }

    private void configureMisc(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();

        http
                .exceptionHandling();
    }

    private void configureLogout(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutSuccessHandler(new BjLogoutSuccessHandler());
    }

    private void configureLogin(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(new BjAuthenticationSuccessHandler())
                .failureHandler(new BjAuthenticationFailureHandler());
    }

    private void configureRequestAuthorization(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/resources/**", "/register").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().hasRole("USER");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
