package home.lflt.repo;

import home.lflt.model.Portfolio;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PortfolioRepo extends CrudRepository<Portfolio, Long> {
    Set<Portfolio> getByTypeNot(String type);
}
