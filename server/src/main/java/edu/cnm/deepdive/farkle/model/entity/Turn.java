package edu.cnm.deepdive.farkle.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Turn {

  @Id
  @GeneratedValue
  @Column(name = "turn_id")
  @JsonIgnore
  private long id;

  @Column(unique = true, nullable = false, updatable = false)
  @JsonProperty(value = "key", access = Access.READ_ONLY)
  private UUID externalKey;

  @Column(nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private int turnScore;

  // TODO: 3/20/25 Add player who took turn
  // TODO: 3/20/25 Consider adding timestamp

  @Column(nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private boolean finished;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id", nullable = false, updatable = false)
  @JsonIgnore
  private Game game;

  private long getId() {
    return id;
  }

  public UUID getExternalKey() {
    return externalKey;
  }

  public int getTurnScore() {
    return turnScore;
  }

  public void setTurnScore(int turnScore) {
    this.turnScore = turnScore;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }


  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @PrePersist
  void generateFieldValues() {
    externalKey = UUID.randomUUID();
  }

}
