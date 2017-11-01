package mobileutility.bookmanager.books;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobileutility.bookmanager.R;
import mobileutility.bookmanager.checkoutbook.BookDetailActivity;
import mobileutility.bookmanager.updateBook.UpdateBookActivity;
import mobileutility.bookmanager.models.Book;
import mobileutility.bookmanager.utils.Constants;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private List<Book> mBooks;
    private Activity activity;
    public BooksAdapter(Activity activity,List<Book> books) {
        this.activity = activity;
        mBooks = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_book, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Book book = mBooks.get(position);

        viewHolder.mTitle.setText(book.getTitle());
        viewHolder.mAuthors.setText(book.getAuthor());

        //View book details and checkout
        viewHolder.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BookDetailActivity.class);
                Book book1 = mBooks.get(position);
                intent.putExtra(Constants.BOOKS_DATA, book1);
                activity.startActivity(intent);
            }
        });

        //update book details
        viewHolder.updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateBookActivity.class);
                Book book1 = mBooks.get(position);
                intent.putExtra(Constants.BOOKS_DATA, book1);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        @BindView(R.id.book_list_item_title)
        TextView mTitle;
        @BindView(R.id.book_list_item_authors)
        TextView mAuthors;
        @BindView(R.id.book_list_root_view)
        LinearLayout rootview;
        @BindView(R.id.update_book)
        ImageView updateBook;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){

        }
    }

}