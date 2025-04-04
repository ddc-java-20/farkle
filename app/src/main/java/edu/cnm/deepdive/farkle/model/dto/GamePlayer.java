package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.time.Instant;

public class GamePlayer {

  @Expose(serialize = false)
  private Instant timestamp;

  @Expose(serialize = false)
  private User user;

  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }

  public Instant getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

}
