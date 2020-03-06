package home.lflt.repo;

import home.lflt.model.Lot;
import home.lflt.model.fmpQuote;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepo extends CrudRepository<fmpQuote, Long> {

}
