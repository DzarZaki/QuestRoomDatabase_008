package com.example.pertemuan9.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan9.data.entity.Mahasiswa
import com.example.pertemuan9.repository.RepositoryMhs
import kotlinx.coroutines.launch
import java.lang.Exception

class MahasiswaViewModel(
    private val repositoryMhs: RepositoryMhs
) : ViewModel(){

    var uistate by mutableStateOf(MhsUIState())

    //memperbarui state berdasarkan input pengguna
    fun updateState (mahasiswaEvent: MahasiswaEvent){
        uistate = uistate.copy(
            mahasiswaEvent = mahasiswaEvent
        )
    }
    // Validasi data input
    private fun validasiFields(): Boolean {
        val event = uistate.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jeniskelamin = if (event.jeniskelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",
        )

        uistate=uistate.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data ke repository
    fun saveData(){
        val currencyEvent = uistate.mahasiswaEvent

        if (validasiFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.inserths(currencyEvent.toMahasiswaEntity())
                    uistate = uistate.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(),// Reset input form
                        isEntryValid =  FormErrorState()// Reset error state
                    )
                } catch (e: Exception) {
                    uistate=uistate.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        }else {
            uistate =uistate.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda"
            )
        }
    }
    //
    fun resetSnackBarMessage() {
        uistate=uistate.copy(
            snackBarMessage = null)
    }
}

data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jeniskelamin: String? =null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null
){
    fun isValid(): Boolean{
        return nim == null
                && nama == null
                && jeniskelamin == null
                && alamat == null
                && kelas == null
                && angkatan == null
    }
}

// data class Variabel yang menyimpan data input form

data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jeniskelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    )

// Menyimpan input form ke dalam entity

fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jeniskelamin = jeniskelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan,
)