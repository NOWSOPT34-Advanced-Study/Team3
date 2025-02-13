package com.sopt.now.core.view

sealed interface UiState<out T> {

    object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T
    ) : UiState<T>

    data class Failure(
        val errorMessage: String
    ) : UiState<Nothing>
}
