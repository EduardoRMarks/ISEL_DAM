package dam_a45977.projeto.data.model.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


internal class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        Log.d("Book-API-Request",request.toString())
        return chain.proceed(request)
    }
}

internal object NetworkModule {
    private val _client = initBookRemoteService()
    val client: BookAPI
        get() = _client

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://openlibrary.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    fun provideBookService(retrofit: Retrofit): BookAPI {
        return retrofit.create(BookAPI::class.java)
    }

    fun initBookRemoteService() : BookAPI
    {
        val okHttpClient = provideOkHttpClient()
        val retrofit = provideRetrofit(okHttpClient)
        val bookApi = provideBookService(retrofit)
        return bookApi
    }

}