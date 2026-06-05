package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.ui.auth.AuthViewModel.AuthUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _email    = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _displayName = MutableStateFlow("")
    val displayName: StateFlow<String> = _displayName.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()
    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val loginState: StateFlow<AuthUiState> = _loginState.asStateFlow()
    fun onLoginClick() {
        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            delay(800)
            if (_email.value.isNotBlank() && _password.value.isNotBlank()) {
                _loginState.value = AuthUiState.Success
            } else {
                _loginState.value = AuthUiState.Error(edu.metrostate.ics342.mediatracker.R.string.error_empty_credentials)
            }
        }
    }
    fun onDisplayNameChange(value: String) {_displayName.value = value}

    fun onUserNameChange(value: String) {_userName.value = value}

    fun onEmailChange(value: String)    { _email.value    = value }
    fun onPasswordChange(value: String) { _password.value = value }
}