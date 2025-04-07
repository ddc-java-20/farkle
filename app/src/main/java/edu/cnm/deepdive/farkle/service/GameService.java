package edu.cnm.deepdive.farkle.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.farkle.BuildConfig;
import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameService {

  private static final String API_BASE_URL = BuildConfig.API_BASE_URL; // Base URL from app config.
  private static GameService instance;
  private static FarkleApiProxy farkleApi = null;

  private GameService() {
    Gson gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation() // Respect GSON Expose annotations.
        .create();

    Retrofit retrofit = new Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    farkleApi = retrofit.create(FarkleApiProxy.class);
  }

  /**
   * Singleton instance of the service layer.
   */
  public static synchronized GameService getInstance() {
    if (instance == null) {
      instance = new GameService();
    }
    return instance;
  }

  /**
   * Retrieves game data from the backend.
   *
   * @param gameId Unique ID of the game.
   * @return Call object for obtaining the game asynchronously.
   */
  public Call<Game> getGame(UUID gameId) {
    return farkleApi.getGame(gameId);
  }

  public static Call<Game> freezeOrContinue(UUID gameKey, UUID userId, RollAction rollAction) {
    return farkleApi.freezeOrContinue(gameKey, userId, rollAction);
  }

}

