package com.example.bookish.data.models;

import com.example.bookish.data.models.Book;

import java.util.List;

public class BooksResponse {
    //private String kind;
    private List<Book> items;
    private int totalItems;

//    public String getKind() {
//        return kind;
//    }


    public List<Book> getItems() {
        return items;
    }

    public int getTotalItems() {
        return totalItems;
    }

}