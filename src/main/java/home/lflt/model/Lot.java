package home.lflt.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "lots")
public class Lot {
    public Lot(String symbol, int units, double price) {
        this.symbol = symbol;
        this.units = units;
        this.ip = price;
        this.ipt = units * price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String symbol;
    private int units;
    private double ip;
    private double ipt;

    @CreationTimestamp
    private Date tstamp;

    @ManyToOne
    @JoinColumn(name="portfolio_id", nullable = false)
    private Portfolio portfolio;

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
}
