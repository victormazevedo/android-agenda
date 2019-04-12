package br.gov.sp.fatec.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.agenda.model.Usuario;

public class UsuarioDAO extends SQLiteOpenHelper {

    public UsuarioDAO(@Nullable Context context) {
        super(context, "iAcadUser", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +
                "Usuarios (id INTEGER PRIMARY KEY, nome TEXT, email TEXT, senha TEXT, telefone TEXT);";
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
        db.insert("Usuarios", null, dados);
    }

    public List<Usuario> buscaUsuarios() {
        String sql = "SELECT * FROM Usuarios;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Usuario> alunos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getLong(cursor.getColumnIndex("id")));
            usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            usuario.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            alunos.add(usuario);
        }
        cursor.close();

        return alunos;
    }

    public List<Usuario> buscaParaLogar(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email, senha FROM Usuarios where email='" + email + "' and senha='" + password + "'", null);
        List<Usuario> userSenha = new ArrayList<>();
        while (cursor.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex("senha")));

            userSenha.add(usuario);
        }
        cursor.close();

        return userSenha;
    }

    private ContentValues pegaDadosDoUsuario(Usuario usuario) {
        ContentValues dados = new ContentValues();
        dados.put("nome", usuario.getNome());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        dados.put("telefone", usuario.getTelefone());
        return dados;
    }
}
