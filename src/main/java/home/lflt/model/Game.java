package home.lflt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * game belongs to a player and uses his id as a shared key
 */

@Data
@Slf4j
@Entity
@EqualsAndHashCode(exclude = {"portfolios", "users"})
@Table(name = "games")
public class Game {
    public Game(){};

    public Game(User user) {
        this.name = user.getUsername() + "'s Game";
        this.description = "make the best of daily 2k investments during a week";
//        this.balance_user = funds;
//        this.balance_enemy = funds;
        this.portfolios = new ArrayList<>();
        this.users = new ArrayList<User>(){{
            add(user);
        }};
//        this.users = new ArrayList<>(Arrays.asList(user));
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
    private List<Portfolio> portfolios;

    @ManyToMany(mappedBy = "games")
    private List<User> users;

    @Transient
    private double delta; // user balance - enemy

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", portfolios=" + portfolios +
                ", delta=" + delta +
                '}';
    }
}