package com.mobileprojects.cs60333.databaseapplication;

/**
 * Created by bchaudhr on 3/22/2017.
 */

public class Image {

    int _id;
    int book_id;
    String image_name;

    public Image(int _id, int book_id, String image_name) {
        this._id = _id;
        this.book_id = book_id;
        this.image_name = image_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_book_id() {
        return book_id;
    }

    public void set_book_id(int _id) {
        this.book_id = book_id;
    }

    public String get_image_name() {
        return image_name;
    }

    public void set_image_name(String image_name) {
        this.image_name = image_name;
    }
}
