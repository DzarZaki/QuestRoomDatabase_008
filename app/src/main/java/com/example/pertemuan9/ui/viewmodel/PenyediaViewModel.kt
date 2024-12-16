package com.example.pertemuan9.ui.viewmodel

import HomeMhsViewModel
import android.text.Spannable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan9.KrsApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            MahasiswaViewModel(
                krsApp().containerApp.repositoryMhs
            )
        }
        initializer {
            HomeMhsViewModel(
                krsApp().containerApp.repositoryMhs
            )
        }
        initializer {
            DetailMhsViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMhs
            )
        }
        initializer {
            UpdateMhsViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMhs
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)

