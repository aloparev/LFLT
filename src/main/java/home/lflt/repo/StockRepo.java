package home.lflt.repo;

import home.lflt.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepo extends CrudRepository<Stock, String> {

}