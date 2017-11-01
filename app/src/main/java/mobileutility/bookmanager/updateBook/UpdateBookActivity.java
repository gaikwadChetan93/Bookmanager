package mobileutility.bookmanager.updateBook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobileutility.bookmanager.BookManagerApplication;
import mobileutility.bookmanager.R;
import mobileutility.bookmanager.checkoutbook.BookDetailActivity;
import mobileutility.bookmanager.models.Book;
import mobileutility.bookmanager.network.interfaces.BookApiInterface;
import mobileutility.bookmanager.utils.Constants;
import mobileutility.bookmanager.utils.Utils;
import retrofit2.Retrofit;

public class UpdateBookActivity extends AppCompatActivity {

    private static final String TAG = BookDetailActivity.class.getSimpleName();

    @BindView(R.id.edt_book_title)
    EditText bookTitle;
    @BindView(R.id.edt_author)
    EditText bookAuthor;
    @BindView(R.id.edt_categories)
    EditText bookCategories;
    @BindView(R.id.edt_publisher)
    EditText bookPublisher;
    @Inject
    Retrofit mRetrofit;
    @Inject
    BookApiInterface bookApiInterface;

    private static UpdateBookPresenter presenter;

    private Book book;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((BookManagerApplication) getApplication())
                .getBookComponent().inject(this);
        mProgressDialog = Utils.getProgressDialog(UpdateBookActivity.this);
        if (presenter == null) {
            presenter = new UpdateBookPresenter(bookApiInterface);
            presenter.onTakeView(this);
        }

        book = getIntent().getParcelableExtra(Constants.BOOKS_DATA);
        if(book != null){
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookPublisher.setText(book.getPublisher());
            bookCategories.setText(book.getCategories());
        }
    }

    @OnClick(R.id.btn_update)
    public void update(View view) {
        if (!isAnyFieldEmpty()) {
            if (Utils.isConnectedToInternet(UpdateBookActivity.this)) {
                Book book = new Book();
                book.setTitle(bookTitle.getText().toString());
                book.setAuthor(bookAuthor.getText().toString());
                book.setPublisher(bookPublisher.getText().toString());
                book.setCategories(bookCategories.getText().toString());
                book.setUrl(this.book.getUrl());
                presenter.updateBook(book);
            } else {
                Utils.showAlert(UpdateBookActivity.this, getString(R.string.no_internet),
                        getString(R.string.connect_msg));
            }
        }else {
            Utils.showAlert(UpdateBookActivity.this, getString(R.string.alert),
                    getString(R.string.please_fill_all_fields));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_done:
                if(isAllFieldsEmpty()) {
                    this.finish();
                }else{
                    Utils.showExitAlert(UpdateBookActivity.this,"Alert","Do you wants to leave the screen with unsaved" +
                            "changes.");
                }
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:return true;
        }
    }


    /**
     * check if any field is empty
     * @return
     */
    private boolean isAnyFieldEmpty(){
        if(bookTitle.getText().toString().isEmpty() || bookAuthor.getText().toString().isEmpty()
                ||bookCategories.getText().toString().isEmpty() || bookPublisher.getText().toString().isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Check weather all fields are empty
     * @return
     */
    private boolean isAllFieldsEmpty(){
        if(bookTitle.getText().toString().isEmpty() && bookAuthor.getText().toString().isEmpty()
                &&bookCategories.getText().toString().isEmpty() && bookPublisher.getText().toString().isEmpty()){
            return true;
        }else {
            return false;
        }
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
        Utils.showAlert(UpdateBookActivity.this,"Success",
                "Book updated");
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

}
