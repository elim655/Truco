package Truco.com.model;

public class SettingsModel {

    private Long id;

    private int volume;
    private String language;
    private boolean surrender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isAllowSurrender() {
        return surrender;
    }

    public void setAllowSurrender(boolean allowSurrender) {
        this.surrender = allowSurrender;
    }
}

