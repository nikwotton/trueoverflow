package com.gotruemotion.trueoverflow

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {
    private val HTTP_CONNECTION_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10)
    private val HTTP_SOCKET_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(30)

    private var retrofit: Retrofit? = null


    private fun buildOkHttpClientSdk() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .retryOnConnectionFailure(true)
            .connectTimeout(HTTP_CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_SOCKET_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(HTTP_SOCKET_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .build()

    private fun buildRetrofitClient(context: Context): Retrofit =
        Retrofit.Builder()
            .client(buildOkHttpClientSdk())
            .baseUrl("https://api.stackexchange.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()

    fun <T : Any> getService(context: Context, clazz: Class<T>): T = ServiceCache.getService(context, clazz)


    private fun getRetrofit(context: Context): Retrofit =
        (retrofit ?: buildRetrofitClient(context)).apply { retrofit = this }

    @VisibleForTesting
    object ServiceCache {
        private val serviceCache = HashMap<Class<*>, Any?>()
        fun <T> getService(context: Context, clazz: Class<T>): T {
            // The only place that should be accessing serviceCache is this one method, meaning we can
            // guarantee that the type be correct.
            @Suppress("UNCHECKED_CAST") val cachedService: T? = serviceCache[clazz] as T?
            if (cachedService != null) return cachedService
            val newService = getRetrofit(context).create(clazz)
            serviceCache[clazz] = newService
            return newService
        }
    }

}
