package com.example.pertemuan9.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan9.data.entity.Mahasiswa
import com.example.pertemuan9.repository.RepositoryMhs
import kotlinx.coroutines.launch

class MahasiswaViewModel(private val repositoryMhs: RepositoryMhs) : ViewModel(){

    var uistate by mutableStateOf(MhsUIState())

    //memperbarui state berdasarkan input pengguna
    fun updateState (mahasiswaEvent: MahasiswaEvent){
        uistate = uistate.copy(
            mahasiswaEvent = mahasiswaEvent
        )
    }

    private fun validateFields(): Boolean{
        val event = uistate.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong"
        )

        uistate = uistate.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uistate.mahasiswaEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())
                    uistate = uistate.copy(
                        snackBarMassage = "Data berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(), //Reset input form
                        isEntryValid = FormErrorState() //Reset error state
                    )
                } catch (e: Exception){
                    uistate = uistate.copy(
                        snackBarMassage = "Data gagal disimpan"
                    )
                }
            }
        } else{
            uistate = uistate.copy(
                snackBarMassage = "Input tidak valid, Periksa kembali data Anda"
            )
        }
    }

    fun resetSnackBarMessage(){
        uistate = uistate.copy(snackBarMassage = null)
    }
}

data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMassage: String? = null,
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
){
    fun isValid(): Boolean{
        return nim == null
                && nama == null
                && jenisKelamin == null
                && alamat == null
                && kelas == null
                && angkatan == null
    }
}

// data class Variabel yang menyimpan data input form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
)

fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jeniskelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)