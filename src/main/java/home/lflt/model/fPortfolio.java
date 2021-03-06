package home.lflt.model;

import home.lflt.security.GetUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

import static home.lflt.utils.Constants.*;

@Builder
@Data
public class fPortfolio {

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String name;

//    @NotNull(message = INPUT_REQUIRED)
//    @NotBlank(message = INPUT_REQUIRED)
//    private String info;
//    private String type;

    @NotNull(message = INPUT_REQUIRED)
    @Min(MIN_FUNDS)
    @Max(MAX_FUNDS)
    private int funds;

//    @NotNull(message = INPUT_REQUIRED)
//    @NotBlank(message = INPUT_REQUIRED)
//    private String cron;

    @NotNull(message = INPUT_REQUIRED)
    @Min(1)
    @Max(2)
    private int delay;

    @NotNull(message = INPUT_REQUIRED)
    @Min(5)
    @Max(10)
    private int epochs;

    private User user;
    private Game game;

    public Portfolio toUserManagedPortfolio() {
        return new Portfolio(user.getUsername() + name, "USER", funds, 'd', delay, epochs, user, game);
    }

    public Portfolio toRandomPortfolio() {
        return new Portfolio("opponent" + name, "RANDOM", funds, 'd', delay, epochs, user, game);
    }

    @Override
    public String toString() {
        return "Portfolio Form {" +
                "name='" + name + '\'' +
                ", funds=" + funds +
                ", delay=" + delay +
                ", epochs=" + epochs +
                ", user id=" + user.getId() +
                '}';
    }
}
