package com.shi.simbo.entity;


public class SeriesItem {
    private String imgSrc;
    private String title;
    private String source;
    private String current;


    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "SeriesItem{" +
                "imgSrc='" + imgSrc + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", current='" + current + '\'' +
                '}';
    }
}
