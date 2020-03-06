package home.lflt.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "quotes")
public class fmpQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String symbol;
    private double price;

    @Column(name="changespercentage")
    private double changesPercentage;
    private double change;

    @Column(name="daylow")
    private double dayLow;

    @Column(name="dayhigh")
    private double dayHigh;

    @Column(name="yearlow")
    private double yearLow;

    @Column(name="yearhigh")
    private double yearHigh;

    @Column(name="marketcap")
    private double marketCap;
    @Column(name="priceavg50")
    private double priceAvg50;
    @Column(name="priceavg200")
    private double priceAvg200;
    private double volume;

    @Column(name="avgvolume")
    private double avgVolume;
    private String exhange;
    private double open;
    @Column(name="previousclose")
    private double previousClose;
    private double eps;
    private double pe;
    @Column(name="earningsannouncement")
    private Date earningsAnnouncement;
    @Column(name="sharesoutstanding")
    private long sharesOutstanding;

    @Transient
    private String timestamp;

    @CreationTimestamp
    private Date tstamp;

    public Date getDateFromTstamp() {
        return new Date(Long.parseLong(timestamp));
    }

}
