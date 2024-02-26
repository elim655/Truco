package com.backend.backend.test;

import com.backend.backend.controller.SettingsController;
import com.backend.backend.model.Settings;
import com.backend.backend.repository.SettingsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SettingsControllerTest {
    private SettingsController settingsController;

    @Mock
    private SettingsRepository settingsRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        settingsController = new SettingsController(settingsRepository);
    }

    @Test
    public void testGetSettingsById() {
        // Create a sample Settings object
        Settings sampleSettings = new Settings();
        sampleSettings.setId(1L);
        sampleSettings.setVolume(50);
        sampleSettings.setLanguage("English");
        sampleSettings.setSurrender(true);

        // Mock the behavior of the repository to return the sampleSettings when findById is called with ID 1
        when(settingsRepository.findById(1L)).thenReturn(Optional.of(sampleSettings));

        // Call the controller method
        ResponseEntity<Settings> response = settingsController.getSettings(1L);

        // Verify that the response status is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the returned Settings object matches the sampleSettings
        assertEquals(sampleSettings, response.getBody());

        // Verify that the repository's findById method was called once with ID 1
        verify(settingsRepository, times(1)).findById(1L);
    }
}
