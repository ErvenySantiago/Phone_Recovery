package com.uni7.estagio.antifurto.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.uni7.estagio.antifurto.funcionalidade.AudioRecorder;
import com.uni7.estagio.antifurto.funcionalidade.GPS;


/**
 * Created by Breno Luan on 22/05/2017.
 */

public class SelecionarFuncionalidade extends Service {
    String telefone;
    String mensagem;
    Context context;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        telefone = intent.getStringExtra("telefone");
        mensagem = intent.getStringExtra("mensagem");
        Log.i("Telefone Selecionar", telefone);
        Log.i("Mensagem Selecionar", mensagem);

        if(mensagem.equals("1")){
            GPS gps = new GPS(context);
            Double latitude;
            Double longitude;
            String texto = "PROBLEMAS EM ECONTRAR LOCALIZAÇÃO! TENTE NOVAMENTE";
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                texto = "Latitude: " + gps.getLatitude();
                texto += " Longitude: " + gps.getLongitude();
            }
            Intent it = new Intent("SEND_SMS");
            it.putExtra("telefone",telefone);
            it.putExtra("texto",texto);
            startService(it);
        }else if(mensagem.equals("2")){
            AudioRecorder audio = new AudioRecorder();
            audio.gravar();
            Intent it = new Intent("SEND_EMAIL");
            it.putExtra("anexo",audio.mFileName);
            context.startService(it);
        }else if(mensagem.equals("3")){
            String imei;
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
            Intent it = new Intent("SEND_SMS");
            it.putExtra("telefone",telefone);
            it.putExtra("texto",imei);
            startService(it);
        }else{
            Log.i("Telefone", telefone);
            Log.i("Mensagem", mensagem);
            Log.i("Funcionalidade", "Nenhuma funcionalidade encontrada!");
            Toast.makeText(context,
                "Nenhuma funcionalidade encontrada!",
                Toast.LENGTH_LONG).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
