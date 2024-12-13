package com.example.pertemuan9.repository

import com.example.pertemuan9.data.dao.MahasiswaDao
import com.example.pertemuan9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow


class localrepositoryMhs(
    private val mahasiswaDao: MahasiswaDao
) : RepositoryMhs{
    override suspend fun inserths(mahasiswa: Mahasiswa) {
        mahasiswaDao.insertMahasiswa(mahasiswa)

        fun getAllMhs (): Flow<List<Mahasiswa>>
    }
}