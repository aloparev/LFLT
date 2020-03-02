package home.lflt.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class fmpQuote {
    private String symbol;
    private double price;
    private double changesPercentage;
    private double change;
    private double dayLow;
    private double dayHigh;
    private double yearLow;
    private double yearHigh;
    private double marketCap;
    private double priceAvg50;
    private double priceAvg200;
    private double volume;
    private double avgVolume;
    private String exhange;
    private double open;
    private double previousClose;
    private double eps;
    private double pe;
    private Date earningsAnnouncement;
    private long sharesOutstanding;
    private String timestamp;

    public Date getDateFromTstamp() {
        return new Date(Long.parseLong(timestamp));
    }

}
