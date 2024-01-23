package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityPrimerEjercicioBinding

class PrimerEjercicioActivity : AppCompatActivity() {
    //Hola

    private lateinit var binding: ActivityPrimerEjercicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrimerEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerarNotificacion.setOnClickListener {

            createNotificationChannel()
            val idGenerada = GenerarId.crearIdNotificacion()

            val intentBoton = Intent(this, MainActivity::class.java)
            val pendingIntentBoton = PendingIntent.getActivity(this, 0, intentBoton,
                PendingIntent.FLAG_IMMUTABLE)

            val intentNotifi = Intent(this, PrimerEjercicioActivity::class.java)
            val pendingIntentNotifi = PendingIntent.getActivity(this, 0, intentNotifi,
                PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(this, GenerarId.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_adb)
                .setContentTitle("Notificación del ejercicio 1")
                .setStyle(NotificationCompat.BigTextStyle().bigText("Nos invaden los alienígenas!\nMi id es ${idGenerada}"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntentNotifi)  // Aquí asignamos el PendingIntent al contenido de la notificación
                .addAction(R.drawable.ic_launcher_background, "Ir a la actividad principal", pendingIntentBoton)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(idGenerada, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "TestChannel"
        val descriptionText = "TestDescription"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(GenerarId.CHANNEL_ID, name, importance)
        channel.description = descriptionText
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
