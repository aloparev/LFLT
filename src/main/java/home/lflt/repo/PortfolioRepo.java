package home.lflt.repo;

import home.lflt.model.Portfolio;
import home.lflt.model.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PortfolioRepo extends CrudRepository<Portfolio, Long> {

    //public
    Set<Portfolio> getByUserIsNull();

    //private
    Set<Portfolio> getByUserIsNotNull();
    Set<Portfolio> getByUserId(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM portfolios WHERE user_id = ? AND game_id IS NULL")
    Set<Portfolio> getByUserIdAndGameIsNull(long id);

    Set<Portfolio> getByType(String type);
    Portfolio getById(long id);
}
