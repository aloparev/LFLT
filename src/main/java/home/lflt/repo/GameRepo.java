package home.lflt.repo;

import home.lflt.model.Game;
import home.lflt.model.Portfolio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GameRepo extends CrudRepository<Game, Long> {
//    Set<Game> getByUser(String user);

    @Query(nativeQuery = true, value = "SELECT * FROM games g LEFT JOIN user_game ug on g.id = ug.game_id and ug.user_id = ?")
//    @Query("SELECT g FROM games g left join user_game ug on g.id = ug.game_id and ug.user_id = userId")
//    @Query("Game g where g.id in user_game and user_game.user_id = userId")
    Set<Game> getByUserId(long userId);

//    Set<Portfolio> getByType(String type);
//    Portfolio getById(long id);
}
