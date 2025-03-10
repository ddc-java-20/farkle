package edu.cnm.deepdive.farkle.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Turn {

  // Primary key field
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "turn_id")
  private Long Id;

  // Score for the turn
  @Column(nullable = false)
  private Integer turnScore;

  // Flag indicating if the turn is finished
  @Column(nullable = false)
  private Boolean finished;

  // Reference to game entity
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id", nullable = false, updatable = false)
  private Game game;

  //  reference to roll entity
  @OneToMany(mappedBy = "turn", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Roll> rolls = new LinkedList<>();

  // Getters and Setters
  public List<Roll> getRolls() {
    return rolls;
  }

  public void setRolls(List<Roll> rolls) {
    this.rolls = rolls;
  }

  private Long getId() {
    return Id;
  }

  public Integer getTurnScore() {
    return turnScore;
  }

  public void setTurnScore(Integer turnScore) {
    this.turnScore = turnScore;
  }


  public Boolean getFinished() {
    return finished;
  }

  public void setFinished(Boolean finished) {
    this.finished = finished;
  }


  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }



}
