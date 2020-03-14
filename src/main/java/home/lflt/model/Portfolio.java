package home.lflt.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String info;
    private String type;

    private int funds;
    private int epoch;

    @CreationTimestamp
    private LocalDateTime tstamp;

    @UpdateTimestamp
    private LocalDateTime ustamp;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lot> lots = new ArrayList<>();

    @Transient
    private double cpts;

    @Transient
    private double changeSum;

    @Transient
    private double plds;

    @Transient
    private double plts;
}
