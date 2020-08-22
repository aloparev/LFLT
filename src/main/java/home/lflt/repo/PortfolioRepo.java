package home.lflt.repo;

import home.lflt.model.Portfolio;
import home.lflt.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PortfolioRepo extends CrudRepository<Portfolio, Long> {
    Set<Portfolio> getByTypeNot(String type);
    Set<Portfolio> getByType(String type);
    Portfolio getById(long id);
}
