package home.lflt.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class PagePost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String page;
    String header;
    String body;
    Boolean disabled;
    Integer position;
    LocalDateTime tstamp;
}
