package edu.cnm.deepdive.farkle.viewmodel;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import edu.cnm.deepdive.farkle.service.GameService;


public class GameViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final MutableLiveData<Game> game = new MutableLiveData<>();
  private final MutableLiveData<String> error = new MutableLiveData<>();
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  private LiveData<Boolean> quitGame;

  public LiveData<Game> getGame() {
    return game;
  }

  public LiveData<String> getError() {
    return error;
  }

  public void submitRollChoice(UUID gameKey, UUID userId, int[][] frozenGroups, boolean finished) {
    executor.execute(() -> {
      RollAction rollAction = new RollAction(frozenGroups, finished);
      GameService.freezeOrContinue(gameKey, userId, rollAction);
      });
    }

  public LiveData<Boolean> getQuitGame() {
    return quitGame;
  }
}
