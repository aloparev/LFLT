package home.lflt.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
//@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
//@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
//@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    public User() {}
    public User(String username, String email, String password, int age, String city, String state, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.city = city;
        this.state = state;
        this.enabled = enabled;
        this.tstamp = LocalDateTime.now();
    }
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username; //will be shown publicly
    private String email;
    private String password; //stored and compared as hash
    private int age;
//    private final String fullname;
//    private final String street;
    private String city;
    private String state;
//    private final String zip;
//    private final String phoneNumber;
    private boolean enabled;
    private LocalDateTime tstamp;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}