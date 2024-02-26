package Truco.com.api;

import Truco.com.Main;
import java.util.List;

import Truco.com.model.Login;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginApi {

    @GET("signup")
    Call<String> signUp();

    @POST("signup/{user}/{pass}")
//    Call<String> PostSignupByPath(@Path("user") String user, @Path("pass") String pass);
    Call<ResponseBody> PostSignupByPath(@Path("user") String user, @Path("pass") String pass);

    @POST("login")
    //Call<String> UserLogin(@Body Login userLogin);
    Call<ResponseBody> UserLogin(@Body Login userLogin);

    @GET("users")
    Call<List<Login>> GetAllUsers();

    @DELETE("user/delete")
    Call<List<Login>> DeleteUserByPath(@Body Login delUser);


}
