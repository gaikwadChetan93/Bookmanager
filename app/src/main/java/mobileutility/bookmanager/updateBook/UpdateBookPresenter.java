package mobileutility.bookmanager.updateBook;

import mobileutility.bookmanager.addbook.AddNewBookActivity;
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
public class UpdateBookPresenter {
    private static final String TAG = "UpdateBookPresenter";
    BookApiInterface bookApiInterface;

    private UpdateBookActivity view;

    public UpdateBookPresenter(BookApiInterface bookApiInterface) {
        this.bookApiInterface = bookApiInterface;
    }

    public void updateBook(Book book) {
        String url = book.getUrl().replace("/book/","");
        Utils.showLog(TAG,"Update : "+url);

        Observable<Book> bookCall = bookApiInterface.updateBook(url,book);

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
    public void onTakeView(UpdateBookActivity view) {
        this.view = view;
    }

}
