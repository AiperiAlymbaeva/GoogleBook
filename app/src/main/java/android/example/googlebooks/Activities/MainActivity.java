package android.example.googlebooks.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.example.googlebooks.Adapter.BookAdapter;
import android.example.googlebooks.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Search(View view) {
        EditText editText = findViewById(R.id.user_input_edit_tv);
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please write the name of book", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, BooksActivity.class);
            intent.putExtra("Search", editText.getText().toString());
            startActivity(intent);
        }
    }
        }
