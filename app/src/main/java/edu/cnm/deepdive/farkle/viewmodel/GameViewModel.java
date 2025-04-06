package edu.cnm.deepdive.farkle.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.farkle.controller.GameFragment;
import edu.cnm.deepdive.farkle.databinding.FragmentGameBinding;
import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import edu.cnm.deepdive.farkle.model.dto.RollChoice;
import edu.cnm.deepdive.farkle.service.GameService;



public class GameViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final MutableLiveData<Game> game = new MutableLiveData<>();
  private final MutableLiveData<String> error = new MutableLiveData<>();
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  private final GameService gameService;
  private LiveData<Boolean> quitGame;

  public GameViewModel() {
    this.gameService = new GameService(); // Service to handle API requests.
  }

  public LiveData<String> getUserId() {
    return userId;
  }
  public LiveData<Game> getGame() {
    return game;
  }

  public LiveData<String> getError() {
    return error;
  }

  @Inject
  public GameViewModel(LiveData<Boolean> quitGame, GameService gameService) {
    this.gameService = gameService;
    this.quitGame = quitGame;
  }

  public void submitRollChoice(UUID gameKey, int[][] frozenGroups, boolean finished, User userId
  ) {
    executor.execute(() -> {
      RollAction rollAction = new RollAction();
      rollAction.setFrozenGroups(frozenGroups);
      rollAction.setFinished(finished);

      Game updatedGame = gameService.freezeOrContinue(rollAction, gameKey, userId);
        game.postValue(updatedGame); // Update LiveData with the latest game state.
      };
    }


  public LiveData<Boolean> getQuitGame() {
    return quitGame;
  }
}
