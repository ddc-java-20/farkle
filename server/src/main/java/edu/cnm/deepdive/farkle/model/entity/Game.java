package edu.cnm.deepdive.farkle.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "game_id", nullable = false)
  private Long Id;

  // Reference to the current player
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_profile_id", nullable = false)
  private User currentPlayer;

  // Reference to the current turn
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "turn_id", nullable = true)
  private Turn currentTurn;

  // Reference to the winner
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_profile_id", nullable = true, insertable = false, updatable = false)
  private User winner;

  // State of the game
  @Enumerated(EnumType.STRING)
  private State state;

  // List of turns
  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Turn> turns = new LinkedList<>();

  // Getters and Setters
  public Long getId() {
    return Id;
  }

  public User getCurrentPlayer() {
    return currentPlayer;
  }

  public Turn getCurrentTurn() {
    return currentTurn;
  }

  public void setCurrentTurn(Turn currentTurn) {
    this.currentTurn = currentTurn;
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

// TODO: 3/7/25 Correct players field & add getter/setter
  // @ManyToMany
//  private List<UserProfile> players;


}




