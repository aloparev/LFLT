package home.lflt.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static home.lflt.utils.Constants.INPUT_REQUIRED;

@Data
public class Feedback {

    public enum Type {
        IDEA, GUI, BUG, OTHER
    }



    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String type;
//    private Feedback.Type type;


    private String url;
    private String device;
    private String os;

    @NotNull
    @NotBlank(message = INPUT_REQUIRED)
    private String message;

    @Email
    @NotBlank(message = INPUT_REQUIRED)
    private String email;
}
