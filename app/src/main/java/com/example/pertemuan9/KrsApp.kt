package com.example.pertemuan9

import android.app.Application
import com.example.pertemuan9.data.dependenciesinjection.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp // Fungsinya untuk menyimpan instance
    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this) // Membuat instance ContainerApp
    }   // instance adalah object yang dibuat dari class
}