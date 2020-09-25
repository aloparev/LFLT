package home.lflt.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Builder
@Data
public class Quote {
    private String symbol;
    private double price;
    private double change;
    private double changeAbs;
}
