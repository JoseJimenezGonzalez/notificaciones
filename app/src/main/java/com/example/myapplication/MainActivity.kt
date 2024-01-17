package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPrimerEjercicio.setOnClickListener {
            val intent = Intent(this,PrimerEjercicioActivity::class.java)
            startActivity(intent)
        }

        binding.btnSegundoEjercicio.setOnClickListener {
            val intent = Intent(this,SegundoEjercicioActivity::class.java)
            startActivity(intent)
        }

        binding.btnTercerEjercicio.setOnClickListener {
            val intent = Intent(this,TercerEjercicioActivity::class.java)
            startActivity(intent)
        }
    }
}