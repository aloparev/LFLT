package home.lflt.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
//@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
//@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
//@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    public User() {}
    public User(String username, String email, String password, String city, String state, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.city = city;
        this.state = state;
        this.enabled = enabled;
    }
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username; //will be shown publicly
    private String email;
    private String password; //stored and compared as hash
//    private final String fullname;
//    private final String street;
    private String city;
    private String state;
//    private final String zip;
//    private final String phoneNumber;
    private boolean enabled;

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