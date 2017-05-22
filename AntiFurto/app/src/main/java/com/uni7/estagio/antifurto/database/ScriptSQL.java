package com.uni7.estagio.antifurto.database;

/**
 * Created by Erveny on 20/05/2017.
 */

public class ScriptSQL {

    public static String getCreateContato(){

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CONTATO ( ");
        sqlBuilder.append("_id        INTEGER         NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME VARCHAR(100), ");
        sqlBuilder.append("TELEFONE VARCHAR(14), ");
        sqlBuilder.append("EMAIL VARCHAR(50), ");
        sqlBuilder.append("PASSWORD VARCHAR(30) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateCodigo(){

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CODIGO ( ");
        sqlBuilder.append("_id        INTEGER         NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("COD INTEGER, ");
        sqlBuilder.append("DESCRICAO VARCHAR(50) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

}
