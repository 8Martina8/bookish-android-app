package com.example.bookish.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
@Entity(primaryKeys = {"userID", "bookID"})
public class UserFavBooks {
    @NonNull
    private Integer userID;
    @NonNull

    private String bookID;
    public UserFavBooks(Integer userID, String bookID) {
        this.userID = userID;
        this.bookID = bookID;
    }
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }


}
