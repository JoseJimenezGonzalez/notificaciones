package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.myapplication.databinding.ActivitySegundoEjercicioBinding

class SegundoEjercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySegundoEjercicioBinding
    private var notificationIdCounter = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySegundoEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerarNotificacion.setOnClickListener {
            createNotificationChannel()

            // Intent que se lanza cuando se hace clic en la notificación
            val intent = Intent(this, SegundoEjercicioActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_MUTABLE)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.burger)
                .setContentTitle("Notificación del ejercicio 2")
                .setStyle(NotificationCompat.BigTextStyle().bigText("Ya tiene su hamburguesa lista!"))
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Cambia la prioridad a IMPORTANCE_HIGH
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Establece la visibilidad a VISIBILITY_PUBLIC
                .setAutoCancel(true)
                .setColor(Color.GREEN)
                .setContentIntent(pendingIntent) // Asigna el PendingIntent al hacer clic en la notificación
                .build()

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Lanza la notificación con un ID único cada vez que se genera una nueva notificación
            notificationManager.notify(notificationIdCounter++, notification)
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TestChannel"
            val descriptionText = "TestDescription"
            val importance = NotificationManager.IMPORTANCE_HIGH // Cambia la importancia a IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText
            channel.setShowBadge(true) // Habilita la bandera FLAG_MUTABLE
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "channelID"
    }
}


