package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dao.GameRepository;
import edu.cnm.deepdive.farkle.model.dao.RollRepository;
import edu.cnm.deepdive.farkle.model.dao.TurnRepository;
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
  //I reviewed the TODOs Aaron wrote that were left pending on Friday
  //so I added these 2 fields to update rolls and turns
  private final RollRepository rollRepository;
  private final TurnRepository turnRepository;

  public GameService(GameRepository gameRepository, RollRepository rollRepository,
      TurnRepository turnRepository) {
    this.gameRepository = gameRepository;
    //with alt + insert I injected the constructors for the 2 fields I added above
    this.rollRepository = rollRepository;
    this.turnRepository = turnRepository;
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
          //Aaron's code from Friday retrieves recent turn n roll
              Turn currentTurn = game.getCurrentTurn();
         //Aaron's code to validate if user is the player for current turn
              Roll currentRoll = currentTurn.getRolls().getFirst();
              if (!currentTurn.getUser().equals(user)) {
                throw new IllegalStateException();
              }
        //To validate the dice selected, I used the list that Aaron started and called it "actionDice" below,
        // to work with the dice the player picked during their turn. It's a copy of getDice from Roll entity.
        //This keeps the original roll data untouched for scoring and other parts of the game.
              List<Die> actionDcie = new LinkedList<>(currentRoll.getDice()); //LinkedList removes the values
        // from actionDice that player chose to freeze
        //the remaining dice in actionDice reflect the dice that have not been frozen
              action.getFrozenGroups() //this is a method from RollAction DTO providing array of int arrays
        // of groups of dice player chose to freeze. This is actual user input for the turn
                  .forEach(group -> { //iterates through each group in frozenGroups array above
                    for (int value : group) { //iterates through individual dice values in current frozen group
//      //validate each dice value in the group against the available dice in actionDice.
//      If a die value is not present in actionDice, the group is invalid, and the program will throw an exception.
                      if (!actionDice.removeIf(die -> die.getValue() == value)) {
                        throw new IllegalStateException();
                      }
                    }
                  });

  }
          // create different list that has all dice rolled, then remove items from the list, as user passes them in, in the action
          // watch out for "remove" when taking out dice from list

          // Additional logic for validating the action, computing the score, and updating entities




  // TOD 3/21/25 Query game object with key and User 1
  // TOD 3/21/25 Look at most recent turn and most recent roll in game object 2
  // TOD 3/21/25 Validate whether the user IS the current user in turn 3
  // TODO: 3/21/25 Validate what the user wants to do is valid according to the most recent roll 4
  // TODO: 3/21/25 Compute the score that results from this action 5
  // TODO: 3/21/25 Update the turn and roll entity instances and write to the database 6
  // TODO: 3/21/25 Check if this turn puts the game in 'last round' state 7
  // TODO: 3/21/25 If turn, advance to the next turn and user 8

    @Override
    public Game getGame (User user){
      return null;
    }

    @Override
    public Game getGameState (User user){
      return null;
    }

    @Override
    public Game setGameState (State state){
      return null;
    }

    @Override
    public Game getCurrentPlayer () {
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
