package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.time.Instant;
import java.util.List;

public class Roll {

  @Expose(serialize = false)
  private long id;

  @Expose(serialize = false)
  private int rollScore;

  @Expose(serialize = false)
  private boolean farkle;

  @Expose(serialize = false)
  private int numberDice;

  @Expose(serialize = false)
  private Instant timestamp;

  @Expose(serialize = false)
  private List<Die> dice;


  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
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

  public Instant getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public List<Die> getDice() {
    return dice;
  }

  public void setDice(List<Die> dice) {
    this.dice = dice;
  }
}
