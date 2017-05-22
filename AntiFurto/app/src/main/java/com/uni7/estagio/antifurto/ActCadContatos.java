package com.uni7.estagio.antifurto;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.uni7.estagio.antifurto.app.MessageBox;
import com.uni7.estagio.antifurto.database.DataBase;
import com.uni7.estagio.antifurto.dominio.RepositorioContato;
import com.uni7.estagio.antifurto.dominio.entidades.Contato;

public class ActCadContatos extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtPassword;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cad_contatos);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtTelefone = (EditText)findViewById(R.id.edtTelefone);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        Bundle bundle = getIntent().getExtras();

        if((bundle!=null) && (bundle.containsKey(ActContato.PAR_CONTATO))){

            contato = (Contato)bundle.getSerializable(ActContato.PAR_CONTATO);
            preenherDados();

        }else {

            contato = new Contato();
        }

        try {

            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();

            repositorioContato = new RepositorioContato(conn);

        }catch (SQLException ex){

            MessageBox.show(this,"ERRO","Erro ao criar o banco: " + ex.getMessage());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_contatos,menu);



        if(contato.getId() != 0)
            menu.getItem(1).setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

    private void preenherDados(){

        edtNome.setText(contato.getNome());
        edtTelefone.setText(contato.getTelefone());
        edtEmail.setText(contato.getEmail());
        edtPassword.setText(contato.getPassword());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mni_acao1:

                salvar();

                finish();
                break;
            case R.id.mni_acao2:

                excluir();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void excluir(){
        try{

            repositorioContato.excluir(contato.getId());

        }catch (Exception ex){

            MessageBox.show(this,"ERRO","Erro ao excluir o contato: " + ex.getMessage());

        }

    }

    private void salvar(){
        try{


            contato.setNome(edtNome.getText().toString());
            contato.setTelefone(edtTelefone.getText().toString());
            contato.setEmail(edtEmail.getText().toString());
            contato.setPassword(edtPassword.getText().toString());

            if (contato.getId() == 0){
                repositorioContato.inserir(contato);
            }else
                repositorioContato.alterar(contato);

        }catch (Exception ex){

            MessageBox.show(this,"ERRO","Erro ao salvar os dados: " + ex.getMessage());

        }
    }
}
