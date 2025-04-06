package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.time.Instant;

public class GamePlayer {

  @Expose(serialize = false)
  private Instant joinedAt;

  @Expose(serialize = false)
  private int score;

  @Expose(serialize = false)
  private Long userId;


  @Expose(serialize = false)
  private Long lastTurnId;

  public Instant getJoinedAt() {
    return joinedAt;
  }
  public void setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
  }

  public int getScore() {
    return score;
  }
  public void setScore(int score) {
    this.score = score;
  }

  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getLastTurnId() {
    return lastTurnId;
  }
  public void setLastTurnId(Long lastTurnId) {
    this.lastTurnId = lastTurnId;
  }

}
