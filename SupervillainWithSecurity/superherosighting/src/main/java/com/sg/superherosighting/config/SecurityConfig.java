/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author anna
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Autowired 
UserDetailsService userDetails;

//    @Autowired
//    public void configureGlobalInMemory(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("{noop}password")// noop-not ecodied password 
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("{noop}password")
//                .roles("ADMIN", "USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")//if it match
                .antMatchers("/", "/home", "index", "/villains", "/villainDetails").permitAll()
                .antMatchers("/styles.css", "/css/**", "/js/**", "/fonts/**", "/images/**", "/js.js").permitAll()//any css/js/.. files
                .anyRequest().hasRole("USER")
                .and()
             .formLogin()
                .loginPage("/login")
                .failureUrl("/login?login_error=1")
                .permitAll()
                .and()
             .logout()
                .logoutSuccessUrl("/")
                .permitAll();
        http.formLogin().defaultSuccessUrl("/home", true);
    }

    @Autowired
    public void configureGlobalnDB(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder());
        
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
       
        return new BCryptPasswordEncoder();
    }

}
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // the boolean flags force the redirection even though 
//        // the user requested a specific secured resource.
//        http.formLogin().defaultSuccessUrl("/success.html", true);
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//    }
//}