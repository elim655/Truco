package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.Settings;
import org.junit.jupiter.api.Test;

class SettingsTest {

    @Test
    void testGetId() {
        Settings settings = new Settings();
        Long settingsId = 1L;
        settings.setId(settingsId);
        assertEquals(settingsId, settings.getId());
    }

    @Test
    void testGetVolume() {
        Settings settings = new Settings();
        int volume = 50;
        settings.setVolume(volume);
        assertEquals(volume, settings.getVolume());
    }

    @Test
    void testGetLanguage() {
        Settings settings = new Settings();
        String language = "English";
        settings.setLanguage(language);
        assertEquals(language, settings.getLanguage());
    }

    @Test
    void testIsSurrender() {
        Settings settings = new Settings();
        boolean allowSurrender = true;
        settings.setSurrender(allowSurrender);
        assertTrue(settings.isSurrender());
    }

    // Additional tests for other getter and setter methods...

    @Test
    void testSetVolume() {
        Settings settings = new Settings();
        int volume = 75;
        settings.setVolume(volume);
        assertEquals(volume, settings.getVolume());
    }

    @Test
    void testSetLanguage() {
        Settings settings = new Settings();
        String language = "French";
        settings.setLanguage(language);
        assertEquals(language, settings.getLanguage());
    }

    @Test
    void testSetSurrender() {
        Settings settings = new Settings();
        boolean allowSurrender = false;
        settings.setSurrender(allowSurrender);
        assertFalse(settings.isSurrender());
    }
}
