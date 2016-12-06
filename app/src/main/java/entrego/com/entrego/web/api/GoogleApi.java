package entrego.com.entrego.web.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bertalt on 03.08.16.
 */
public interface GoogleApi {

    public static String BASE_URL = "https://maps.googleapis.com";

    @GET("/maps/api/directions/json")
    Call<JsonElement> getTrackFromGoogle(@Query("origin") String from, @Query("destination") String to,
                                         @Query("mode") String mode,
                                         @Query("waypoints") String waypoints,
                                         @Query("key") String key);
    @GET("/maps/api/directions/json")
    Call<JsonElement> getTrackFromGoogle(@Query("origin") String from,
                                         @Query("destination") String to,
                                         @Query("mode") String mode,
                                         @Query("key") String key);
}
