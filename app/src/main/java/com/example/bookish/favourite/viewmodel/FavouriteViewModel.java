package com.example.bookish.favourite.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookish.data.models.Book;
import com.example.bookish.repo.BookRepo;

import java.util.ArrayList;
import java.util.List;

public class FavouriteViewModel extends ViewModel {

    private final BookRepo bookRepo;
    private LiveData<List<Book>> favoriteBooks;
    private MutableLiveData<List<Book>> _favoriteBooks;

    public FavouriteViewModel(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
        _favoriteBooks = new MutableLiveData<>();
        favoriteBooks = _favoriteBooks;
    }

    public LiveData<List<Book>> getFavoriteBooks(int userID) {
        List<String> favBooksIDs = bookRepo.getBooksIdsByUserId(userID);
        List<Book> favoriteBooksList = new ArrayList<>();
        for (String bookID : favBooksIDs) {
            Book book = bookRepo.getBookById(bookID);
            if (book != null) {
                favoriteBooksList.add(book);
            }
        }
        _favoriteBooks.setValue(favoriteBooksList);
        return favoriteBooks;
    }
}