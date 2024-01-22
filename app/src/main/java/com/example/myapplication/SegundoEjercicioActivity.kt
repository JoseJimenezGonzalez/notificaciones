package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.myapplication.databinding.ActivityPrimerEjercicioBinding

class SegundoEjercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrimerEjercicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrimerEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerarNotificacion.setOnClickListener {

            createNotificationChannel()
            val idGenerada = GenerarId.crearIdNotificacion()

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            // En tu código para construir la notificación
            val remoteViews = RemoteViews(packageName, R.layout.custom_notification_layout)
            remoteViews.setImageViewResource(R.id.notification_image, R.drawable.foto_celulas) // Reemplaza con la imagen que deseas mostrar

            val builder = NotificationCompat.Builder(this, GenerarId.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_adb)
                .setContentTitle("Título de la notificación")
                .setContentText("Texto de la notificación")
                .setCustomContentView(remoteViews) // Configura el RemoteViews personalizado
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH)

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



