package edu.metrostate.ics342.mediatracker.network
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String? = null
)


class MediaNotFoundException(message: String) : Exception(message)
