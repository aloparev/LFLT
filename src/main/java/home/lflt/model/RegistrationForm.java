package home.lflt.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username; //will be shown publicly
    private  String password; //stored and compared as hash
    private String city;
    private String state;
    private boolean status;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), city, state, status);
    }
}
