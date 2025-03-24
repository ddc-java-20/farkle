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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final RandomGenerator rng;

  public GameService(GameRepository gameRepository, RandomGenerator rng) {
    this.gameRepository = gameRepository;
    this.rng = rng;
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
              game.setCurrentPlayer(startNewTurn(game, null)); // TODO: 3/24/25 Evaluate the neccessity
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
    gameRepository
        .findByPlayersContainsAndStateIn(user, EnumSet.of(State.IN_PLAY))
        .map((game) -> {
          Turn currentTurn = game.getCurrentTurn();
          Roll currentRoll = currentTurn.getRolls().getFirst();
          if (!currentTurn.getUser().equals(user)) {
            throw new IllegalStateException();
          }
          List<Die> dice = new LinkedList<>(currentRoll.getDice());
          for(int[] group : action.getFrozenGroups()) {
            // TODO: 3/24/25 Check the int[] to make sure it is valid for scoring and get score
            //  (look into using map that takes list as a key)
            int score = 0; // FIXME: 3/24/25 use the score returned by the scoring table
            VALUE_LOOP:
            for (int faceValue : group) {
              for (Iterator<Die> iterator = dice.iterator(); iterator.hasNext(); ) {
                if (iterator.next().getValue() == faceValue) {
                  iterator.remove();
                  continue VALUE_LOOP;
                }
              }
              throw new IllegalArgumentException();
            }
          }
          if (dice.isEmpty() || action.isFinished()) {
            // FIXME: 3/24/25 add conditions to verify that the user is allowed to end turn
            User nextPlayer = startNewTurn(game, user);
            game.setCurrentPlayer(nextPlayer); // TODO: 3/24/25 Evaluate whether we really need this.
          } else {
            addRoll(currentTurn, dice.size());
          }
          return gameRepository.save(game);
        })
        .orElseThrow();

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

  private User startNewTurn(Game game, User currentPlayer) {
    Turn turn = new Turn();
    User nextPlayer;
    List<User> players = game.getPlayers();
    if (currentPlayer == null) {
      nextPlayer = players.getFirst();
    } else {
      int position = players.indexOf(currentPlayer);
      nextPlayer = players.get((position + 1) % players.size());
    }
    turn.setUser(nextPlayer);
    turn.setGame(game);
    game.getTurns().add(turn);
    addRoll(turn, 6);
    return nextPlayer;
  }

  private void addRoll(Turn turn, int numberOfDice) {
    Roll roll = new Roll();
    roll.setTurn(turn);
    roll.setNumberDice(numberOfDice);
    turn.getRolls().add(roll);
    for (int i = 0; i < roll.getNumberDice(); i++) {
      Die die = new Die();
      die.setGroup(0);
      die.setValue(rng.nextInt(1, 7));
      roll.getDice().add(die);
    }
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
