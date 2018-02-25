package io.esid.dev.githubcommit.network

import io.esid.dev.githubcommit.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Aldi on 2/25/2018.
 */
object Injector{

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constant.GITHUB_API)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun getHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(Constant.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(Constant.HTTP_TIME_OUT, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    fun getApi(): API {
        return provideRetrofit().create(API::class.java)
    }

}