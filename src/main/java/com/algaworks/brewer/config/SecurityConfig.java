package com.algaworks.brewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.algaworks.brewer.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = { AppUserDetailsService.class })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("admin").password("admin").roles("CADASTRAR_CLIENTE");
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder());
    }

    // @formatter:off
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/usuarios/**").hasRole("CADASTRAR_USUARIO")
                .antMatchers("/cidades/novo").hasRole("CADASTRAR_CIDADE")
                .anyRequest().authenticated()
                //.anyRequest().denyAll() mesmo as pág que não existem dão acesso negado.
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
            .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login");

    }
    // @formatter:on

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**").antMatchers("/layout/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
