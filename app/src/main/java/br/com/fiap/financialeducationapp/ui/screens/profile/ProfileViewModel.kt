package br.com.fiap.financialeducationapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.User
import br.com.fiap.financialeducationapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val currentUser = userRepository.getUser()
            _user.value = currentUser ?: createDefaultUser()
        }
    }

    private suspend fun createDefaultUser(): User {
        val defaultUser = User(
            id = UUID.randomUUID().toString(),
            name = "Usuário",
            email = "usuario@exemplo.com",
            incomeRange = "1000-3000",
            financialGoals = listOf("Economizar", "Investir")
        )
        userRepository.saveUser(defaultUser)
        return defaultUser
    }

    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            userRepository.updateUser(updatedUser)
            _user.value = updatedUser
        }
    }
}