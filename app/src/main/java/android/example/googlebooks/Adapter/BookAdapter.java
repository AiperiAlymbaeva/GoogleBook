package android.example.googlebooks.Adapter;

import android.annotation.SuppressLint;

import android.example.googlebooks.Models.Book;
import android.example.googlebooks.R;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> bookList;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BookAdapter(OnItemClickListener listener) {

        onItemClickListener = listener;
        bookList = new ArrayList<>();

    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Book book = bookList.get(position);
        holder.bind(bookList.get(position), onItemClickListener);
        holder.titleOfBooks.setText(book.volumeInfo.title);
        try {
            holder.authors.setText(book.volumeInfo.authors.get(0));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        holder.date.setText(book.volumeInfo.publishedDate);
        if (book.volumeInfo.pageCount != 0) {
            holder.pages.setText(book.volumeInfo.pageCount + " pages");
        } else {
            holder.pages.setText("N/A");
        }
        if (book.volumeInfo.averageRating != 0.0) {
            holder.rating.setText(String.valueOf(book.volumeInfo.averageRating));
        } else {
            holder.rating.setText("N/A");
        }


        Glide
                .with(holder.image.getContext())
                .load(book.volumeInfo.imageLinks.smallThumbnail.replace("http", "https"))
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView titleOfBooks;
        private TextView date;
        private TextView authors;
        private TextView pages;
        private TextView rating;


        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            titleOfBooks = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            authors = itemView.findViewById(R.id.author);
            pages = itemView.findViewById(R.id.page);
            rating = itemView.findViewById(R.id.info);

        }


        public void bind(final Book book, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(book);
                }
            });
        }
    }

    public void clear() {
        bookList.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Book> bookList) {
        this.bookList.clear();
        this.bookList.addAll(bookList);
        notifyDataSetChanged();
    }


}