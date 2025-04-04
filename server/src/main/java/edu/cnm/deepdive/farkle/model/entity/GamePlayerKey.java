package edu.cnm.deepdive.farkle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Embeddable
public class GamePlayerKey implements Serializable {

  @Column(name = "game_id")
  private long gameId;

  @Column(name = "user_profile_id")
  private long userProfileId;

  public long getGameId() {
    return gameId;
  }

  public long getUserProfileId() {
    return userProfileId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public void setUserProfileId(long userProfileId) {
    this.userProfileId = userProfileId;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof GamePlayerKey that)) return false;
    return (gameId == that.gameId) &&
        (userProfileId == that.userProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameId, userProfileId);
  }
}
