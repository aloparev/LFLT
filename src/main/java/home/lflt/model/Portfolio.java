package home.lflt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
@Entity
@EqualsAndHashCode(exclude = "lots")
@Table(name = "portfolios")
public class Portfolio {
    public Portfolio(){};

    public Portfolio(String name, String type, double balance, double funds, char cron, int delay, int epochs) {
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.funds = funds;
        this.cron = cron;
        this.delay = delay;
        this.epochs = epochs;
        this.lots = new HashSet<>();
        this.tstamp = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String info;
    private String type; //of management, man vs bot
    private double balance; //current pf value
    private double funds; //epochal investment sum
    private char cron;
    private int delay;
    private int epochs;
    private LocalDateTime tstamp;
    private LocalDateTime ustamp;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lot> lots;
    @Transient
    private double cptSum; //current lot price total
    @Transient
    private double changePct;
    @Transient
    private double plDailySum; //profit loss
    @Transient
    private double plTotalSum;
}