package home.lflt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
//@RequiredArgsConstructor
@Entity
//@NoArgsConstructor
@Table(name = "stocks")
public class Stock {

    @Id
    @Column(length = 9)
    private  String symbol;

    @Column(length = 49)
    private String name;

    private int index;
    private String market;
    private String land;
    private String sector;
    private String industry;
    private LocalDateTime tstamp;

//    @Transient
//    @OneToOne(mappedBy = "stock")
//    private Lot lot;
}