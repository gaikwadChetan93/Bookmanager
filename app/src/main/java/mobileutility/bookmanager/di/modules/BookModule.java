package mobileutility.bookmanager.di.modules;

import android.app.Application;
import android.app.ProgressDialog;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mobileutility.bookmanager.di.scopes.UserScope;
import mobileutility.bookmanager.network.interfaces.BookApiInterface;
import retrofit2.Retrofit;

@Module
public class BookModule {

    @Provides
    @UserScope // needs to be consistent with the component scope
    public BookApiInterface providesGitHubInterface(Retrofit retrofit) {
        return retrofit.create(BookApiInterface.class);
    }
}