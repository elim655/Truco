package Truco.com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Truco.com.api.GameRoomAPI;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

public class GameRoomAPITest {
    private MockWebServer mockWebServer;
    private GameRoomAPI api;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(GameRoomAPI.class);
    }

    @Test
    public void createGameRoomTest() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("game room created"));

        Call<ResponseBody> call = api.createGameRoom("playerName", 4);
        Response<ResponseBody> response = call.execute();

        assertEquals(200, response.code());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("/room/create/playerName/4", recordedRequest.getPath());
        assertEquals("POST", recordedRequest.getMethod());
    }


    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
