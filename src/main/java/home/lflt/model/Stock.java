package home.lflt.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Stock {
//public class Stock implements Serializable {
    private final String symbol;
    private final String name;
    private final String market;
}