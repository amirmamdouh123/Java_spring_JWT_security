package com.example.demo.jwt_security;

import com.example.demo.jwt_security.services.UserService;
//import com.example.demo.jwt_security.utils.JwtAuthFilter;
//import com.example.demo.jwt_security.utils.JwtAuthFilter;
import com.example.demo.jwt_security.utils.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;


    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    JwtAuthenticationEntry jwtAuthenticationEntry;


//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception { //basic authentication
//            auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//        }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception { //authorization
        http.csrf().disable().authorizeRequests().antMatchers("/unsecure/**")
                .permitAll().anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntry).and(). //lw mafi4 header Authentication -> error
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // m4 48alin bSessions (stateless) -> a7na 48alin bJWT

        http.addFilterBefore(JwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public JwtAuthenticationFilter JwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

}
