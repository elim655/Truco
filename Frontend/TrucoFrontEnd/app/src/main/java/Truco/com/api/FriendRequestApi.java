package Truco.com.api;

import java.util.List;

import Truco.com.model.UserDTOModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FriendRequestApi {

    @GET("/users/{username}")
    Call<UserDTOModel> getUserById(@Path("username") String username);

    @POST("/users/{username}/add/{friendName}")
    Call<Void> sendFriendRequest(@Path("username") String username, @Path("friendName") String friendName);

    @GET("/users/{username}")
    Call<UserDTOModel> getUserByUsername(@Path("username") String username);

    @POST("/users/{username}/accept/{requestUser}")
    Call<ResponseBody> acceptFriendRequest(@Path("username") String loggedInUser, @Path("requestUser") String requestUser);

    @POST("/users/{username}/decline/{requestUser}")
    Call<ResponseBody> declineFriendRequest(@Path("username") String loggedInUser, @Path("requestUser") String requestUser);

    @DELETE("/users/{username}/delete/{friendName}")
    Call<Void> deleteFriend(@Path("username") String username, @Path("friendName") String friendName);


}
