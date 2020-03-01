package home.lflt.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

import static home.lflt.utils.Constants.INPUT_REQUIRED;

@Data
@Entity
@Table(name = "lots")
public class Lot {

    @Id
    private String symbol;

    private int units;
    private double ip;
    private double ipt;

    @Transient
    private double cp;

    @Transient
    private double cpt;

    @Transient
    private double change;

    @Transient
    private double yc;

    @Transient
    private double pld;

    @Transient
    private double plt;

    @CreationTimestamp
    private Date timestamp;

    @Transient
    @OneToOne
    @MapsId
    private Stock stock;
}
