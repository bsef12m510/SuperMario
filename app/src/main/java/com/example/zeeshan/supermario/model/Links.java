package com.example.zeeshan.supermario.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Links implements Serializable {

    private List<Self> self = null;
    private List<Collection> collection = null;
    private List<About> about = null;
    private List<Author> author = null;
    private List<Reply> replies = null;
    private List<VersionHistory> versionHistory = null;
    private List<PredecessorVersion> predecessorVersion = null;

    @SerializedName(value = "wp:featuredmedia")
    private List<WpFeaturedmedium> wpFeaturedmedia = null;

    private List<WpAttachment> wpAttachment = null;
    private List<WpTerm> wpTerm = null;
    private List<Cury> curies = null;

    public List<Self> getSelf() {
        return self;
    }

    public void setSelf(List<Self> self) {
        this.self = self;
    }

    public List<Collection> getCollection() {
        return collection;
    }

    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    public List<About> getAbout() {
        return about;
    }

    public void setAbout(List<About> about) {
        this.about = about;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<VersionHistory> getVersionHistory() {
        return versionHistory;
    }

    public void setVersionHistory(List<VersionHistory> versionHistory) {
        this.versionHistory = versionHistory;
    }

    public List<PredecessorVersion> getPredecessorVersion() {
        return predecessorVersion;
    }

    public void setPredecessorVersion(List<PredecessorVersion> predecessorVersion) {
        this.predecessorVersion = predecessorVersion;
    }

    public List<WpFeaturedmedium> getWpFeaturedmedia() {
        return wpFeaturedmedia;
    }

    public void setWpFeaturedmedia(List<WpFeaturedmedium> wpFeaturedmedia) {
        this.wpFeaturedmedia = wpFeaturedmedia;
    }

    public List<WpAttachment> getWpAttachment() {
        return wpAttachment;
    }

    public void setWpAttachment(List<WpAttachment> wpAttachment) {
        this.wpAttachment = wpAttachment;
    }

    public List<WpTerm> getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(List<WpTerm> wpTerm) {
        this.wpTerm = wpTerm;
    }

    public List<Cury> getCuries() {
        return curies;
    }

    public void setCuries(List<Cury> curies) {
        this.curies = curies;
    }

}