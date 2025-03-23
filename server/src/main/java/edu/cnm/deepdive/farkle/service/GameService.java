package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dao.GameRepository;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.State;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;

  public GameService(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  @Override
  public Game startOrJoin(User user) {
    return gameRepository
        .findByPlayersContainsAndStateIn(user, EnumSet.of(State.PRE_GAME, State.IN_PLAY))
        .orElseGet(() -> gameRepository
            .findByState(State.PRE_GAME)
            .map((game) -> {
              game.setState(State.IN_PLAY);
              List<User> players = game.getPlayers();
              players.add(user);
              game.setCurrentPlayer(players.getFirst());
              // TODO: 3/20/25 Need to figure out how to create turn and roll dice first time.
              return gameRepository.save(game);
            })
            .orElseGet(() -> {
              Game game = new Game();
              game.setState(State.PRE_GAME);
              game.getPlayers().add(user);
              return gameRepository.save(game);
            })
        );
  }

  // Query game object with key and User
  @Override
  public void freezeOrContinue(RollAction action, UUID key, User user) {
//Use`gameRepository.findByExternalKey(key)` to find the game by its external key
    //I created a method in gameRepository to findByExternalKey
    Optional<Game> gameOptional = gameRepository.findByExternalKey(key);
    if (gameOptional.isEmpty()) {
      throw new IllegalArgumentException("Game not found with key: " + key);
    }
  //Verify the user is part of the game's players list
  Game game = gameOptional.get();
      if (!game.getPlayers().contains(user)) {
        throw new IllegalArgumentException("User not part of this game");
   }
}

    // TODO: 3/21/25 Query game object with key and User
    // TODO: 3/21/25 Look at most recent turn and most recent roll in game object
    // TODO: 3/21/25 Validate whether the user IS the current user in turn
    // TODO: 3/21/25 Validate what the user wants to do is valid according to the most recent roll
    // TODO: 3/21/25 Compute the score that results from this action
    // TODO: 3/21/25 Update the turn and roll entity instances and write to the database
    // TODO: 3/21/25 Check if this turn puts the game in 'last round' state
    // TODO: 3/21/25 If turn, advance to the next turn and user


  @Override
  public Game getGame(User user) {
    return null;
  }

  @Override
  public Game getGameState(User user) {
    return null;
  }

  @Override
  public Game setGameState(State state) {
    return null;
  }

  @Override
  public Game getCurrentPlayer() {
    return null;
  }
}
