package home.lflt.model;

import home.lflt.model.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

import static home.lflt.utils.Constants.*;

@Data
public class fRegistration {

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String username; //will be shown publicly

    @NotNull(message = INPUT_REQUIRED)
//    @NotBlank(message = INPUT_REQUIRED)
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = BAD_EMAIL)
    private String email;

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String password; //stored and compared as hash

    @NotNull(message = INPUT_REQUIRED)
    @Min(MIN_AGE)
    @Max(MAX_AGE)
    private int age;

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String city;

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String state;

    private boolean enabled;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, email, passwordEncoder.encode(password), age, city, state, true);
    }
}
