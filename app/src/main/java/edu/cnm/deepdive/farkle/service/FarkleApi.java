package edu.cnm.deepdive.farkle.service;

import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.model.dto.RollAction;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FarkleApi {

  /**
   * Retrieves a game from the backend service, with optional additional parameters for state.
   * @param gameId Unique identifier for the game.
   * @return Asynchronous call containing game data.
   */
  @GET("games/{gameId}")
  Call<Game> getGame(@Path("gameId") UUID gameId);

  /**
   * Submits a roll action to freeze dice or continue the turn.
   *
   * @param gameKey Unique game identifier.
   * @param userId User identifier.
   * @param action Roll action to submit, including frozen dice and turn finishing data.
   * @return Asynchronous call containing updated game state.
   */
  @POST("games/{gameKey}/action")
  Call<Game> freezeOrContinue(
      @Path("gameKey") UUID gameKey,       // Pass the gameKey as part of the URL.
      @Query("userId") UUID userId,      // Pass the userId as a query parameter.
      @Body RollAction action              // Pass RollAction as the body of the request.
  );


}
