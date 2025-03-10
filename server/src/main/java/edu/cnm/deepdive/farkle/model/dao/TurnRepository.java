package edu.cnm.deepdive.farkle.model.dao;

import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.Turn;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnRepository extends JpaRepository<Turn, Long> {

  Optional<List<Turn>> findByGame(Game game);

//  Optional<List<Turn>> findByUser(User user);

  // TODO: 3/9/2025 add spring data queries as necessary
}
