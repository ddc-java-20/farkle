package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dao.GameRepository;
import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.State;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.EnumSet;
import java.util.List;
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
    // Do we need to do .findByUserProfile?
  }

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
