package mobileutility.bookmanager;

import android.app.Application;

import mobileutility.bookmanager.di.components.BookComponent;
import mobileutility.bookmanager.di.components.DaggerBookComponent;
import mobileutility.bookmanager.di.components.DaggerNetComponent;
import mobileutility.bookmanager.di.components.NetComponent;
import mobileutility.bookmanager.di.modules.AppModule;
import mobileutility.bookmanager.di.modules.BookModule;
import mobileutility.bookmanager.di.modules.NetModule;

public class BookManagerApplication extends Application {
    private NetComponent mNetComponent;
    private BookComponent mBookComponent;
    public static final String BOOKS_ENDPOINT = "https://interview-api-staging.bytemark.co/";
    @Override 
    public void onCreate() { 
        super.onCreate(); 

        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule(BOOKS_ENDPOINT))
                .build();

        mBookComponent = DaggerBookComponent.builder()
                .netComponent(mNetComponent)
                .bookModule(new BookModule())
                .build();
    } 
    public NetComponent getNetComponent() { return mNetComponent; }
    public BookComponent getBookComponent() { return mBookComponent; }
}