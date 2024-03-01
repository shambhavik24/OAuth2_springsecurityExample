package com.example.Securitydemoproject.config;

import com.example.Securitydemoproject.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.web.server.ServerHttpSecurity.http;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails student =User.withUsername("leo")
                .password(passwordEncoder().encode("password"))
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(student);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(
         c -> c.disable()
        )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/css/**")
                                .anonymous().requestMatchers(HttpMethod.GET,"/index*","/static/**","/*.js", "/*.json",
                                        "/*.ico","/rest")
                                .permitAll().
                                anyRequest().
                                authenticated()
                )
                .formLogin(
                        form -> form.loginPage("/index.html").permitAll()
                                .loginProcessingUrl("/perform_login")
                                .defaultSuccessUrl("/home.html")
                )
                .oauth2Login(

                        form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/home")

                )

                .logout(
                        form -> form.invalidateHttpSession(true).clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                );
                return  http.build();
    }

}
