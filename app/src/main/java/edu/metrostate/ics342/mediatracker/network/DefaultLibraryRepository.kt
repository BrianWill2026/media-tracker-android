package edu.metrostate.ics342.mediatracker.network

import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import retrofit2.HttpException

class DefaultLibraryRepository(
    private val api: LibraryApiService = RetrofitInstance.libraryApiService
) {

    suspend fun getLibrary(status: LibraryStatus): List<LibraryItem> {
        val response = api.getLibrary(status.toApiString())
        if (!response.isSuccessful) throw HttpException(response)
        return response.body() ?: emptyList()
    }


    suspend fun getLibraryItem(mediaId: Int): LibraryItem? {
        val response = api.getLibraryItem(mediaId)
        return when {
            response.isSuccessful  -> response.body()
            response.code() == 404 -> null
            else                   -> throw HttpException(response)
        }
    }


    suspend fun updateStatus(mediaId: Int, status: LibraryStatus) {
        val response = api.updateLibraryStatus(mediaId, UpdateLibraryStatusRequest(status))
        if (!response.isSuccessful) throw HttpException(response)
    }


    suspend fun removeFromLibrary(mediaId: Int) {
        val response = api.removeFromLibrary(mediaId)
        if (!response.isSuccessful) throw HttpException(response)
    }


    suspend fun addToLibrary(mediaId: Int, status: LibraryStatus) {
        val response = api.addToLibrary(AddLibraryItemRequest(mediaId, status))
        if (!response.isSuccessful && response.code() != 409) {
            throw HttpException(response)
        }
    }
}