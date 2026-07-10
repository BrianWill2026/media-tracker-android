package edu.metrostate.ics342.mediatracker.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import edu.metrostate.ics342.mediatracker.datastore.DefaultSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DefaultMediaRepository(DefaultSessionRepository(application))

    private val _uiState = MutableStateFlow<MediaDetail?>(null)
    val uiState: StateFlow<MediaDetail?> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadMediaDetail(id: Int) {
        if (id == -1) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _uiState.value = repository.getDetails(id)
            } catch (e: Exception) {
                _uiState.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
