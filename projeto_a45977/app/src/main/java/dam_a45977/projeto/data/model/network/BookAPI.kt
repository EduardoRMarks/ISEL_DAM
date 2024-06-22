package dam_a45977.projeto.data.model.network

import dam_a45977.projeto.data.model.network.responses.BookByTitleResponse
import dam_a45977.projeto.data.model.network.responses.BooksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookAPI {

    @GET("search.json")
    fun searchBooksByTitle(@Query("q") title: String): Call<BookByTitleResponse<BooksResponse>>

    @GET("search.json")
    fun searchBooksByISBN(@Query("isbn") isbn: String): Call<BookByTitleResponse<BooksResponse>>

    @GET("search.json")
    fun searchBooksByAuthor(@Query("author") author: String): Call<BookByTitleResponse<BooksResponse>>
}