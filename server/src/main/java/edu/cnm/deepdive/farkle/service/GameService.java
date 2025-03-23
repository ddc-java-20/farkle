package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dao.GameRepository;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.Roll;
import edu.cnm.deepdive.farkle.model.entity.Roll.Die;
import edu.cnm.deepdive.farkle.model.entity.State;
import edu.cnm.deepdive.farkle.model.entity.Turn;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
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

  @Override
  public void freezeOrContinue(RollAction action, UUID key, User user) {
      return gameRepository
          .findByPlayersContainsAndStateIn(user, EnumSet.of(State.IN_PLAY))
          .map((game) -> {
            Turn currentTurn = game.getCurrentTurn();
            Roll currentRoll = currentTurn.getRolls().getFirst();
            if (!currentTurn.getUser().equals(user)) {
              throw new IllegalStateException();
            }
            List<Die> dice = new LinkedList<>(currentRoll.getDice());
            //create different list that has all dice rolled, then remove items from the list, as user passes them in, in the action
            // watch out for "remove" when taking out dice from list
          });

    // TOD 3/21/25 Query game object with key and User
    // TOD 3/21/25 Look at most recent turn and most recent roll in game object
    // TOD 3/21/25 Validate whether the user IS the current user in turn
    // TODO: 3/21/25 Validate what the user wants to do is valid according to the most recent roll
    // TODO: 3/21/25 Compute the score that results from this action
    // TODO: 3/21/25 Update the turn and roll entity instances and write to the database
    // TODO: 3/21/25 Check if this turn puts the game in 'last round' state
    // TODO: 3/21/25 If turn, advance to the next turn and user
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

//  private Game setCurrentPlayer() { return CurrentPlayer }
//  cycle through players via turn order,


/*  create turn
    if (rollAgain = true) {
      create roll
      (userChoiceListener)
      only turn choices from CurrentPlayer are accepted

      set rollAgain to true|false

    } else {
      end turn

*/
