package android.example.googlebooks.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.googlebooks.API.ApiInterface;
import android.example.googlebooks.Models.Book;
import android.example.googlebooks.Adapter.BookAdapter;
import android.example.googlebooks.R;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class BooksActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    ApiInterface apiInterface;

    Uri baseUri, finalUri;
    Uri.Builder builder;

    ArrayList<Book> bookList = new ArrayList<>();
    public static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public String searchBook = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        Intent intent = getIntent();
        searchBook = intent.getStringExtra("Search").replace(' ', '+');
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        progressBar = (ProgressBar) findViewById(R.id.spinner);

        bookAdapter = new BookAdapter(clickListener);

        recyclerView.setAdapter(bookAdapter);

        String baseUrl = "https://www.googleapis.com/books/v1/";
        baseUri = Uri.parse(URL);
        finalUri = Uri.parse(URL + searchBook);
        builder = finalUri.buildUpon();
        searchBook = builder.toString();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiInterface = retrofit.create(ApiInterface.class);

        retrofitBook();
    }

    BookAdapter.OnItemClickListener clickListener = new BookAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Book book) {
            String url = book.volumeInfo.getPreviewLink();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    };

    private void retrofitBook() {

        Call<Book> call = apiInterface.getBooks(searchBook);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(@NonNull Call<Book> call, @NonNull Response<Book> response) {
                if (response.isSuccessful()) {
                    Book book = response.body();
                    assert book != null;
                    bookAdapter.setData(book.books);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Book> call, @NonNull Throwable t) {
                recyclerView.setVisibility(View.INVISIBLE);
                Toast.makeText(BooksActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




}