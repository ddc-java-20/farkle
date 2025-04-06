package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;

public class GamePlayerKey {

  @Expose(serialize = false)
  private long gameId;

  @Expose(serialize = false)
  private long userProfileId;

  public long getGameId() {
    return gameId;
  }
  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public long getUserProfileId() {
    return userProfileId;
  }
  public void setUserProfileId(long userProfileId) {
    this.userProfileId = userProfileId;
  }
}
