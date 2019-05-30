package br.gov.sp.fatec.agenda.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.agenda.model.Aluno;

public class AlunoDAO {

    private final DatabaseHelper helper;

    public AlunoDAO(DatabaseHelper helper) {
        this.helper = helper;
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);
        db.insert("Aluno", null, dados);
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

//    public List<Object> buscaAlunos() {
//        String sql = "SELECT a.* FROM Aluno as a INNER JOIN Endereco as e ON (a.id_endereco = e.id);";
//        String where[] = null;
//        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, null);
//        List<Object> alunos = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            ContentValues obj = new ContentValues();
//            obj.put("id", cursor.getLong(cursor.getColumnIndex("id")));
//            obj.put("nome", cursor.getString(cursor.getColumnIndex("nome")));
//            obj.put("telefone", cursor.getString(cursor.getColumnIndex("telefone")));
//            obj.put("email", cursor.getString(cursor.getColumnIndex("email")));
//            obj.put("logradouro", cursor.getString(cursor.getColumnIndex("logradouro")));
//            obj.put("complemento", cursor.getString(cursor.getColumnIndex("complemento")));
//            obj.put("bairro", cursor.getString(cursor.getColumnIndex("bairro")));
//            obj.put("localidade", cursor.getString(cursor.getColumnIndex("localidade")));
//            obj.put("uf", cursor.getString(cursor.getColumnIndex("uf")));
//            obj.put("cep", cursor.getString(cursor.getColumnIndex("cep")));
//            alunos.add(obj);
//        }
//        cursor.close();
//
//        return alunos;
//    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Aluno;";
        SQLiteDatabase db = helper.getReadableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        db.delete("Aluno", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        String[] params = {aluno.getId().toString()};
        db.update("Aluno", dados, "id = ?", params);
    }
}

