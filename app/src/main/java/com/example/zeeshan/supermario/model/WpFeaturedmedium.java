package com.example.zeeshan.supermario.model;

import java.io.Serializable;

public class WpFeaturedmedium implements Serializable {

    private Boolean embeddable;
    private String href;

    public Boolean getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(Boolean embeddable) {
        this.embeddable = embeddable;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}