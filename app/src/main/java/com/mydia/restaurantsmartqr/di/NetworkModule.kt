package com.mydia.restaurantsmartqr.di







import com.mydia.restaurantsmartqr.api.Api
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

    @Provides
    @RetrofitStore
     fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideApiClient(@RetrofitStore retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
       /* if (BuildConfig.DEBUG)*/
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        return client.build()
    }


}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitStore
