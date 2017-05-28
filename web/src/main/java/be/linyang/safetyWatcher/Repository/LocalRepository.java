package be.linyang.safetyWatcher.Repository;

import be.linyang.safetyWatcher.Model.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yanglin on 26/05/17.
 */
@Repository
public interface LocalRepository extends CrudRepository<Local, Long> {
    List<Local> findAll();
    Local findByName(String name);
}
