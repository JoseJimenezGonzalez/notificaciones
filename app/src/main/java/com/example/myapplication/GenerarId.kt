package com.example.myapplication

import java.util.concurrent.atomic.AtomicInteger

class GenerarId {
    companion object{
        val APP_ID = "com.example.myapplication"
        val CHANNEL_ID = "${APP_ID}_c1"
        var id = AtomicInteger(0)

        fun crearIdNotificacion(): Int = id.incrementAndGet()
    }
}