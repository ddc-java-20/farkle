package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dto.RollAction;
import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.model.entity.Roll;
import edu.cnm.deepdive.farkle.model.entity.State;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.UUID;

public interface AbstractGameService {

  Game startOrJoin(User user);

  Game getGame(UUID gameKey, User user);

  Game getGameState(User user);

  Game setGameState(State state);

  Game getCurrentPlayer();

  Roll freezeOrContinue(RollAction action, UUID key, User user);

}
