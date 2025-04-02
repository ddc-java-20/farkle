package edu.cnm.deepdive.farkle.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.UniqueConstraint;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

  @Id
  @GeneratedValue
  @Column(name = "game_id", nullable = false)
  @JsonIgnore
  private long id;

  @Column(nullable = false, updatable = false, unique = true)
  @JsonProperty(value = "key", access = Access.READ_ONLY)
  private UUID externalKey;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "winner_id", nullable = true, insertable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private User winner;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private State state;

  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("startTime ASC")
  @JsonIgnore
  private final List<Turn> turns = new LinkedList<>();

  // TODO: 4/2/25 Replace this with a List<GamePlayer> (OneToMany)
  //  Order the list by creation timestamp of GamePlayer.

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
  @JoinTable(name = "game_player",
      joinColumns = @JoinColumn(name = "game_id"),
      inverseJoinColumns = @JoinColumn(name = "player_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "player_id"})
  )
  @OrderBy("externalKey")
  @JsonProperty(access = Access.READ_ONLY)
  private final List<User> players = new LinkedList<>();

  public long getId() {
    return id;
  }

  public UUID getExternalKey() {
    return externalKey;
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

  public List<Turn> getTurns() {
    return turns;
  }

  public List<User> getPlayers() {
    return players;
  }

  public Turn getCurrentTurn(){
    return turns.isEmpty() ? null : turns.getLast();
  }

  public int getRollCount() {
    return getTurns()
        .stream()
        .mapToInt((turn) -> turn.getRolls().size())
        .sum();
  }

  @PrePersist
  void generateFieldValues() {
    externalKey = UUID.randomUUID();
  }
}




