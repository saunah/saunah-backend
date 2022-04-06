package ch.saunah.saunahbackend;

import ch.saunah.saunahbackend.model.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class SaunahBackendApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(SaunahBackendApplication.class, args);
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and()
            .authorizeRequests().antMatchers("/").permitAll()
            .and()
            .authorizeRequests().antMatchers("/user/**").hasAnyRole(UserRole.USER.toString(), UserRole.ADMIN.toString());
    }

}
