package com.example.atividade23_11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class MonitorEnergiaReceiver : BroadcastReceiver() {

    override fun onReceive(contexto: Context, intent: Intent) {
        val mensagem: String = when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> "O dispositivo foi conectado à força externa."
            Intent.ACTION_POWER_DISCONNECTED -> "O dispositivo foi desconectado da força externa."
            else -> return
        }

        exibirNotificacao(contexto, mensagem)
    }

    private fun exibirNotificacao(contexto: Context, mensagem: String) {
        val canalId = "canal_notificacao_energia"
        val gerenciadorNotificacao =
            contexto.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Criando o canal de notificação (necessário para Android 8 ou superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                "Notificações de Energia",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            gerenciadorNotificacao.createNotificationChannel(canal)
        }

        // Intent para abrir a AtividadePrincipal ao clicar na notificação
        val intent = Intent(contexto, AtividadePrincipal::class.java)
        val pendente = PendingIntent.getActivity(
            contexto, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Construção da notificação
        val notificacao = NotificationCompat.Builder(contexto, canalId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Mudança no estado de energia")
            .setContentText(mensagem)
            .setContentIntent(pendente)
            .setAutoCancel(true)
            .build()

        // Exibindo a notificação
        gerenciadorNotificacao.notify(1, notificacao)
    }
}
