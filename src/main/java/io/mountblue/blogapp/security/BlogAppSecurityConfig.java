package io.mountblue.blogapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class BlogAppSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username,role FROM roles where username=?");

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/register","/processNewUser","/",
                                "/filterPosts","/viewPost","/api/**").permitAll()
                        .requestMatchers("/**").hasAnyRole("AUTHOR","ADMIN")
                        .anyRequest().authenticated()
        )
                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedPage("/access-denied")
                )
                .formLogin(form ->
                        form
                                .loginPage("/loginPage")
                                .loginProcessingUrl("/authenticateUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                );
        httpSecurity.httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }
}
