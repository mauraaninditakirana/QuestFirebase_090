package com.example.pertemuan14.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan14.repositori.AplikasiDataSiswa

fun CreationExtras.aplikasiDataSiswa(): AplikasiDataSiswa = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                AplikasiDataSiswa
        )

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiDataSiswa().container.repositorySiswa) }
        initializer { EntryViewModel(aplikasiDataSiswa().container.repositorySiswa) }
        initializer { DetailViewModel(this.createSavedStateHandle(),
            aplikasiDataSiswa().container.repositorySiswa) }
        initializer { EditViewModel(this.createSavedStateHandle(),
            aplikasiDataSiswa().container.repositorySiswa) }
    }
}