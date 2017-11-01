package mobileutility.bookmanager.books;

import android.app.Activity;

import java.util.List;

import mobileutility.bookmanager.models.Book;
import mobileutility.bookmanager.network.interfaces.BookApiInterface;
import mobileutility.bookmanager.utils.Utils;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chegaikw on 7/5/2016.
 */
public class BookPresenter {
    private static final String TAG = "BookPresenter";
    BookApiInterface bookApiInterface;

    private BookActivity view;

    public BookPresenter(BookApiInterface bookApiInterface) {
        this.bookApiInterface = bookApiInterface;
    }

    public void getAllBooks() {

        Observable<List<Book>> bookCall = bookApiInterface.getBooksRx();

        bookCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Book>>() {
                    @Override
                    public void onCompleted() {
                        Utils.showLog(TAG, "Completed");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.showLog(TAG, "error : " + e.getMessage());
                        view.onItemsError(e);
                    }

                    @Override
                    public void onNext(List<Book> bookList) {
                        view.showBookList(bookList);
                    }
                });

    }

    public void deleteAllBooks() {

        Observable<Void> bookCall = bookApiInterface.
                deleteAllBook();
        bookCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onCompleted() {
                        Utils.showLog(TAG, "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.showLog(TAG, "error : " + e.getMessage());
                    }

                    @Override
                    public void onNext(Void book) {

                    }
                });
    }
    public void onTakeView(BookActivity view) {
        this.view = view;
    }
}
