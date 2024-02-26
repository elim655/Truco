package Truco.com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Truco.com.api.FriendRequestApi;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

public class FriendRequestApiTest {
    private MockWebServer mockWebServer;
    private FriendRequestApi api;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(FriendRequestApi.class);
    }

    @Test
    public void sendFriendRequestTest() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        Call<Void> call = api.sendFriendRequest("userName", "friendName");
        Response<Void> response = call.execute();

        assertEquals(200, response.code());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("/users/userName/add/friendName", recordedRequest.getPath());
        assertEquals("POST", recordedRequest.getMethod());
    }


    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
