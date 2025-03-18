package br.com.fiap.financialeducationapp.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        // Criar um usuário de exemplo
        val defaultUser = User(
            id = UUID.randomUUID().toString(),
            name = "João Silva",
            email = "joao.silva@exemplo.com",
            incomeRange = "R$ 3.000 - R$ 5.000",
            financialGoals = listOf("Economizar para aposentadoria", "Comprar uma casa", "Investir em educação")
        )
        _user.value = defaultUser
    }

    fun updateUser(updatedUser: User) {
        _user.value = updatedUser
    }
}