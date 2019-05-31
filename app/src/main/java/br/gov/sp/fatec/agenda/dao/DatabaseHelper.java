package br.gov.sp.fatec.agenda.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iacad.db";
    private static final int DATABASE_VERSION = 4;

    private static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE Usuario ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " nome TEXT, " +
                    " email TEXT, " +
                    " senha TEXT, " +
                    " telefone TEXT " + ")";

    private static final String CREATE_TABLE_ALUNO =
            "CREATE TABLE Aluno ( " +
                    "id INTEGER PRIMARY KEY, " +
                    " nome TEXT NOT NULL, " +
                    " telefone TEXT, " +
                    " email TEXT, " +
                    " genero TEXT,  " +
                    " id_endereco INTEGER, " +
                    " FOREIGN KEY(id_endereco) REFERENCES Endereco(id) " + ")";

    private static final String CREATE_TABLE_ENDERECO =
            "CREATE TABLE Endereco ( " +
                    "id INTEGER PRIMARY KEY, " +
                    " cep TEXT NOT NULL, " +
                    " logradouro TEXT, " +
                    " complemento TEXT, " +
                    " bairro TEXT, " +
                    " localidade TEXT, " +
                    " uf TEXT " + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUNO);
        db.execSQL(CREATE_TABLE_ENDERECO);
        db.execSQL(CREATE_TABLE_USUARIO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Aluno");
        db.execSQL("DROP TABLE Endereco");
        db.execSQL("DROP TABLE Usuario");
        onCreate(db);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
