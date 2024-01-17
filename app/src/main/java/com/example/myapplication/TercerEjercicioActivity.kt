package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivitySegundoEjercicioBinding
import com.example.myapplication.databinding.ActivityTercerEjercicioBinding

class TercerEjercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTercerEjercicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTercerEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}