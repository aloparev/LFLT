package home.lflt.repo;

import home.lflt.model.Game;
import home.lflt.model.Portfolio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface GameRepo extends CrudRepository<Game, Long> {
//    Set<Game> getByUser(String user);

//    @Query(nativeQuery = true, value = "SELECT * FROM games g LEFT JOIN user_game ug on g.id = ug.game_id and ug.user_id = ?")
//    Set<Game> getByUserId(long userId);

    @Query("SELECT g FROM Game g INNER JOIN User u ON u.id = (:id)")
    @EntityGraph(value = "Game.portfolios", type = EntityGraph.EntityGraphType.LOAD)
    Set<Game> getByUserIdAndFetchPortfoliosEagerly(@Param("id") long userId);

//    @Query("SELECT g FROM Game g JOIN FETCH g.portfolios p WHERE g.user.id = (:uid)")
//    Set<Game> getByUserIdAndFetchPortfoliosEagerly(@Param("id") Long uid);


}
