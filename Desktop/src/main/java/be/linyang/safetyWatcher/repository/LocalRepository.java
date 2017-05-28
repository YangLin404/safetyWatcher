package be.linyang.safetyWatcher.repository;

import be.linyang.safetyWatcher.model.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yanglin on 28/05/17.
 */

@Repository
public interface LocalRepository extends CrudRepository<Local,Long> {
    List<Local> findAll();
    Local findByName(String name);
}
