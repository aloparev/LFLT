package home.lflt.repo;

import home.lflt.model.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface StockRepo extends CrudRepository<Stock, String> {
    Stock getBySymbol(String symbol);
    Stock getByIndex(int ind);

    @Query(nativeQuery = true, value = "SELECT min(tstamp) FROM stocks")
    LocalDateTime getMinDate();

    @Query(nativeQuery = true, value = "SELECT count(*) FROM stocks")
    int getCount();
}
