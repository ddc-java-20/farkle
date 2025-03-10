package edu.cnm.deepdive.farkle.model.dao;

import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long>{

  List<Game> findByIdIn(Collection<Long> id);

//  List<Game> getAllByActiveOrderByIdDesc(boolean active);

  // List<Game> getAllByWinnerid


  // TODO: 3/9/2025 add spring data queries as necessary
}
