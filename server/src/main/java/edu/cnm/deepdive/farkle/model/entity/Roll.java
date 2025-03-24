package edu.cnm.deepdive.farkle.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Roll {

  @Id
  @GeneratedValue
  @Column(name = "roll_id")
  private long Id;

  @Column(nullable = true)
  private int rollScore;

  @Column(nullable = false, updatable = true)
  private boolean farkle;

  @Column(nullable = false, updatable = false)
  private int numberDice;

  @JoinColumn(name = "turn_id", nullable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Turn turn;

  @Column(nullable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant timestamp;

  @ElementCollection
  @CollectionTable(name = "roll_die", joinColumns = @JoinColumn(name = "roll_id"))
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "face_value", nullable = false, updatable = false))
  )
  private final List<Die> dice = new LinkedList<>();

  public Long getId() {
    return Id;
  }

  public Integer getRollScore() {
    return rollScore;
  }

  public void setRollScore(Integer rollScore) {
    this.rollScore = rollScore;
  }

  public boolean isFarkle() {
    return farkle;
  }

  public void setFarkle(boolean farkle) {
    this.farkle = farkle;
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

  public Turn getTurn() {
    return turn;
  }

  public void setTurn(Turn turn) {
    this.turn = turn;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  @Embeddable
  public static class Die {

    private int value;

    private int group;

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    public int getGroup() {
      return group;
    }

    public void setGroup(int group) {
      this.group = group;
    }

    @Override
    public int hashCode() {
      return Integer.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
      boolean result;
      if(this == obj) {
        result = true;
      } else if(obj instanceof Die other) {
        result = other.value == this.value;
      } else {
        result = false;
      }
      return super.equals(obj);
    }
  }

}