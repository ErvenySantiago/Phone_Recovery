package com.uni7.estagio.antifurto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

//import android.telephony.gsm.SmsManager;
import com.uni7.estagio.antifurto.funcionalidade.AudioRecorder;

/**
 * Created by brennoluan@gmail.com on 19/03/2017.
 */

public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage sms = getMessagesFromIntent(intent)[0];
        String telefone = sms.getOriginatingAddress();
        String mensagem = sms.getMessageBody();
        Intent it = new Intent("SELECIONAR");
        Log.i("Telefone", telefone);
        Log.i("Mensagem", mensagem);
        it.putExtra("telefone",telefone);
        it.putExtra("mensagem",mensagem);
        context.startService(it);


        /*AudioRecorder audio = new AudioRecorder();
        audio.gravar();
        Intent it = new Intent("SEND_EMAIL");
        it.putExtra("anexo",audio.mFileName);
        context.startService(it);
       /* GPSTracker gps;
        SmsSend sendSMS;
        AudioRecorderTest audio;


        gps = new GPSTracker(context);
        sendSMS = new SmsSend();
//        if(telefone.equals("543")) {


          /*  Toast.makeText(context,
                    "Instanciou",
                    Toast.LENGTH_LONG).show();
            audio = new AudioRecorderTest();
            audio.gravar();
            try {
                sendSMS.sendMediaSms(telefone, sendSMS.fileToByte(audio.mFileName));
            }catch (Exception ex){
                Toast.makeText(context,
                        ex.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

//        }else {
            String texto = null;
            if (gps.canGetLocation()) {

                Double latitude = gps.getLatitude();
                Double longitude = gps.getLongitude();
                texto = "IMEI: " +getImei(context);
                Toast.makeText(context,
                        texto,
                        Toast.LENGTH_LONG).show();
            } else {
                for (String error : gps.errors) {
                    Toast.makeText(context,
                            error,
                            Toast.LENGTH_LONG).show();
                }


            }*/
//            Intent it = new Intent("SEND_SMS");
//            it.putExtra("mensagem",mensagem);
//            it.putExtra("telefone",telefone);
//            context.startService(it);

//            context.startActivity();
//            sendSMS.sendTextSms("85991346079", texto, context);


//        }
        }



    public static SmsMessage[] getMessagesFromIntent(Intent intent){

        Object[] pdusExtras = (Object[])intent.getSerializableExtra("pdus");
        SmsMessage[] messages = new SmsMessage[pdusExtras.length];

        for (int i = 0; i < pdusExtras.length; i++){
            messages[i] = SmsMessage.createFromPdu((byte[])pdusExtras[i]);
        }

        return messages;
    }


    public String getImei(Context context){
       String imei;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();
        return imei;
    }
}
