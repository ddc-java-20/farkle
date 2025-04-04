package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.time.Instant;

public class Roll {

  @Expose(serialize = false)
  private long roll_id;

  @Expose(serialize = false)
  private int rollScore;

  @Expose(serialize = false)
  private boolean farkle;

  @Expose(serialize = false)
  private int numberDice;

  @Expose(serialize = false)
  private Turn turn;

  @Expose(serialize = false)
  private Instant timestamp;


  public long getRoll_id() {
    return roll_id;
  }
  public void setRoll_id(long roll_id) {
    this.roll_id = roll_id;
  }

  public int getRollScore() {
    return rollScore;
  }
  public void setRollScore(int rollScore) {
    this.rollScore = rollScore;
  }

  public boolean isFarkle() {
    return farkle;
  }
  public void setFarkle(boolean farkle) {
    this.farkle = farkle;
  }

  public int getNumberDice() {
    return numberDice;
  }
  public void setNumberDice(int numberDice) {
    this.numberDice = numberDice;
  }

  public Turn getTurn() {
    return turn;
  }
  public void setTurn(Turn turn) {
    this.turn = turn;
  }

  public Instant getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

}
