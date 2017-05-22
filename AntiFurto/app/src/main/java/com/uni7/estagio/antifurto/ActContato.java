package com.uni7.estagio.antifurto;

/**
 * Created by Erveny on 21/05/2017.
 */

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import android.database.sqlite.*;
import android.database.*;

import com.uni7.estagio.antifurto.app.MessageBox;
import com.uni7.estagio.antifurto.database.DataBase;
import com.uni7.estagio.antifurto.dominio.RepositorioContato;
import com.uni7.estagio.antifurto.dominio.entidades.Contato;


public class ActContato extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ImageButton btnAdd;
    private ListView lstContatos;
    private ArrayAdapter<Contato> adpContatos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;

    public static final String PAR_CONTATO = "CONTATO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (ImageButton)findViewById(R.id.bntAdd);
        lstContatos = (ListView)findViewById(R.id.lstContatos);

        btnAdd.setOnClickListener(this);
        lstContatos.setOnItemClickListener(this);


        try {

            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();

            repositorioContato = new RepositorioContato(conn);

            adpContatos = repositorioContato.buscaContatos(this);

            lstContatos.setAdapter(adpContatos);

        }catch (SQLException ex){

            MessageBox.show(this,"ERRO","Erro ao criar o banco: " + ex.getMessage());

        }
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this,ActCadContatos.class);
        startActivityForResult(it,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        adpContatos = repositorioContato.buscaContatos(this);

        lstContatos.setAdapter(adpContatos);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contato contato = adpContatos.getItem(position);

        Intent it = new Intent(this,ActCadContatos.class);

        it.putExtra(PAR_CONTATO,contato);

        startActivityForResult(it,0);
    }
}
