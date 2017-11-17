package mobileutility.bookmanager.di.components;

import android.content.SharedPreferences;


import javax.inject.Singleton;

import dagger.Component;
import mobileutility.bookmanager.di.modules.AppModule;
import mobileutility.bookmanager.di.modules.NetModule;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
   Retrofit retrofit();
   OkHttpClient okHttpClient();
   SharedPreferences sharedPreferences();
}