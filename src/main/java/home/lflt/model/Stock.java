package home.lflt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
//@RequiredArgsConstructor
@Entity
//@NoArgsConstructor
@Table(name = "stocks")
public class Stock {
    private int index;

    @Id
    private  String symbol;
    private  String name;
    private  String market;
    private  String land;
    private  String sector;
    private  String industry;
    private  Date tstamp;

//    @Transient
//    @OneToOne(mappedBy = "stock")
//    private Lot lot;
}