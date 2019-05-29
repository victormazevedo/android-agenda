package br.gov.sp.fatec.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.agenda.model.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(@Nullable Context context) {
        super(context, "iAcad", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + "Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, telefone TEXT, email TEXT, genero TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("telefone", aluno.getTelefone());
        dados.put("email", aluno.getEmail());
        dados.put("genero", aluno.getGenero());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            aluno.setGenero(cursor.getString(cursor.getColumnIndex("genero")));

            alunos.add(aluno);
        }
        cursor.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }
}
