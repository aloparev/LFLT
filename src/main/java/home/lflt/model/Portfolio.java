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
        this.type = type; //see utils.constants
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
    private String type; //user, random, over ... >> see constants
    private double balance; //left overs after purchase + PL
    private double funds; //epochal investment sum
    private char cron; //reoccurrence
    private int delay; //cron units
    private int epochs; //how much rounds left
    private LocalDateTime tstamp;
    private LocalDateTime ustamp;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lot> lots;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Transient
    private double cptSum; //current lot price total
    @Transient
    private double changePct;
    @Transient
    private double plDailySum; //profit loss
    @Transient
    private double plTotalSum;
}