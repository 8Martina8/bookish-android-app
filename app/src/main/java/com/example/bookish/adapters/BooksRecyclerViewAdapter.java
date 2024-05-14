package com.example.bookish.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookish.R;
import com.example.bookish.data.models.Book;

import java.util.ArrayList;

public class BooksRecyclerViewAdapter extends RecyclerView.Adapter<BooksRecyclerViewAdapter.BookViewHolder> {
    Context context;
    ArrayList<Book> books;

    public BooksRecyclerViewAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BooksRecyclerViewAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rc_item, parent, false);

        return new BooksRecyclerViewAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksRecyclerViewAdapter.BookViewHolder holder, int position) {
        holder.bookTitleTV.setText(books.get(position).getVolumeInfo().getTitle());
        holder.bookDescTV.setText(books.get(position).getVolumeInfo().getDescription());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitleTV, bookDescTV;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookTitleTV = itemView.findViewById(R.id.bookTitleTV);
            bookDescTV = itemView.findViewById(R.id.bookDescTV);
        }
    }
}
