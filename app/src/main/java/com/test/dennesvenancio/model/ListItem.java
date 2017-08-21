package com.test.dennesvenancio.model;

import java.util.List;

/**
 * Created by dennes on 24/09/16.
 */
public class ListItem {

    private String image;
    private String name;
    private String time;
    private List<String> bookmarks;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<String> bookmarks) {
        this.bookmarks = bookmarks;
    }
}
