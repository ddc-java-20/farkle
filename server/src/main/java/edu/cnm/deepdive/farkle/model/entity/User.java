package edu.cnm.deepdive.farkle.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_profile")
public class User {

  // Primary key  field
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_profile_id", nullable = false)
  private Long Id;

  // Unique identifier for the  user
  @Column(nullable = false, updatable = false, unique = true)
  @JsonProperty(value = "key", access = JsonProperty.Access.READ_ONLY)
  private UUID externalKey;

  // Authentication key for the user
  @Column(nullable = true)
  private String authKey;

  // Display name for the user
  @Column(nullable = true)
  private String displayName;


  // Getter for primary key
  public Long getId() {
    return Id;
  }

  // Getter and setter for the authentication  key
  public String getAuthKey() {
    return authKey;
  }

  public void setAuthKey(String authCode) {
    this.authKey = authCode;
  }

  // Getter and setter for the display name
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String gameName) {
    this.displayName = gameName;
  }

  // Method to generate a unique external key before persisting the entity
  @PrePersist
  void generateFieldValues() {
    externalKey = UUID.randomUUID();
  }


}