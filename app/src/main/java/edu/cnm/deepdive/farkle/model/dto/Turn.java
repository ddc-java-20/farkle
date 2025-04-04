package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.time.Instant;
import java.util.UUID;

public class Turn {

  @Expose(serialize = false)
  private UUID key;

  @Expose(serialize = false)
  private Instant startTime;

  @Expose(serialize = false)
  private User user_id;

  @Expose(serialize = false)
  private boolean finished;

  //was this moved?
  @Expose(serialize = false)
  private boolean farkle;

  @Expose(serialize = false)
  private Roll currentRoll;

  @Expose(serialize = false)
  private int turnScore;

  public UUID getKey() {
    return key;
  }
  public void setKey(UUID key) {
    this.key = key;
  }

  public Instant getStartTime() {
    return startTime;
  }
  public void setStartTime(Instant startTime) {
    this.startTime = startTime;
  }

  public User getUser_id() {
    return user_id;
  }
  public void setUser_id(User user_id) {
    this.user_id = user_id;
  }

  public boolean isFinished() {
    return finished;
  }
  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  public boolean isFarkle() {
    return farkle;
  }
  public void setFarkle(boolean farkle) {
    this.farkle = farkle;
  }

  public Roll getCurrentRoll() {
    return currentRoll;
  }
  public void setCurrentRoll(Roll currentRoll) {
    this.currentRoll = currentRoll;
  }

  public int getTurnScore() {
    return turnScore;
  }
  public void setTurnScore(int turnScore) {
    this.turnScore = turnScore;
  }

}