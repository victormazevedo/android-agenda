package br.gov.sp.fatec.agenda.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.gov.sp.fatec.agenda.model.Endereco;

public class EnderecoDAO {

    private final DatabaseHelper helper;

    public EnderecoDAO(DatabaseHelper helper) {
        this.helper = helper;
    }

    public void insere(Endereco endereco) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dados = pegaDadosDoEndereco(endereco);
        db.insert("Endereco", null, dados);
    }

    private ContentValues pegaDadosDoEndereco(Endereco endereco) {
        ContentValues dados = new ContentValues();
        dados.put("id", endereco.getId());
        dados.put("cep", endereco.getCep());
        dados.put("logradouro", endereco.getLogradouro());
        dados.put("complemento", endereco.getComplemento());
        dados.put("bairro", endereco.getBairro());
        dados.put("localidade", endereco.getLogradouro());
        dados.put("uf", endereco.getUf());

        return dados;
    }

    public Long getId() {
        Long lastId = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT ROWID from Endereco order by ROWID desc limit 1";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getLong(0);
        }

        return lastId;
    }
}
