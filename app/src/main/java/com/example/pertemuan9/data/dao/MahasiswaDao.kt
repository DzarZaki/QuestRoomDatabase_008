package com.example.pertemuan9.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pertemuan9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {

    @Insert
    suspend fun insertMahasiswa(mahasiswa:  Mahasiswa)

    @Query("SELECT * FROM mahasiswa ORDER BY nama ASC")
    fun getAllMahasiswa(): Flow<List<Mahasiswa>>

    @Query("SELECT * FROM mahasiswa WHERE nim = :nim")
    fun getMahasiswa(nim: String): Flow<Mahasiswa>

    @Delete
    suspend fun deleteMahasiswa(mahasiswa: Mahasiswa)

    @Update
    suspend fun updateMahasiswa(mahasiswa: Mahasiswa)
}