package home.lflt.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static home.lflt.utils.Constants.INPUT_REQUIRED;

@Data
public class Feedback {

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String type;

//    private String url;
//    private String device;
//    private String os;

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String msg;

    @NotNull(message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String name;

//    @Email
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = INPUT_REQUIRED)
    @NotBlank(message = INPUT_REQUIRED)
    private String email;
}
