package com.shi.simbo.entity;

import java.util.List;

public class SeriesDetail {
    private String title;
    private String description;
    private String actors;
    private String release;
    private String url;

    private List<Episode> episodes;
    private int current = 0;


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "SeriesDetail{" +
                "description='" + description + '\'' +
                ", actors='" + actors + '\'' +
                ", release='" + release + '\'' +
                ", episodes=" + episodes +
                '}';
    }
}
