package mobileutility.bookmanager.addbook;

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
import mobileutility.bookmanager.models.Book;
import mobileutility.bookmanager.network.interfaces.BookApiInterface;
import mobileutility.bookmanager.utils.Utils;
import retrofit2.Retrofit;

public class AddNewBookActivity extends AppCompatActivity {
    private static final String TAG = AddNewBookActivity.class.getSimpleName();
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
    private static AddBookPresenter presenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((BookManagerApplication) getApplication())
                .getBookComponent().inject(this);
        mProgressDialog = Utils.getProgressDialog(AddNewBookActivity.this);
        if (presenter == null) {
            presenter = new AddBookPresenter(bookApiInterface);
            presenter.onTakeView(this);
        }
    }

    @OnClick(R.id.btn_submit)
    public void submit(View view) {
        if (!isAnyFieldEmpty()) {
            if (Utils.isConnectedToInternet(AddNewBookActivity.this)) {
                Book book = new Book();
                book.setTitle(bookTitle.getText().toString());
                book.setAuthor(bookAuthor.getText().toString());
                book.setPublisher(bookPublisher.getText().toString());
                book.setCategories(bookCategories.getText().toString());
                presenter.addBook(book);
            } else {
                Utils.showAlert(AddNewBookActivity.this, getString(R.string.no_internet),
                        getString(R.string.connect_msg));
            }
        }else {
            Utils.showAlert(AddNewBookActivity.this, getString(R.string.alert),
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
                    Utils.showExitAlert(AddNewBookActivity.this,"Alert","Do you wants to leave the screen with unsaved" +
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
        Utils.showAlert(AddNewBookActivity.this,"Success",
                "Book added");
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
