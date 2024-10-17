package ptithcm.tttn.config;


import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ptithcm.tttn.service.impl.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public AppConfig(UserDetailsServiceImpl customerUserDetails) {
        this.userDetailsService = customerUserDetails;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Allow sessions for OAuth2
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/user/**").permitAll()  // Require authentication
                .antMatchers("/api/staff/**").hasAnyAuthority("STAFF", "MANAGER", "SHIPPER")
                .antMatchers("/api/manager/**").hasAnyAuthority("MANAGER", "STAFF")
                .and()
                .oauth2Login(oauth2login -> {
                    oauth2login
                            .loginPage("/login")
                            .successHandler((request, response, authentication) -> {
                                // Log or handle authentication success
                                OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
                                if (auth != null) {
                                    System.out.println("Authenticated user: " + auth.getPrincipal().getAttributes());
                                    response.sendRedirect("/user");
                                } else {
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
                                }
                            });
                })
                .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic()
                .and()
                .formLogin();  // Optional: Allows form-based login if needed
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(

                "http://localhost:3000", // React
                "http://localhost:5173",
                "http://localhost:4200",             // Angular
                "https://wachshop-react.onrender.com"
                // Add the deployed GitHub Pages URL

        ));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setMaxAge(3600L);

        return request -> configuration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
