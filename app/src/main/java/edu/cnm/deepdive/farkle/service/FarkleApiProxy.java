package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import io.reactivex.rxjava3.core.Single;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FarkleApiProxy {

  /**
   * Retrieves a game from the backend service, with optional additional parameters for state.
   * @param gameId Unique identifier for the game.
   * @return Asynchronous call containing game data.
   */
  @GET("games/{gameId}")
  Single<Game> getGame(@Path("gameId") UUID gameId);

  /**
   * Submits a roll action to freeze dice or continue the turn.
   *
   * @param gameKey Unique game identifier.
   * @param userId User identifier.
   * @param action Roll action to submit, including frozen dice and turn finishing data.
   * @return Asynchronous call containing updated game state.
   */
  @POST("games/{gameKey}/action")
  Single<Boolean> freezeOrContinue(
      @Path("gameKey") UUID gameKey,       // Pass the gameKey as part of the URL.
      @Body RollAction action              // Pass RollAction as the body of the request.
  );

  @POST("games")
  Single<Game> startOrJoin()


}
