package com.uni7.estagio.antifurto.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.uni7.estagio.antifurto.funcionalidade.AudioRecorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Breno Luan on 20/05/2017.
 */

public class SmsService extends Service {
    public static Short SMS_PORT = 8091;

    SmsManager smsManager;
    String telefone;
    String mensagem;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        telefone = intent.getStringExtra("telefone");
        mensagem = intent.getStringExtra("texto");

        Log.i("Telefone SMS", telefone);
        Log.i("Mensagem SMS", mensagem);
        SmsManager smsManager = SmsManager.getDefault();
        sendTextSms(telefone, mensagem);

        return super.onStartCommand(intent, flags, startId);
    }


    public void sendTextSms(String telefone, String texto){

       smsManager = SmsManager.getDefault();
       smsManager.sendMultipartTextMessage(telefone, null, smsManager.divideMessage(texto), null, null);

    }

}
