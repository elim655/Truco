package Truco.com.api;

import android.provider.Settings;

import Truco.com.model.SettingsModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SettingsApi {
    @GET("settings/{id}")
    Call<SettingsModel> getSettings(@Path("id") Long id);

    @POST("settings")
    Call<SettingsModel> createSettings(@Body SettingsModel newSettings);

    @PUT("settings/{id}")
    Call<SettingsModel> updateSettings(@Path("id") Long id, @Body SettingsModel updatedSettings);

    @DELETE("settings/{id}")
    Call<Void> deleteSettings(@Path("id") Long id);
}
