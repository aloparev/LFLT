package home.lflt.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
        this.ipt = units * price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 9)
    private String symbol;

    @Column(length = 49)
    private String name;

    private int units;
    private double ip; //initial buy price
    private double ipt;

    @CreationTimestamp
    private Date tstamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Portfolio portfolio;

    @Transient
    private double cp; //current price
    @Transient
    private double cpt;
    @Transient
    private double changePct;
    @Transient
    private double yc; //yesterday close price
    @Transient
    private double pld; //profit loss daily
    @Transient
    private double plt;

    @Override
    public String toString() {
        return "\n\t" +
                "Lot [id=" + id + ", pf_id=" + portfolio.getId() + ", symbol=" + symbol + ", name=" + name +
                ", units=" + units + ", IP=" + ip + ", IPT=" + ipt + ", tstamp=" + tstamp + "]";
    }
}
