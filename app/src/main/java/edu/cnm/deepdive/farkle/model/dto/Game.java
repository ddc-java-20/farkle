package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.util.List;
import java.util.UUID;

public class Game {

  @Expose(serialize = false)
  private UUID key;

  @Expose(serialize = false)
  private User winner;

  @Expose(serialize = false)
  private State state;

  @Expose(serialize = false)
  private List<GamePlayer> players;

  @Expose(serialize = false)
  private Turn currentTurn;

  public UUID getKey() {
    return key;
  }
  public void setKey(UUID key) {
    this.key = key;
  }

  public User getWinner() {
    return winner;
  }
  public void setWinner(User winner) {
    this.winner = winner;
  }

  public State getState() {
    return state;
  }
  public void setState(State state) {
    this.state = state;
  }

  public List<GamePlayer> getPlayers() {
    return players;
  }
  public void setPlayers(List<GamePlayer> players) {
    this.players = players;
  }

  public Turn getCurrentTurn() {
    return currentTurn;
  }
  public void setCurrentTurn(Turn currentTurn) {
    this.currentTurn = currentTurn;
  }

}
