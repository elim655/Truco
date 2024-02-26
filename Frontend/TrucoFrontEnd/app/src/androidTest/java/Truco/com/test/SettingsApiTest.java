package Truco.com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Truco.com.api.SettingsApi;
import Truco.com.model.SettingsModel;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SettingsApiTest {
    private MockWebServer mockWebServer;
    private SettingsApi api;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(SettingsApi.class);
    }

    @Test
    public void getSettingsTest() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"volume\":70,\"language\":\"en\"}"));

        Response<SettingsModel> response = api.getSettings(1L).execute();

        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertEquals("en", response.body().getLanguage());

        assertEquals("/settings/1", mockWebServer.takeRequest().getPath());
    }

    @Test
    public void updateSettingsTest() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"volume\":80,\"language\":\"es\"}"));

        SettingsModel updatedSettings = new SettingsModel();
        updatedSettings.setVolume(80);
        updatedSettings.setLanguage("es");

        Response<SettingsModel> response = api.updateSettings(1L, updatedSettings).execute();

        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertEquals("es", response.body().getLanguage());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("/settings/1", recordedRequest.getPath());
        assertEquals("PUT", recordedRequest.getMethod());
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
