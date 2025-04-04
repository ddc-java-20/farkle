package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.util.UUID;

public class User {
//do I add @JsonProperty ?
  @Expose(serialize = false)
  private UUID key;

  @Expose
  private String displayName;
//Can be used to update profile


  public UUID getKey() {
    return key;
  }
  public void setKey(UUID key) {
    this.key = key;
  }

  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
}
