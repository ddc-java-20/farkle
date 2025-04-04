package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.time.Instant;
import java.util.List;

public class Roll {

  @Expose(serialize = false)
  private int rollScore;

  @Expose(serialize = false)
  private boolean farkle;

  @Expose(serialize = false)
  private Instant rolledAt;

  @Expose(serialize = false)
  private int numberDice;

  @Expose(serialize = false)
  private List<Die> dice;

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

  public Instant getRolledAt() {
    return rolledAt;
  }
  public void setRolledAt(Instant rolledAt) {
    this.rolledAt = rolledAt;
  }

  public int getNumberDice() {
    return numberDice;
  }
  public void setNumberDice(int numberDice) {
    this.numberDice = numberDice;
  }

  public List<Die> getDice() {
    return dice;
  }
  public void setDice(List<Die> dice) {
    this.dice = dice;
  }
  
}