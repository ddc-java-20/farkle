package edu.cnm.deepdive.farkle.model.dao;

import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.State;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {

//  List<Game> findByUser(User user);

  Optional<Game> findByPlayersContainsAndStateIn(User player, Set<State> states);

  Optional<Game> findByState(State state);

  Optional<Game> findByExternalKey(UUID externalKey);

  @Query("SELECT game FROM Game game JOIN game.players player WHERE game.externalKey = :externalKey AND player = :user")
  Optional<Game> findByExternalKeyAndUser(@Param("externalKey") UUID externalKey, @Param("user") User user);


}