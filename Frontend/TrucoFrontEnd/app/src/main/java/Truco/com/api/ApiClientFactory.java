package Truco.com.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;



/**
 * Factory class for creating instances of API clients.
 * This class is responsible for setting up and providing Retrofit instances
 * configured for accessing the Truco game's backend services.
 */
public class ApiClientFactory {

    private static Retrofit apiClient = null;

    /**
     * Retrieves a singleton Retrofit instance.
     * Initializes the instance with Gson converter and base URL if not already created.
     *
     * @return Retrofit instance for making API calls.
     */
    private static Retrofit getApiClient() {
        if (apiClient == null) {


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            apiClient = new Retrofit.Builder()
                    .baseUrl("http://coms-309-059.class.las.iastate.edu:8080/")

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClient;
    }

    /**
     * Creates and returns a LoginApi instance.
     *
     * @return LoginApi instance for managing login operations.
     */
    public static LoginApi getLoginApi() {
        return getApiClient().create(LoginApi.class);
    }
}
