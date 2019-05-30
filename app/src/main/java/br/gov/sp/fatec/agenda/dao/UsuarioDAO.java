package br.gov.sp.fatec.agenda.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.agenda.model.Usuario;

public class UsuarioDAO {

    private final DatabaseHelper helper;

    private String TempPassword = "NOT_FOUND";

    public UsuarioDAO(DatabaseHelper helper) {
        this.helper = helper;
    }

    public void insere(Usuario usuario) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dados = pegaDadosDoUsuario(usuario);
        db.insert("Usuario", null, dados);
    }

    public List<Usuario> buscaUsuarios() {
        String sql = "SELECT * FROM Usuario;";
        SQLiteDatabase db = helper.getReadableDatabase();
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

    public boolean buscaParaLogar(String email, String senha) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("Usuario", null, " " + "email" + "=?", new String[]{email}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();

                TempPassword = cursor.getString(cursor.getColumnIndex("senha"));
            }
        }
        db.close();
        return TempPassword.equalsIgnoreCase(senha);
    }

    @NonNull
    private ContentValues pegaDadosDoUsuario(Usuario usuario) {
        ContentValues dados = new ContentValues();
        dados.put("nome", usuario.getNome());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        dados.put("telefone", usuario.getTelefone());
        return dados;
    }
}
