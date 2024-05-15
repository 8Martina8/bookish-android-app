package com.example.bookish.search.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookish.R;
import com.example.bookish.adapters.BooksRecyclerViewAdapter;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;
import com.example.bookish.fragments.MicFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private RemoteDataSource apiClient;

    private RecyclerView recyclerView;
    private BooksRecyclerViewAdapter adapter;
    private ArrayList<Book> books;

    private SearchView searchView;

    private SpeechRecognizer speechRecognizer;
    private ImageView micImageView;
    MicFragment fragment;

    @SuppressLint({"NotifyDataSetChanged", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiClient = new ApiClient();

        books = new ArrayList<>();
        adapter = new BooksRecyclerViewAdapter(this, books);
        recyclerView = findViewById(R.id.searchBooksRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        searchView = findViewById(R.id.booksSearchView);
        searchView.clearFocus();

        fragment = MicFragment.newInstance();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooks(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }
            @Override
            public void onBeginningOfSpeech() {
            }
            @Override
            public void onRmsChanged(float rmsdB) {

            }
            @Override
            public void onBufferReceived(byte[] buffer) {

            }
            @Override
            public void onEndOfSpeech() {
                micImageView.setImageResource(R.drawable.baseline_mic_24);
            }
            @Override
            public void onError(int error) {

            }
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (text != null && !text.isEmpty()) {
                    searchView.setQuery(text.get(0), true);
                } else toast("no text");
                fragment.dismiss();
            }
            @Override
            public void onPartialResults(Bundle partialResults) {

            }
            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        micImageView = findViewById(R.id.voice_search_iv);
        micImageView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                speechRecognizer.stopListening();
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                speechRecognizer.startListening(speechIntent);
                micImageView.setImageResource(R.drawable.baseline_mic_pressed);

                fragment.show(getSupportFragmentManager(), "voice_capture_fragment");
            }

            return false;
        });
    }

    private void searchBooks(String query) {
        Call<BooksResponse> searchCall = apiClient.searchBooks(query);
        searchCall.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BooksResponse booksResponse = response.body();
                    List<Book> searchedBooks = booksResponse.getItems();

                    books = (ArrayList<Book>) searchedBooks;
                    updateSearchResults();
                } else {
                    toast("No Result!");
                }
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                toast("API Call Failed!");
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateSearchResults() {
        adapter.setBooks(books);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }
}