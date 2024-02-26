package Truco.com.api;

import java.util.List;

import Truco.com.model.GameRoomInfoDTOModel;
import Truco.com.model.InvitationDTOModel;
import Truco.com.model.PlayerDTOModel;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface GameRoomAPI {

    @GET("/room/{roomId}")
    Call<GameRoomInfoDTOModel> getGameRoomInfo(@Path("roomId") Long roomId);

    @GET("/room/player/{playerName}")
    Call<PlayerDTOModel> getPlayerInfo(@Path("playerName") String playerName);

    @POST("/room/create/{playerName}/{maxPlayers}")
    Call<ResponseBody> createGameRoom(@Path("playerName") String playerName, @Path("maxPlayers") int maxPlayers);

    @POST("/room/{roomId}/invite")
    Call<ResponseBody> sendInvite(@Path("roomId") Long roomId, @Body InvitationDTOModel invitationDTO);

    @GET("/room/invitations/{receiverName}")
    Call<List<InvitationDTOModel>> getInvitationsForReceiver(@Path("receiverName") String receiver);

    @POST("/{receiver}/accept/{roomId}")
    Call<ResponseBody> acceptInvite(@Path("receiver") String receiver, @Path("roomId") Long roomId);

    @POST("/invite/{receiver}/decline/{roomId}")
    Call<String> declineInvite(@Path("receiver") String receiver, @Path("roomId") Long roomId);

    @POST("/room/ready/{playerName}")
    Call<String> changeReadyStatus(@Path("playerName") String playerName);

    @POST("/{host}/start/{roomId}")
    Call<String> startGame(@Path("host") String host, @Path("roomId") Long roomId);

    @DELETE("/{roomId}/{host}/kick/{player}")
    Call<String> removePlayer(@Path("roomId") Long roomId, @Path("host") String host, @Path("player") String player);

    @DELETE("/room/quit/{player}/{roomId}")
    Call<ResponseBody> quitGameRoom(@Path("player") String player, @Path("roomId") Long roomId);



}
