package home.lflt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * game belongs to a player and uses his id as a shared key
 */

@Data
@Slf4j
@Entity
@EqualsAndHashCode(exclude = "portfolios")
@Table(name = "games_pvc")
public class Game {
    public Game(){};

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
//        this.balance_user = funds;
//        this.balance_enemy = funds;
        this.portfolios = new HashSet<>();
        this.users = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    private int user_id;

    private String name;
    private String description;
//    private double balance_user;
//    private double balance_enemy;

    // pojo in portfolio class, CRUD propagation, no orphan removal to allow null values
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<Portfolio> portfolios;

    @ManyToMany(mappedBy = "games")
    private Set<User> users;

    @Transient
    private double delta; // user balance - enemy
}