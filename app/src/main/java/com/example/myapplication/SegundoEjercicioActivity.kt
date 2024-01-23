package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.myapplication.databinding.ActivityPrimerEjercicioBinding
import com.example.myapplication.databinding.ActivitySegundoEjercicioBinding

class SegundoEjercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySegundoEjercicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySegundoEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.btnBigPictureStyle.setOnClickListener {

            val idGenerada = GenerarId.crearIdNotificacion()

            val intent = Intent(this, MainActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Para evitar bugs en la aplicacion
            }
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val bigPictureBitmap = BitmapFactory.decodeResource(resources, R.drawable.img_sol)//imagen mas grande


            val builder = NotificationCompat.Builder(this, GenerarId.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_gallery)//Icono pequeño
                .setContentTitle("Captura de pantalla realizada")//Titulo de la notificacion
                .setContentText("Pulsa para ver la captura de pantalla")//texto
                //Se muestra o bigTextStyle o bigPictureStyle, no se pueden mostrar ambos de forma concurrente
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigPictureBitmap))
                .setContentIntent(pendingIntent)
                //Como mucho se le pueden meter 3 botones
                .addAction(android.R.drawable.btn_radio, "Compartir", pendingIntent)
                .addAction(android.R.drawable.btn_radio, "Borrar", pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(idGenerada, builder.build())
        }


        binding.btnBigTextStyle.setOnClickListener {

            val idGenerada = GenerarId.crearIdNotificacion()

            val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.img_sol)

            val intent = Intent(this, MainActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Para evitar bugs en la aplicacion
            }
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)



            val builder = NotificationCompat.Builder(this, GenerarId.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_gallery)//Icono pequeño
                .setContentTitle("Captura de pantalla realizada")//Titulo de la notificacion
                .setLargeIcon(largeIconBitmap) // Establecer la imagen como icono grande(aparece a la derecha)
                .setContentText("Pulsa para ver la captura de pantalla")//texto
                //Se muestra o bigTextStyle o bigPictureStyle, no se pueden mostrar ambos de forma concurrente
                .setStyle(NotificationCompat.BigTextStyle().bigText("Esto es una pedazo de descripcion del mensaje. Este es un mensaje de los grandes."))
                .setContentIntent(pendingIntent)
                //Como mucho se le pueden meter 3 botones
                .addAction(android.R.drawable.btn_radio, "Compartir", pendingIntent)
                .addAction(android.R.drawable.btn_radio, "Borrar", pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(idGenerada, builder.build())
        }
    }

    private fun createNotificationChannel() {
        val name = "TestChannel"
        val descriptionText = "TestDescription"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(GenerarId.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        // Register the channel with the system
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}



