package home.lflt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * authorizes user with hash password
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("detailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * security rules, e.g.
     * - special conditions and access rules (first rules overwrite the following)
     * - custom login page
     * - user's log out
     * - cross-site request forgery protection
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/game")
            .access("hasRole('ROLE_USER')")
            .antMatchers("/", "/**").access("permitAll")

//                custom login
            .and()
                .formLogin()
//                .loginPage("/")
//                .defaultSuccessUrl("/")

            .and()
                .logout()
                .logoutSuccessUrl("/")

            // Make H2-Console non-secured; for debug purposes
            .and()
                .csrf();
//                .ignoringAntMatchers("/h2-console/**")

            // Allow pages to be loaded in frames from the same origin; needed for H2-Console
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin();
    }
//end::configureHttpSecurity[]
//end::authorizeRequests[]
//end::customLoginPage[]

    //encodes password with SHA256
    @Bean
    public PasswordEncoder encoder() {
//        return new StandardPasswordEncoder("53cr3t");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
