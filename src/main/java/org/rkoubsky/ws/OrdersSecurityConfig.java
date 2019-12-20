package org.rkoubsky.ws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@Configuration
@EnableWebSecurity
public class OrdersSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${orders.service.username}")
    private String username;
    @Value("${orders.service.password}")
    private String password;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(this.username)
            .password(this.encoder().encode(this.password))
            .roles("USER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/ws/order_service/**").authenticated()
            .and()
            .formLogin().disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
