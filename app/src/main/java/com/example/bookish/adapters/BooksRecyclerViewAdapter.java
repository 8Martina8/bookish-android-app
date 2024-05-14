package com.example.bookish.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookish.R;
import com.example.bookish.data.models.Book;
import com.example.bookish.details.view.DetailsActivity;
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
        Book book = books.get(position);

        String imageURL = "https://angelbookhouse.com/assets/front/img/product/edition_placeholder.png";
        if (book.getVolumeInfo().getImageLinks() != null) {
            imageURL = book.getVolumeInfo().getImageLinks().getThumbnail();
        }
        Glide.with(context).load(imageURL).into(holder.imageView);

        holder.rating.setText(String.valueOf(book.getVolumeInfo().getAverageRating()));
        holder.bookTitle.setText(book.getVolumeInfo().getTitle());
        List<String> authors = book.getVolumeInfo().getAuthors();
        if (authors != null && !authors.isEmpty()) {
            holder.authorName.setText(authors.get(0));
        } else {
            holder.authorName.setText("Unknown Author");
        }

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("book_id", book.getId());

            context.startActivity(intent);
        });

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_search_cards));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView rating, bookTitle, authorName;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.book_card);
            imageView = itemView.findViewById(R.id.img_book);

            rating = itemView.findViewById(R.id.txt_rating);
            bookTitle = itemView.findViewById(R.id.txt_book_title);
            authorName = itemView.findViewById(R.id.txt_author_name);
        }
    }
}
