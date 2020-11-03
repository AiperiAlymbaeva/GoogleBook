package android.example.googlebooks.API;

import android.example.googlebooks.Models.Book;
import android.example.googlebooks.Models.BooksBody;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {
    @GET
    Call<Book> getBooks(@Url String Url);

}
