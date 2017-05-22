package com.uni7.estagio.antifurto.dominio.entidades;

import java.io.Serializable;

/**
 * Created by Erveny on 20/05/2017.
 */

public class Contato implements Serializable{

    public static String TABELA = "CONTATO";
    public static String ID = "_id";
    public static String NOME = "NOME";
    public static String TELEFONE = "TELEFONE";
    public static String EMAIL = "EMAIL";
    public static String PASSWORD = "PASSWORD";


    private long id;
    private String nome;
    private String telefone;
    private String email;
    private String password;

    public Contato(){
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return nome + " " + telefone;
    }

}
