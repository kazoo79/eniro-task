package pl.pdebala.eniro.rpository;

import org.springframework.data.repository.CrudRepository;
import pl.pdebala.eniro.rpository.entity.SearchHistory;

/**
 * Created by pdebala on 2016-10-08.
 */
public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Integer> {
}
