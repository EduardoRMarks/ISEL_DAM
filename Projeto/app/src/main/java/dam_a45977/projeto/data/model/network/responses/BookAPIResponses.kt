package dam_a45977.projeto.data.model.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dam_a45977.projeto.data.model.Book

@JsonClass(generateAdapter = true)
data class BookByTitleResponse<T>(
    @field:Json(name = "count") val count: Int?,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "previous") val previous: String?,
    @field:Json(name = "docs") val results: List<T>?
)

@JsonClass(generateAdapter = true)
data class BooksResponse(
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "author_name") val author: List<String>?,
    @field:Json(name = "number_of_pages_median") val numberOfPages: Int?,
    @field:Json(name = "isbn") val isbn: List<String>?,
    @field:Json(name = "cover_edition_key") val cover: String?
)