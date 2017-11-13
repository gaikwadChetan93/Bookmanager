package mobileutility.bookmanager.di.components;

import dagger.Component;
import mobileutility.bookmanager.addbook.AddNewBookActivity;
import mobileutility.bookmanager.checkoutbook.BookDetailActivity;
import mobileutility.bookmanager.books.BookActivity;
import mobileutility.bookmanager.updateBook.UpdateBookActivity;
import mobileutility.bookmanager.di.modules.BookModule;
import mobileutility.bookmanager.di.scopes.UserScope;
//Acts as a link between dependency provider and dependency requestor

@UserScope // using the previously defined scope, note that @Singleton will not work
@Component(dependencies = NetComponent.class, modules = BookModule.class)
public interface BookComponent {
    void inject(BookActivity activity);
    void inject(AddNewBookActivity activity);
    void inject(BookDetailActivity activity);
    void inject(UpdateBookActivity activity);
}