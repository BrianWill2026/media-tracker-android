package edu.metrostate.ics342.mediatracker.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import edu.metrostate.ics342.mediatracker.data.SessionRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitInstance {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults    = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    private var sharedHttpClient: OkHttpClient? = null

    private fun getHttpClient(sessionRepository: SessionRepository): OkHttpClient {
        return sharedHttpClient ?: synchronized(this) {
            sharedHttpClient ?: OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(sessionRepository))
                .addInterceptor(loggingInterceptor)
                .build().also { sharedHttpClient = it }
        }
    }

    private var mediaRetrofit: Retrofit? = null

    fun mediaApiService(sessionRepository: SessionRepository): MediaApiService {
        val r = mediaRetrofit ?: synchronized(this) {
            mediaRetrofit ?: Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(getHttpClient(sessionRepository))
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build().also { mediaRetrofit = it }
        }
        return r.create(MediaApiService::class.java)
    }

    // User API usually doesn't need auth, but using the same client is fine
    // as AuthInterceptor will just see a null token.
    // However, since we need a SessionRepository to build the client,
    // we can't initialize this until we have one.
    // We'll use a simple client for public APIs if no sessionRepo is provided.
    
    private val publicHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val userApiService: UserApiService = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(publicHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(UserApiService::class.java)
}