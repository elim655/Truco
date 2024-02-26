package com.backend.backend.controller;

import com.backend.backend.model.Settings;
import com.backend.backend.repository.SettingsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/settings")
@Tag(name = "Settings API")
public class SettingsController {

    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsController(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Operation(summary = "Get settings info", description = "Get information settings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Settings> getSettings(@PathVariable Long id) {
        Optional<Settings> settings = settingsRepository.findById(id);
        return settings.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Setting", description = "Create New Settings data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping
    public ResponseEntity<Settings> createSettings(@RequestBody Settings newSettings) {
        Settings savedSettings = settingsRepository.save(newSettings);
        return ResponseEntity.ok(savedSettings);
    }

    @Operation(summary = "Update settings", description = "Modify information inside the specific {id} settings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Settings> updateSettings(@PathVariable Long id, @RequestBody Settings updatedSettings) {
        Optional<Settings> existingSettings = settingsRepository.findById(id);

        if (existingSettings.isPresent()) {
            Settings settings = existingSettings.get();
            settings.setVolume(updatedSettings.getVolume());
            settings.setLanguage(updatedSettings.getLanguage());
            settings.setSurrender(updatedSettings.isSurrender());

            settingsRepository.save(settings);
            return ResponseEntity.ok(settings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Settings", description = "Delete the settings informtion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSettings(@PathVariable Long id) {
        settingsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

