package home.lflt.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Slf4j
@Entity
@EqualsAndHashCode(exclude = "lots")
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String info;
    private String type;

    private double funds;
    private int epochs;

//    @CreationTimestamp
    private LocalDateTime tstamp;

    private LocalDateTime ustamp;

//    @PreUpdate
//    private void onUpdate() {
//        log.info("private void onUpdate()");
//        this.ustamp = LocalDateTime.now();
//    }

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lot> lots;

    @Transient
    private double cptSum;

    @Transient
    private double changeSum;

    @Transient
    private double plDailySum;

    @Transient
    private double plTotalSum;

}
