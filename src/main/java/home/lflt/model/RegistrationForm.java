package home.lflt.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username; //will be shown publicly
    private String email;
    private String password; //stored and compared as hash
    private String city;
    private String state;
    private boolean enabled;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, email, passwordEncoder.encode(password), city, state, true);
    }
}
