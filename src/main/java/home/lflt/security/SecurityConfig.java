package home.lflt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * custom login page
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("detailsService")
    @Autowired
    private UserDetailsService userDetailsService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/design", "/orders")
//                .access("hasRole('ROLE_USER')")
//                .antMatchers("/", "/**").access("permitAll")
//                //end::authorizeRequests[]
//
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                //end::customLoginPage[]
//
//                // tag::enableLogout[]
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                // end::enableLogout[]
//
//                // Make H2-Console non-secured; for debug purposes
//                // tag::csrfIgnore[]
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/h2-console/**")
//                // end::csrfIgnore[]
//
//                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
//                // tag::frameOptionsSameOrigin[]
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//        // end::frameOptionsSameOrigin[]
//
//        //tag::authorizeRequests[]
//        //tag::customLoginPage[]
//        ;
//    }
//end::configureHttpSecurity[]
//end::authorizeRequests[]
//end::customLoginPage[]

    //encodes password with SHA256
    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
