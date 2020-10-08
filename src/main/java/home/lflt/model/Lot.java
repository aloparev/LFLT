package home.lflt.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
//@EqualsAndHashCode(exclude = "portfolio")
@Table(name = "lots")
public class Lot {
    public Lot(){}

    public Lot(String symbol, String name, int units, double price) {
        this.symbol = symbol;
        this.name = name;
        this.units = units;
        this.ip = price;
        this.cp = price;
//        this.ipt = units * price;
    }

    public Lot(Portfolio pp, String symbol, String name, int units, double price) {
        this(symbol, name, units, price);
        this.portfolio = pp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 9, nullable = false)
    private String symbol;

    @Column(length = 99)
    private String name;

    private int units;
    private double ip; //initial buy price
    private double cp; //current price

    @CreationTimestamp
    private Date tstamp;

//    private LocalDateTime ustamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Portfolio portfolio;

//    not in db

    @Transient
    private double ipt;
    @Transient
    private double cpt;
    @Transient
    private double change;
    @Transient
    private double yesterdayClose; //yesterday close price
    @Transient
    private double pld; //profit loss daily
    @Transient
    private double plt;
    @Transient
    private boolean error;

    public boolean getError() {
        return error;
    }

    public double getIpt() {
        return units * ip;
    }

    @Override
    public String toString() {
        return "\n\t" +
                "Lot [id=" + id + ", pf_id=" + portfolio.getId() + ", symbol=" + symbol + ", name=" + name +
                ", units=" + units + ", IP=" + ip + ", CP=" + cp + ", CPT=" + cpt + ", tstamp=" + tstamp + "]";
    }
}
