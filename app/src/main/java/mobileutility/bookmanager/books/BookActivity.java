package mobileutility.bookmanager.books;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobileutility.bookmanager.BookManagerApplication;
import mobileutility.bookmanager.R;
import mobileutility.bookmanager.addbook.AddNewBookActivity;
import mobileutility.bookmanager.models.Book;
import mobileutility.bookmanager.network.interfaces.BookApiInterface;
import mobileutility.bookmanager.utils.Utils;
import retrofit2.Retrofit;

public class BookActivity extends AppCompatActivity {
    private static final String TAG = BookActivity.class.getSimpleName();

    @BindView(R.id.add_book)  FloatingActionButton addNewBook;
    @BindView(R.id.toolbar)  Toolbar toolbar;
    @BindView(R.id.books_recyclerview)  RecyclerView mRecyclerView;
    @BindView(R.id.delete_layout)
    LinearLayout deleteLayout;
    @Inject
    Retrofit mRetrofit;
    @Inject
    BookApiInterface bookApiInterface;
    private ProgressDialog mProgressDialog;
    private static BookPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((BookManagerApplication) getApplication())
                .getBookComponent().inject(this);
        setSupportActionBar(toolbar);

        mProgressDialog = Utils.getProgressDialog(BookActivity.this);
        if (presenter == null) {
            presenter = new BookPresenter(bookApiInterface);
            presenter.onTakeView(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_refresh:
                //get all books
                if(Utils.isConnectedToInternet(BookActivity.this)) {
                    presenter.getAllBooks();
                }else{
                    Utils.showAlert(BookActivity.this,getString(R.string.no_internet),
                            getString(R.string.connect_msg));
                }
                return true;
            default:return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //get all books
        if(Utils.isConnectedToInternet(BookActivity.this)) {
            presenter.getAllBooks();
        }else{
            Utils.showAlert(BookActivity.this,getString(R.string.no_internet),
                    getString(R.string.connect_msg));
        }
    }

    /**
     * Add new book
     * @param view
     */
    @OnClick(R.id.add_book)
    public void submit(View view) {
        startActivity(new Intent(BookActivity.this,AddNewBookActivity.class));
    }

    /**
     * Delete all books
     * @param view
     */
    @OnClick(R.id.delete_layout)
    public void deleteAll(View view) {
        if(Utils.isConnectedToInternet(BookActivity.this)) {
            presenter.deleteAllBooks();
        }else{
            Utils.showAlert(BookActivity.this,getString(R.string.no_internet),
                    getString(R.string.connect_msg));
        }
    }

    /**
     * show book list
     * @param bookList
     */
    public void showBookList(List<Book> bookList) {
        Utils.showLog(TAG,bookList.toString());
        if(bookList.size() == 0){
            deleteLayout.setVisibility(View.GONE);
        }else{
            deleteLayout.setVisibility(View.VISIBLE);
        }
        RecyclerView.LayoutManager mLayoutManager; mLayoutManager =
                new LinearLayoutManager(BookActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        BooksAdapter mAdapter = new BooksAdapter(BookActivity.this,bookList);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Show error
     * @param throwable
     */
    public void onItemsError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * refresh screnn after all books are deleted
     */
    public void onDeleteCompleted() {
        Toast.makeText(BookActivity.this,"All books deleted",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(BookActivity.this,BookActivity.class));
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
