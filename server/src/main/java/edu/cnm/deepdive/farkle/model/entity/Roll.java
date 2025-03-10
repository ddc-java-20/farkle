package edu.cnm.deepdive.farkle.model.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Roll {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "roll_id")
  private Long Id;

  // Score for the roll
  @Column(nullable = true)
  private Integer rollScore;

  // Flag indicating if the roll is a farkle
  @Column(nullable = false, updatable = false)
  private boolean farkle;

  @Column(nullable = false, updatable = false)
  private int numberDice;

  // List of dice values
  @ElementCollection
  @CollectionTable(name = "roll_die", joinColumns = @JoinColumn(name = "roll_id"))
  @AttributeOverrides(
      @AttributeOverride(name=  "value", column = @Column(name = "face_value", nullable = false, updatable = false))
  )
  private final List<Die> dice = new LinkedList<>();

  //  creating many-to-one relationship with annotations
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "turn_id", nullable = false)
  private Turn turn;

  //Getters and Setters
  public Turn getTurn() {
    return turn;
  }

  public void setTurn(Turn turn) {
    this.turn = turn;
  }

  public Long getId() {
    return Id;
  }

  public Integer getRollScore() {
    return rollScore;
  }

  public void setRollScore(Integer rollScore) {
    this.rollScore = rollScore;
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

  @Embeddable
  public static class Die {

    private int value;

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }
  }
}