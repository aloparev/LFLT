package home.lflt.repo;

import home.lflt.model.Lot;
import org.springframework.data.repository.CrudRepository;

public interface LotRepo extends CrudRepository<Lot, String> {
    Lot getByPortfolioIdAndSymbol(long portfolioId, String symbol);
}
