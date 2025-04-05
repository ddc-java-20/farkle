package edu.cnm.deepdive.farkle.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.farkle.controller.GameFragment;
import edu.cnm.deepdive.farkle.databinding.FragmentGameBinding;
import javax.inject.Inject;

public class GameViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final LiveData<Boolean> quitGame;

  @Inject
  public GameViewModel(LiveData<Boolean> quitGame) {
    this.quitGame = quitGame;
  }

  public LiveData<Boolean> getQuitGame() {
    return quitGame;

  }
}
