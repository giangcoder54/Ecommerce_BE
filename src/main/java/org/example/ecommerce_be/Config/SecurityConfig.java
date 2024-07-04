package org.example.ecommerce_be.Config;


// imports

import org.example.ecommerce_be.Security.UserDetailsImplService;
import org.example.ecommerce_be.Service.JWT.AuthenticationEntryPointHandler;
import org.example.ecommerce_be.Service.JWT.AuthenticationFilter1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.AuthenticationFilter;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        // securedEnabled = true,
//        // jsr250Enabled = true,
//        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        UserDetailsImplService userDetailsService;

        @Autowired
        private AuthenticationEntryPointHandler unauthorizedHandler;

        @Bean

        public AuthenticationFilter1 authenticationJwtTokenFilter() {
                return new AuthenticationFilter1();
        }

        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
                authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
                return super.authenticationManagerBean();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.cors().and().csrf().disable()
                        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeRequests().antMatchers("/", "/home", "/search", "/api/auth/**", "/verify",
                                "/product/**", "/product-detail/**", "/blog/**", "/blog-detail/**", "/about", "/contact",
                                "/review-ws/**", "/auto-chat").permitAll()
                        .antMatchers("/api/test/**").permitAll()
                        .anyRequest().authenticated();

                http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        }
}


