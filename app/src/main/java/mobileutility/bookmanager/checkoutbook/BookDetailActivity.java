package mobileutility.bookmanager.checkoutbook;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobileutility.bookmanager.BookManagerApplication;
import mobileutility.bookmanager.R;
import mobileutility.bookmanager.models.Book;
import mobileutility.bookmanager.network.interfaces.BookApiInterface;
import mobileutility.bookmanager.updateBook.UpdateBookPresenter;
import mobileutility.bookmanager.utils.Constants;
import mobileutility.bookmanager.utils.Utils;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookDetailActivity extends AppCompatActivity {

    private static final String TAG = BookDetailActivity.class.getSimpleName();
    @BindView(R.id.book_title)
    TextView bookTitle;
    @BindView(R.id.book_author)
    TextView bookAuthor;
    @BindView(R.id.book_category)
    TextView bookCategories;
    @BindView(R.id.book_publisher)
    TextView bookPublisher;
    @BindView(R.id.book_last_checked_out)
    TextView bookLastCheckedOut;
    @Inject
    Retrofit mRetrofit;
    @Inject
    BookApiInterface bookApiInterface;
    private Book book;

    private ShareActionProvider mShareActionProvider;
    private static CheckoutBookPresenter presenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((BookManagerApplication) getApplication())
                .getBookComponent().inject(this);
        mProgressDialog = Utils.getProgressDialog(BookDetailActivity.this);
        if (presenter == null) {
            presenter = new CheckoutBookPresenter(bookApiInterface);
            presenter.onTakeView(this);
        }

        book = getIntent().getParcelableExtra(Constants.BOOKS_DATA);
        if(book != null){
            bookTitle.setText(""+book.getTitle());
            bookAuthor.setText(""+book.getAuthor());
            bookPublisher.setText("Publisher : "+book.getPublisher());
            bookCategories.setText("Tags : "+book.getCategories());
            if(book.getLastCheckedOutBy()!=null && book.getLastCheckedOut()!=null){
                bookLastCheckedOut.setText("Last Checked Out : " + book.getLastCheckedOutBy()
                        + "@" + book.getLastCheckedOut());
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        mShareActionProvider.setShareHistoryFileName(
                ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        mShareActionProvider.setShareIntent(createShareIntent());

        return true;
    }

    private Intent createShareIntent() {
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("text/plain");
        String data = "Please read this book : "+book.getTitle();
        myShareIntent.putExtra(Intent.EXTRA_TEXT, data);
        return myShareIntent;
    }

    @OnClick(R.id.btn_checkout)
    public void submit(View view) {
        if(Utils.isConnectedToInternet(BookDetailActivity.this)) {
            getUserName();
        }else{
            Utils.showAlert(BookDetailActivity.this,getString(R.string.no_internet),
                    getString(R.string.connect_msg));
        }
    }

    @OnClick(R.id.btn_delete)
    public void delete(View view) {
        if(Utils.isConnectedToInternet(BookDetailActivity.this)) {
            String url = book.getUrl().replace("/book/","");
            presenter.deleteBook(url);
        }else{
            Utils.showAlert(BookDetailActivity.this,getString(R.string.no_internet),
                    getString(R.string.connect_msg));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_share:


                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:return true;
        }
    }



    private void getUserName() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(BookDetailActivity.this);
        alert.setMessage("Enter your name to checkout");
        alert.setTitle("Checkout");

        alert.setView(edittext);

        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String userName = edittext.getText().toString();
                if(userName!=null) {
                    if(!userName.isEmpty()) {
                        book.setLastCheckedOutBy(userName);
                        presenter.checkoutBook(book);
                    } else {
                        Utils.showAlert(BookDetailActivity.this,"Checkout alert","Please enter name to checkout");
                    }
                }
                return;
            }
        });

        alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    /**
     * Show error
     * @param throwable
     */
    public void onItemsError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * Show book added success message
     */
    public void showSuccess() {
        Utils.showAlert(BookDetailActivity.this,"Success",
                "Book checkout success");
    }

    /**
     * show loader
     */
    public void showLoading(){
        mProgressDialog.show();
    }

    /**
     * hide loader
     */
    public void hideLoading(){
        if(mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void showDeleteSuccess() {
        BookDetailActivity.this.finish();
        Toast.makeText(BookDetailActivity.this,"Book deleted",Toast.LENGTH_SHORT).show();
    }
}
