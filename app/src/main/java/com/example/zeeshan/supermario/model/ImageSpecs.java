package com.example.zeeshan.supermario.model;

public class ImageSpecs {

    private String file;
    private Integer width;
    private Integer height;
    private String mimeType;
    private String source_url;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSourceUrl() {
        return source_url;
    }

    public void setSourceUrl(String sourceUrl) {
        this.source_url = sourceUrl;
    }

}
