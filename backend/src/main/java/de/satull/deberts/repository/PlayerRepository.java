package de.satull.deberts.repository;

import de.satull.deberts.model.db.Players;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Players, Long> {

}
