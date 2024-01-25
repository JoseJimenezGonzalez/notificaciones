package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.myapplication.databinding.ActivitySegundoEjercicioBinding
import com.example.myapplication.databinding.ActivityTercerEjercicioBinding

class TercerEjercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTercerEjercicioBinding

    private var iconoSeleccionado = "";
    private var imagenSeleccionada = "";
    private val opcionesIconoSeleccionado = arrayOf(
        "Estrella",
        "Correo",
        "Borrar"
    )
    private val opcionesImagenSeleccionado = arrayOf(
        "Universo",
        "Celulas",
        "Sol"
    )

    private val mapaNombresIconos = mapOf(
        "Estrella" to android.R.drawable.btn_star,
        "Correo" to android.R.drawable.ic_dialog_email,
        "Borrar" to android.R.drawable.ic_delete,
    )

    private val mapaNombreImagenes = mapOf(
        "Universo" to R.drawable.universo,
        "Celulas" to R.drawable.foto_celulas,
        "Sol" to R.drawable.img_sol,
    )

    private var numeroBotones = 0;

    private lateinit var arrayNombres :Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTercerEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        configurarOpcionesPredeterminadasSpinner()
        configurarSpinnerIcono()
        configurarSpinnerImagen()
        configurarBotonesNumeroBotones()
        configurarBotonEnviarNotificacion()
    }

    private fun configurarOpcionesPredeterminadasSpinner() {
        iconoSeleccionado = opcionesIconoSeleccionado[0]
        imagenSeleccionada = opcionesImagenSeleccionado[0]
    }

    private fun configurarBotonEnviarNotificacion() {
        binding.btnEnviarNotificacion.setOnClickListener {
            val titulo = binding.tietTitulo.text.toString()
            val texto = binding.tietTexto.text.toString()
            val nombresBotones = binding.tietNombreBotones.text.toString()
            obtenerNombresDesdeString(nombresBotones)
            val imagen = mapaNombreImagenes[imagenSeleccionada]
            val icono = mapaNombresIconos[iconoSeleccionado]

            if(titulo.isEmpty()){
                Toast.makeText(this, "falta el titulo", Toast.LENGTH_SHORT).show()
            }else if(texto.isEmpty()){
                Toast.makeText(this, "falta el texto", Toast.LENGTH_SHORT).show()
            }else if(numeroBotones > 3 || numeroBotones < 0){
                Toast.makeText(this, "El numero de botones tiene que estar entre 0 y 3", Toast.LENGTH_SHORT).show()
            }else{
                val idGenerada = GenerarId.crearIdNotificacion()

                val intent = Intent(this, MainActivity::class.java).apply{
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Para evitar bugs en la aplicacion
                }
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

                val largeIconBitmap = BitmapFactory.decodeResource(resources, imagen!!)

                val bigPictureBitmap = BitmapFactory.decodeResource(resources, imagen!!)//imagen mas grande


                val builder = NotificationCompat.Builder(this, GenerarId.CHANNEL_ID)
                    .setSmallIcon(icono!!)//Icono pequeño
                    .setContentTitle(titulo)//Titulo de la notificacion
                    .setLargeIcon(largeIconBitmap)
                    .setContentText(texto)//texto
                    //Se muestra o bigTextStyle o bigPictureStyle, no se pueden mostrar ambos de forma concurrente
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigPictureBitmap))
                    .setContentIntent(pendingIntent)

                for (i in 0 until numeroBotones){
                    builder.addAction(android.R.drawable.btn_radio, arrayNombres[i], pendingIntent)
                }


                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(idGenerada, builder.build())
            }
        }
    }
    private fun configurarBotonesNumeroBotones() {
        binding.btnMinus.setOnClickListener {
            numeroBotones -= 1
            binding.tvNumBotones.text = numeroBotones.toString()
        }
        binding.btnPlus.setOnClickListener {
            numeroBotones += 1
            binding.tvNumBotones.text = numeroBotones.toString()
        }
    }

    private fun configurarSpinnerIcono() {
        //Configurar el spinner de edades
        //Obtenemos el spinner
        val spinnerIcono = binding.spinnerIcono
        //Creamos un array con el que vamos a inflar al spinner

        // Crear un ArrayAdapter utilizando la lista de opciones y el diseño predeterminado
        // Asegúrate de que el contexto no sea nulo antes de crear el ArrayAdapter
        val adaptadorIcono = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcionesIconoSeleccionado)

        // Aplicar el adaptador al Spinner
        spinnerIcono.adapter = adaptadorIcono

        // Configurar un listener para manejar la selección de elementos en el Spinner
        spinnerIcono.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Obtener la opción seleccionada
                val selectedItem = opcionesIconoSeleccionado[position]

                // Asignar la opción seleccionada
                iconoSeleccionado = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar caso en que no se selecciona nada
            }
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
    private fun obtenerNombresDesdeString(input: String){
        // Dividir el string usando la coma como separador y eliminar espacios en blanco alrededor de cada nombre
        arrayNombres = input.split(',').map { it.trim() }.toTypedArray()
    }

    private fun configurarSpinnerImagen() {
        //Configurar el spinner de edades
        //Obtenemos el spinner
        val spinnerImagen = binding.spinnerImagen
        //Creamos un array con el que vamos a inflar al spinner

        // Crear un ArrayAdapter utilizando la lista de opciones y el diseño predeterminado
        // Asegúrate de que el contexto no sea nulo antes de crear el ArrayAdapter
        val adaptadorImagen = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcionesImagenSeleccionado)

        // Aplicar el adaptador al Spinner
        spinnerImagen.adapter = adaptadorImagen

        // Configurar un listener para manejar la selección de elementos en el Spinner
        spinnerImagen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Obtener la opción seleccionada
                val selectedItem = opcionesImagenSeleccionado[position]

                // Asignar la opción seleccionada
                imagenSeleccionada = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar caso en que no se selecciona nada
            }
        }
    }
}