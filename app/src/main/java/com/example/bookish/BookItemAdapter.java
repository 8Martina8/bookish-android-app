package com.example.bookish;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookish.data.models.Book;
import com.example.bookish.details.view.DetailsActivity;


import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.MyViewHolder> {
    private List<Book> data;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public BookItemAdapter(List<Book> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        context = parent.getContext();
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int currentPosition = holder.getAdapterPosition();

        // Check if the currentPosition is valid
        if (currentPosition != RecyclerView.NO_POSITION) {
            // Retrieve the data item at the current position
            Book currentBook = data.get(currentPosition);

            holder.txtBookTitle.setText(currentBook.getVolumeInfo().getTitle());
            holder.txtAuthorName.setText(currentBook.getVolumeInfo().getAuthors().get(0));

            // Load image using Glide
            Glide.with(context)
                    .load(currentBook.getVolumeInfo().getImageLinks().getThumbnail())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.book_placeholder)
                            .error(R.drawable.baseline_broken_image_24))
                    .into(holder.imgView);

            // Set item click listener
            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    // Invoke onItemClick with the current data item
                    onItemClickListener.onItemClick(currentBook);

                    // Open details activity and send currentBook as an intent
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("BOOK", currentBook);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtBookTitle;
        TextView txtAuthorName;
        ImageView imgView;

        MyViewHolder(View itemView) {
            super(itemView);
            txtBookTitle = itemView.findViewById(R.id.txt_book_title);
            txtAuthorName = itemView.findViewById(R.id.txt_author_name);
            imgView = itemView.findViewById(R.id.img_book);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}
