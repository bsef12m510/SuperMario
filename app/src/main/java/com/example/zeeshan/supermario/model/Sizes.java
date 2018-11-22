package com.example.zeeshan.supermario.model;

public class Sizes {
    private ImageSpecs thumbnail;
    private ImageSpecs medium;
    private ImageSpecs full;

    public ImageSpecs getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageSpecs thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageSpecs getMedium() {
        return medium;
    }

    public void setMedium(ImageSpecs medium) {
        this.medium = medium;
    }

    public ImageSpecs getFull() {
        return full;
    }

    public void setFull(ImageSpecs full) {
        this.full = full;
    }
}
