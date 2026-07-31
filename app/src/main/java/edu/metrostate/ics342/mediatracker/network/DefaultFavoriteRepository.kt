package edu.metrostate.ics342.mediatracker.network

import edu.metrostate.ics342.mediatracker.data.model.Favorite
import retrofit2.HttpException

class DefaultFavoriteRepository(
    private val api: FavoriteApiService = RetrofitInstance.favoriteApiService
) {

    suspend fun getFavorites(): List<Favorite> {
        val response = api.getFavorites()
        if (!response.isSuccessful) throw HttpException(response)
        return response.body() ?: emptyList()
    }


    suspend fun getFavorite(mediaId: Int): Favorite? {
        val response = api.getFavorite(mediaId)
        return when {
            response.isSuccessful  -> response.body()
            response.code() == 404 -> null
            else                   -> throw HttpException(response)
        }
    }


    suspend fun addFavorite(mediaId: Int) {
        val response = api.addFavorite(AddFavoriteRequest(mediaId))
        if (!response.isSuccessful && response.code() != 409) {
            throw HttpException(response)
        }
    }


    suspend fun removeFavorite(mediaId: Int) {
        val response = api.removeFavorite(mediaId)
        if (!response.isSuccessful) throw HttpException(response)
    }
}
