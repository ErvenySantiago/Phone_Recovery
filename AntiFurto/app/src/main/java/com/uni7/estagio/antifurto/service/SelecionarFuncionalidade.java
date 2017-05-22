package com.uni7.estagio.antifurto.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.uni7.estagio.antifurto.database.DataBase;
import com.uni7.estagio.antifurto.dominio.RepositorioContato;
import com.uni7.estagio.antifurto.funcionalidade.AudioRecorder;
import com.uni7.estagio.antifurto.funcionalidade.GPS;
import com.uni7.estagio.antifurto.funcionalidade.InformacaoAparelho;


/**
 * Created by Breno Luan on 22/05/2017.
 */

public class SelecionarFuncionalidade extends Service {
    String telefone;
    String mensagem;
    String smsTexto;
    Context context;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = new DataBase(this);
        conn = dataBase.getReadableDatabase();

        repositorioContato = new RepositorioContato(conn);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        telefone = intent.getStringExtra("telefone");
        mensagem = intent.getStringExtra("mensagem");
        smsTexto = "PROBLEMAS EM ECONTRAR LOCALIZAÇÃO! TENTE NOVAMENTE";
        Log.i("Telefone Selecionar", telefone);
        Log.i("Mensagem Selecionar", mensagem);
        Boolean teste = repositorioContato.existe(telefone);
        Log.i("EXISTE", teste.toString());
        if(repositorioContato.existe(telefone)) {

            if (mensagem.equals("1")) {
                GPS gps = new GPS(context);
                if (gps.canGetLocation()) {
                    smsTexto = gps.toString();
                }
                Intent it = new Intent("SEND_SMS");
                it.putExtra("telefone", telefone);
                it.putExtra("texto", smsTexto);
                startService(it);
            } else if (mensagem.equals("2")) {
                AudioRecorder audio = new AudioRecorder();
                audio.gravar();
                String email = repositorioContato.findByTelefone(telefone).getEmail();
                Intent it = new Intent("SEND_EMAIL");
                it.putExtra("email", email);
                it.putExtra("anexo", audio.mFileName);
                context.startService(it);
            } else if (mensagem.equals("3")) {
                InformacaoAparelho inforAparelho = new InformacaoAparelho(context);
                smsTexto = "ID Aparelho: ";
                smsTexto += inforAparelho.getIMEI();
                Intent it = new Intent("SEND_SMS");
                it.putExtra("telefone",telefone);
                it.putExtra("texto",smsTexto);
                startService(it);
            } else {
                Log.i("Telefone", telefone);
                Log.i("Mensagem", mensagem);
                Log.i("Funcionalidade", "Nenhuma funcionalidade encontrada!");
                Intent it = new Intent("SEND_SMS");
                it.putExtra("telefone", telefone);
                it.putExtra("texto", "Funcionalidade não encontrada!");
                startService(it);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
