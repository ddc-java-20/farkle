package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.util.UUID;

public class User {
//Can be used to update profile

  @Expose(serialize = false)
  private UUID key;

  @Expose(serialize = false)
  private String displayName;


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
