package mobileutility.bookmanager.addbook;

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
public class AddBookPresenter {

    private static final String TAG = "AddBookPresenter";
    BookApiInterface bookApiInterface;

    private AddNewBookActivity view;

    public AddBookPresenter(BookApiInterface bookApiInterface) {
        this.bookApiInterface = bookApiInterface;
    }

    public void addBook(Book book) {
        Observable<Book> bookCall = bookApiInterface.addBook(book);

        bookCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        Utils.showLog(TAG,"Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.showLog(TAG,"error : "+e.getMessage());
                        view.onItemsError(e);
                    }

                    @Override
                    public void onNext(Book book) {
                        view.showSuccess();
                    }
                });
    }
    public void onTakeView(AddNewBookActivity view) {
        this.view = view;
    }
}
