package com.example.atividade23_11

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class AtividadePrincipal : AppCompatActivity() {

    private lateinit var receptorEnergia: MonitorEnergiaReceiver
    private lateinit var textoMensagem: TextView

    override fun onCreate(instanciaSalva: Bundle?) {
        super.onCreate(instanciaSalva)
        setContentView(R.layout.activity_main)

        textoMensagem = findViewById(R.id.tvMensagem)
        textoMensagem.text = "Monitorando o estado de energia do dispositivo."

        // Inicializando e registrando o BroadcastReceiver
        receptorEnergia = MonitorEnergiaReceiver()
        val filtroIntencao = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(receptorEnergia, filtroIntencao)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receptorEnergia)
    }
}
