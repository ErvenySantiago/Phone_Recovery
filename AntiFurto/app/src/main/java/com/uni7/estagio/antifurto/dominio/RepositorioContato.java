package com.uni7.estagio.antifurto.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.uni7.estagio.antifurto.dominio.entidades.Contato;

/**
 * Created by Erveny on 20/05/2017.
 */

public class RepositorioContato {

    private SQLiteDatabase conn;

    public RepositorioContato(SQLiteDatabase conn){
        this.conn = conn;
    }

    private ContentValues preencherContentsValues(Contato contato){

        ContentValues values = new ContentValues();

        values.put(Contato.NOME,contato.getNome());
        values.put(Contato.TELEFONE,contato.getTelefone());
        values.put(Contato.EMAIL,contato.getEmail());
        values.put(Contato.PASSWORD,contato.getPassword());

        return values;

    }

    public void excluir(long id){
        conn.delete(Contato.TABELA,"_id = ?",new String[]{String.valueOf(id)});
    }

    public void inserir(Contato contato){

        ContentValues values = preencherContentsValues(contato);
        conn.insertOrThrow(Contato.TABELA,null,values);


    }

    public void alterar(Contato contato){

        ContentValues values = preencherContentsValues(contato);
        conn.update(Contato.TABELA,values,"_id = ?",new String[]{String.valueOf(contato.getId())});


    }

    public ArrayAdapter<Contato> buscaContatos(Context context){
        ArrayAdapter<Contato> adpContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_expandable_list_item_1);

        Cursor cursor = conn.query(Contato.TABELA,null,null,null,null,null,null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do {

                Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndex(contato.NOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndex(contato.TELEFONE)));
                contato.setEmail(cursor.getString(cursor.getColumnIndex(contato.EMAIL)));
                contato.setPassword(cursor.getString(cursor.getColumnIndex(contato.PASSWORD)));


                adpContatos.add(contato);
            }while (cursor.moveToNext());

        }
        return adpContatos;
    }

    public Contato findByTelefone(String telefone){
//        ArrayAdapter<Contato> adpContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_expandable_list_item_1);
        Contato contato = null;
        String selection = Contato.TELEFONE + "= '" + telefone + "'";
        Log.i("BD param", telefone);
        Cursor cursor = conn.query(Contato.TABELA,null,selection,null,null,null,null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            contato = new Contato();
            contato.setId(cursor.getLong(cursor.getColumnIndex(contato.ID)));
            contato.setNome(cursor.getString(cursor.getColumnIndex(contato.NOME)));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex(contato.TELEFONE)));
            contato.setEmail(cursor.getString(cursor.getColumnIndex(contato.EMAIL)));
            contato.setPassword(cursor.getString(cursor.getColumnIndex(contato.PASSWORD)));
        }

        return contato;
    }

    public Boolean existe(String telefone){
        Boolean existe = false;
        Contato contato = findByTelefone(telefone);
        if(contato != null){
            existe = true;
        }

        return existe;
    }

}
