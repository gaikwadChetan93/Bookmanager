package mobileutility.bookmanager.checkoutbook;

import android.widget.Toast;

import mobileutility.bookmanager.models.Book;
import mobileutility.bookmanager.network.interfaces.BookApiInterface;
import mobileutility.bookmanager.updateBook.UpdateBookActivity;
import mobileutility.bookmanager.utils.Utils;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chegaikw on 7/5/2016.
 */
public class CheckoutBookPresenter {
    private static final String TAG = "CheckoutBookPresenter";
    BookApiInterface bookApiInterface;

    private BookDetailActivity view;

    public CheckoutBookPresenter(BookApiInterface bookApiInterface) {
        this.bookApiInterface = bookApiInterface;
    }

    public void checkoutBook(Book book) {

                String url = book.getUrl().replace("/book/","");
                Utils.showLog(TAG,"Update : "+url);
                Observable<Book> bookCall = bookApiInterface.
                        checkout(url,book);
                bookCall.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Book>() {
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
                            public void onNext(Book book) {
                                Utils.showLog(TAG,"checkoutBook() : "+book.toString());
                                view.showSuccess();
                            }
                        });

    }

    public void deleteBook(String url) {
        Utils.showLog(TAG,"Update : "+url);
        Observable<Book> bookCall = bookApiInterface.
                deleteBook(url);
        bookCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {

                        Utils.showLog(TAG, "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.showLog(TAG, "error : " + e.getMessage());
                    }

                    @Override
                    public void onNext(Book book) {

                        view.showDeleteSuccess();
                    }
                });
    }

    public void onTakeView(BookDetailActivity view) {
        this.view = view;
    }
}
