package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.State;
import edu.cnm.deepdive.farkle.model.entity.User;

public interface AbstractGameService {

  Game startOrJoin(User user);

  Game getGame(User user);

  Game getGameState(User user);

  Game setGameState(State state);

  Game getCurrentPlayer();


}
