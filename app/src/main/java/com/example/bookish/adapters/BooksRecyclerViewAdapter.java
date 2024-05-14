package com.example.bookish.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookish.R;
import com.example.bookish.data.models.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        View view = inflater.inflate(R.layout.book_item, parent, false);

        return new BooksRecyclerViewAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksRecyclerViewAdapter.BookViewHolder holder, int position) {
        Glide.with(context).load(books.get(position).getVolumeInfo().getImageLinks().getThumbnail()).into(holder.imageView);

        holder.rating.setText(String.valueOf(books.get(position).getVolumeInfo().getAverageRating()));
        holder.bookTitle.setText(books.get(position).getVolumeInfo().getTitle());
        List<String> authors = books.get(position).getVolumeInfo().getAuthors();
        if (authors != null && !authors.isEmpty()) {
            holder.authorName.setText(authors.get(0));
        } else {
            holder.authorName.setText("Unknown Author");
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView rating, bookTitle, authorName;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_book);

            rating = itemView.findViewById(R.id.txt_rating);
            bookTitle = itemView.findViewById(R.id.txt_book_title);
            authorName = itemView.findViewById(R.id.txt_author_name);
        }
    }
}
