package com.example.pertemuan9.repository

import com.example.pertemuan9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow


interface RepositoryMhs {
    suspend fun inserths(mahasiswa: Mahasiswa)

    fun getAlths(): Flow<List<Mahasiswa>>

    fun gechs(nim: String): Flow<Mahasiswa>

    suspend fun deletths(mahasiswa: Mahasiswa)

    suspend fun updatths(mahasiswa: Mahasiswa)
}