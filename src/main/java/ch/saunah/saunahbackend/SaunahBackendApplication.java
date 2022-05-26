package ch.saunah.saunahbackend;

import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.security.JwtAuthenticationEntryPoint;
import ch.saunah.saunahbackend.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * This class starts the application
 */
@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SaunahBackendApplication extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * The main method.
     *
     * @param args client main argument
     */
    public static void main(String[] args) {
        SpringApplication.run(SaunahBackendApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
            .csrf().disable()
            .authorizeRequests()
            // api-docs
            .antMatchers(
                "/api-docs/**"
            ).permitAll()
            // user management
            .antMatchers(
                "/login",
                "/signup",
                "/verify/**",
                "/reset-password/**",
                "/reset-password"
            ).permitAll()
            // saunas
            .mvcMatchers(
                HttpMethod.GET,
                "/saunas",
                "/saunas/{id}"
            ).permitAll()
            .antMatchers(
                "/saunas",
                "/saunas/{id}"
            ).hasAuthority(UserRole.ADMIN.toString())
            // sauna images
            .mvcMatchers(
                HttpMethod.GET,
                "/saunas/{id}/images",
                "/saunas/images/{fileName}"
            ).permitAll()
            .antMatchers(
                "/saunas/{id}/images",
                "/saunas/images/{id}"
            ).hasAuthority(UserRole.ADMIN.toString())
            // prices
            .mvcMatchers(
                HttpMethod.GET,
                "/prices",
                "/prices/{id}"
            ).permitAll()
            .antMatchers(
                "/prices/{id}"
            ).hasAuthority(UserRole.ADMIN.toString())
            // bookings
            .mvcMatchers(
                HttpMethod.PUT,
                "/bookings/{id}"
            ).hasAuthority(UserRole.ADMIN.toString())
            .mvcMatchers(
                "/bookings",
                "/bookings/{id}",
                "bookings/{id}/price",
                "bookings/{id}/sauna"
            ).hasAnyAuthority(UserRole.USER.toString(), UserRole.ADMIN.toString())
            .antMatchers(
                "/bookings/all",
                "/bookings/{id}/approve",
                "/bookings/{id}/cancel"
            ).hasAuthority(UserRole.ADMIN.toString())
            //users
            .mvcMatchers(
                HttpMethod.GET,
                "/users/{id}"
            ).hasAnyAuthority(UserRole.USER.toString(), UserRole.ADMIN.toString())
            .antMatchers(
                "/users",
                "users/all"
            ).hasAuthority(UserRole.ADMIN.toString())
            .antMatchers(
                HttpMethod.PUT,
                "/users/{id}"
            ).hasAnyAuthority(UserRole.USER.toString(), UserRole.ADMIN.toString())
            .antMatchers(
                HttpMethod.DELETE,
                "/users/{id}"
            ).hasAnyAuthority(UserRole.ADMIN.toString())
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * This method returns the authenticationManagerBean.
     *
     * @return authenticationManagerBean
     * @throws Exception when AuthenticationManager could not be localized
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * This method returns the BCryptPasswordEncoder.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method configures authenticationManagerBuilder.
     *
     * @param authenticationManagerBuilder used to create an {@link AuthenticationManager}
     * @throws Exception if an error occurs when adding the UserDetailsService based authentication
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
