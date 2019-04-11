package br.gov.sp.fatec.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import br.gov.sp.fatec.agenda.model.Usuario;

public class UsuarioDAO extends SQLiteOpenHelper {

    public UsuarioDAO(@Nullable Context context) {
        super(context, "iAcad", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +
                "Usuarios (id INTEGER PRIMARY KEY, nome TEXT, email TEXT, senha TEXT, telefone TEXT, descricao TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Usuarios";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoUsuario(usuario);
    }

    private ContentValues pegaDadosDoUsuario(Usuario usuario) {
        ContentValues dados = new ContentValues();
        dados.put("nome", usuario.getNome());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        dados.put("telefone", usuario.getTelefone());
        dados.put("descricao", usuario.getDescricao());
        return dados;
    }
}
