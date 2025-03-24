package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dao.GameRepository;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.Roll;
import edu.cnm.deepdive.farkle.model.entity.Roll.Die;
import edu.cnm.deepdive.farkle.model.entity.State;
import edu.cnm.deepdive.farkle.model.entity.Turn;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final RandomGenerator rng;
  private final Map<List<Integer>, Integer>
      FARKLE_SCORES = Map.ofEntries(
      Map.entry(List.of(1), 100),
      Map.entry(List.of(5), 50),
      Map.entry(List.of(1, 1, 1), 1000),
      Map.entry(List.of(2, 2, 2), 200),
      Map.entry(List.of(3, 3, 3), 300),
      Map.entry(List.of(4, 4, 4), 400),
      Map.entry(List.of(5, 5, 5), 500),
      Map.entry(List.of(6, 6, 6), 600),
      Map.entry(List.of(1, 1, 1, 1), 1000),
      Map.entry(List.of(2, 2, 2, 2), 1000),
      Map.entry(List.of(3, 3, 3, 3), 1000),
      Map.entry(List.of(4, 4, 4, 4), 1000),
      Map.entry(List.of(5, 5, 5, 5), 1000),
      Map.entry(List.of(6, 6, 6, 6), 1000),
      Map.entry(List.of(1, 1, 1, 1, 1), 2000),
      Map.entry(List.of(2, 2, 2, 2, 2), 2000),
      Map.entry(List.of(3, 3, 3, 3, 3), 2000),
      Map.entry(List.of(4, 4, 4, 4, 4), 2000),
      Map.entry(List.of(5, 5, 5, 5, 5), 2000),
      Map.entry(List.of(6, 6, 6, 6, 6), 2000),
      Map.entry(List.of(1, 1, 1, 1, 1, 1), 3000),
      Map.entry(List.of(2, 2, 2, 2, 2, 2), 3000),
      Map.entry(List.of(3, 3, 3, 3, 3, 3), 3000),
      Map.entry(List.of(4, 4, 4, 4, 4, 4), 3000),
      Map.entry(List.of(5, 5, 5, 5, 5, 5), 3000),
      Map.entry(List.of(6, 6, 6, 6, 6, 6), 3000),
      Map.entry(List.of(1, 2, 3, 4, 5, 6), 1500),
      Map.entry(List.of(1, 1, 2, 2, 3, 3), 1500),
      // TODO: 3/24/25 Finish scoring list for combos
      Map.entry(List.of(2, 2, 3, 3, 4, 4), 1500),
      Map.entry(List.of(3, 3, 4, 4, 5, 5), 1500),
      Map.entry(List.of(4, 4, 5, 5, 6, 6), 1500)
  );

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
              startNewTurn(game, null);
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
  public Roll freezeOrContinue(RollAction action, UUID key, User user) {
    return gameRepository
        .findByPlayersContainsAndStateIn(user, EnumSet.of(State.IN_PLAY))
        .map((game) -> {
          Turn currentTurn = game.getCurrentTurn();
          Roll currentRoll = currentTurn.getRolls().getLast();
          if (!currentTurn.getUser().equals(user)) {
            throw new IllegalStateException();
          }
          List<Die> dice = new LinkedList<>(currentRoll.getDice());
          int groupNumber = 0;
          for (int[] group : action.getFrozenGroups()) {
            groupNumber++;
            int score = FARKLE_SCORES
                .getOrDefault(Arrays.stream(group).sorted().boxed().toList(), 0);
            if (score == 0) {
              throw new IllegalArgumentException();
            } else {
              currentRoll.setRollScore(currentRoll.getRollScore() + score);
            }
            VALUE_LOOP:
            for (int value : group) {
              for (Iterator<Die> iterator = dice.iterator(); iterator.hasNext(); ) {
                Die die = iterator.next();
                if (die.getValue() == value) {
                  die.setGroup(groupNumber);
                  iterator.remove();
                  continue VALUE_LOOP;
                }
              }
              throw new IllegalArgumentException();
            }
          }
          if (currentRoll.getRollScore() == 0) {
            currentRoll.setFarkle(true);
          }
          Roll nextRoll;
          if (currentRoll.isFarkle()
              || dice.isEmpty()
              || action.isFinished()) { // FIXME: 3/24/2025 add conditions to verify allowed to end turn
            startNewTurn(game, user);
            nextRoll = game.getCurrentTurn().getCurrentRoll();
          } else {
            nextRoll = addRoll(currentTurn, dice.size());
          }
          gameRepository
              .save(game);
          return nextRoll;
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
  public Game getGame(UUID gameKey, User user) {
    return gameRepository.findByPlayersContainsAndStateIn(user, EnumSet.allOf(State.class))
        .orElseThrow();
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

  private Roll addRoll(Turn turn, int numberOfDice) {
    Roll roll = new Roll();
    roll.setTurn(turn);
    roll.setNumberDice(numberOfDice);
    turn.getRolls().add(roll);
    List<Die> dice = IntStream.generate(() -> rng.nextInt(1, 7))
        .limit(numberOfDice)
        .sorted()
        .mapToObj((value) -> {
          Die die = new Die();
          die.setValue(value);
          return die;
        })
        .toList();
    roll.getDice().addAll(dice);
    // TODO: 3/24/25 Check if the dice just rolled is a farkle
    return roll;
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
