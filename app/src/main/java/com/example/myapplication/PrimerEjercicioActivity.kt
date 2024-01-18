package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.myapplication.databinding.ActivityPrimerEjercicioBinding

class PrimerEjercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrimerEjercicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrimerEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerarNotificacion.setOnClickListener {

            createNotificationChannel()

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_adb)
                .setContentTitle("Notificación del ejercicio 1")
                .setStyle(NotificationCompat.BigTextStyle().bigText("Nos invaden los alienígenas!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher_foreground, "Ir a la actividad principal", pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, builder.build())
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