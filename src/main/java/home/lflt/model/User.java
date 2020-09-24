package home.lflt.model;

import home.lflt.security.SecurityConfig;
import home.lflt.utils.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"games", "portfolios", "feedbacks"})
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
        games = new HashSet<>();
        portfolios = new HashSet<>();
    }
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
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

    @Column(name="premium_expires")
    private LocalDateTime premiumExpires;

    @ManyToMany
    @JoinTable(name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<Portfolio> portfolios;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;

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

    public boolean isPremiumUser() {
        return premiumExpires != null && premiumExpires.isAfter(LocalDateTime.now());
    }

    public String getEmail() {
        BasicTextEncryptor encryptor = Utils.encryptor();
        return encryptor.decrypt(email);
    }

    public void setEmail(String newEmail) {
        BasicTextEncryptor encryptor = Utils.encryptor();
        email = encryptor.encrypt(newEmail);
    }
}