package home.lflt.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@Builder
@Data
@Slf4j
@Entity
@EqualsAndHashCode(exclude = "lots")
@Table(name = "portfolios")
public class Portfolio {
    public Portfolio() {};

    public Portfolio(String type, int balance, User user) {
        this.name = user.getUsername() + " Portfolio";
        this.info = "self-managed";
        this.type = type; //see utils.constants
        this.balance = balance;
        this.user = user;
        this.lots = new HashSet<>();
        this.tstamp = LocalDateTime.now();
        this.cron = 'd';
        this.epochs = 111;
    }

    public Portfolio(String name, String type, int balance, double funds, char cron, int delay, int epochs) {
        this.name = name;
        this.type = type; //see utils.constants
//        this.balance = funds;
        this.balance = balance;
        this.funds = funds;
        this.cron = cron;
        this.delay = delay;
        this.epochs = epochs;
        this.lots = new HashSet<>();
        this.tstamp = LocalDateTime.now();
    }

    public Portfolio(String name, String type, double funds, char cron, int delay, int epochs, User user, Game game) {
        this(name, type, 0, funds, cron, delay, epochs);
        this.user = user;
        this.game = game;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String info;
    private String type; //user, random, over ... >> see constants
    private double balance; // available funds for investment
    private double funds; // epochal investment sum, shall be null after buy
    private char cron; //reoccurrence
    private int delay; //cron units
    private int epochs; //rounds to go
    private LocalDateTime tstamp;
    private LocalDateTime ustamp;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lot> lots;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Transient
    private double cptSum; // current stock price total ( + balance = worth )

    @Transient
    private double change;
    @Transient
    private double plDailySum; //profit loss
    @Transient
    private double plTotalSum;

    public long getOwnerId() {
        return user.getId();
    }

    public String getOwnerName() {
        if(user == null)
            return "";
        else
            return user.getUsername();
    }

    public boolean noEpochsLeft() {
        return epochs < 1;
    }

    public boolean ownedByUser(String username) {
        return Objects.equals(getOwnerName(), username);
    }

    public void updateUstamp() {
        ustamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String gameString = "NULL";
        String userString = "NULL";

        if(game != null) gameString = game.getName();
        if(user != null) userString = user.getUsername();

        return "Portfolio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", funds=" + funds +
                ", cron=" + cron +
                ", delay=" + delay +
                ", epochs=" + epochs +
                ", tstamp=" + tstamp +
                ", ustamp=" + ustamp +
                ", lots=" + lots +
                ", game=" + gameString +
                ", user=" + userString +
                ", cptSum=" + cptSum +
                ", change=" + change +
                ", plDailySum=" + plDailySum +
                ", plTotalSum=" + plTotalSum +
                '}';
    }
}