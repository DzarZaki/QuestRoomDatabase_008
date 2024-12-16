package com.example.pertemuan9.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.pertemuan9.repository.RepositoryMhs
import com.example.pertemuan9.ui.navigation.DestinasiDetail
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class DetailMhsviewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,
) : ViewModel() {
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    val detailUiState: StateFlow<DetailUiState> = repositoryMhs.getMhs(_nim)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
}