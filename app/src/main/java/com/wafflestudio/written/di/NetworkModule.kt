package com.wafflestudio.written.di

import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.wafflestudio.written.App
import com.wafflestudio.written.BuildConfig
import com.wafflestudio.written.network.service.PostingService
import com.wafflestudio.written.network.service.TitleService
import com.wafflestudio.written.network.service.UserService
import com.wafflestudio.written.network.service.retrofit.PostingRetrofitService
import com.wafflestudio.written.network.service.retrofit.TitleRetrofitService
import com.wafflestudio.written.network.service.retrofit.UserRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    private val baseUrl = BuildConfig.WRITTEN_BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val tokenInterceptor = object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val token = PreferenceManager.getDefaultSharedPreferences(App.APP)
                    .getString("ACCESS_TOKEN", null)
                val request = chain.request()
                token?.let {return chain.proceed(request.newBuilder().header(
                    "Authorization", "Token $token").build())
                }
                return chain.proceed(request.newBuilder().build())
            }
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return UserService(retrofit.create(UserRetrofitService::class.java))
    }

    @Provides
    @Singleton
    fun providePostingService(retrofit: Retrofit): PostingService {
        return PostingService(retrofit.create(PostingRetrofitService::class.java))
    }

    @Provides
    @Singleton
    fun provideTitleService(retrofit: Retrofit): TitleService {
        return TitleService(retrofit.create(TitleRetrofitService::class.java))
    }
}
