package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;

public class RollAction {

  @Expose
  private int[][] frozenGroups; // Frozen dice groups.

  @Expose
  private boolean finished; // Whether the turn is finished.

  public RollAction(int[][] frozenGroups, boolean finished) {
    this.frozenGroups = frozenGroups;
    this.finished = finished;
  }

  public int[][] getFrozenGroups() {
    return frozenGroups;
  }

  public void setFrozenGroups(int[][] frozenGroups) {
    this.frozenGroups = frozenGroups;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }
}
