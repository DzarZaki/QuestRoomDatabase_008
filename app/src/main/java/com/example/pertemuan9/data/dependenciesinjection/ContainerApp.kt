package com.example.pertemuan9.data.dependenciesinjection

import android.content.Context
import com.example.pertemuan9.data.database.KrsDatabase
import com.example.pertemuan9.repository.RepositoryMhs
import com.example.pertemuan9.repository.localrepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context:Context) : InterfaceContainerApp{
    override val repositoryMhs: RepositoryMhs by lazy {
        localrepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}