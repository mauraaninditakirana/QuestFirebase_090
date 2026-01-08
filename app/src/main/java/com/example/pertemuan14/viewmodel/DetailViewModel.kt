@file:OptIn(InternalSerializationApi::class)

package com.example.pertemuan14.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.modeldata.Siswa
import com.example.pertemuan14.repositori.RepositorySiswa
import com.example.pertemuan14.view.route.DestinasiDetail
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import java.io.IOException

sealed interface StatusUIDetail {
    data class Success(val satusiswa: Siswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    private val idSiswa: Long =
        savedStateHandle.get<String>(DestinasiDetail.itemIdArg)?.toLong()
            ?: throw IllegalArgumentException("idSiswa tidak ditemukan di SavedStateHandle")

    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    private fun getSatuSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                val siswa = repositorySiswa.getSatuSiswa(idSiswa)
                    ?: throw IOException("Data siswa tidak ditemukan")

                StatusUIDetail.Success(satusiswa = siswa)
            } catch (e: Exception) {
                StatusUIDetail.Error
            }
        }
    }

    fun hapusSatuSiswa() {
        viewModelScope.launch {
            try {
                repositorySiswa.hapusSatuSiswa(idSiswa)
                println("Sukses Hapus Data: $idSiswa")
            } catch (e: Exception) {
                println("Gagal Hapus Data: ${e.message}")
            }
        }
    }
}
