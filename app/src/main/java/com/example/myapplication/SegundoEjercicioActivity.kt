package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityPrimerEjercicioBinding
import com.example.myapplication.databinding.ActivitySegundoEjercicioBinding

class SegundoEjercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySegundoEjercicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySegundoEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerarNotificacion.setOnClickListener {
            createNotificationChannel()

            val builder = NotificationCompat.Builder(this, PrimerEjercicioActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.burger)
                .setContentTitle("Notificación del ejercicio 2")
                .setStyle(NotificationCompat.BigTextStyle().bigText("Ya tiene su hamburguesa lista!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setColor(Color.GREEN)//Cambia el color del icono

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(PrimerEjercicioActivity.NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "TestChannel"
        val descriptionText = "TestDescription"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = descriptionText
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }
}