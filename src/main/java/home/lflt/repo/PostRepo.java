package home.lflt.repo;

import home.lflt.model.Feedback;
import home.lflt.model.PagePost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<PagePost, Long> {
    List<PagePost> getAllByPageOrderByTstamp(String page);
//    PagePost getByPage(String page);
}
