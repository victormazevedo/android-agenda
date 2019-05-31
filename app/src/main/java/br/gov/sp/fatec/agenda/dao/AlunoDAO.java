package br.gov.sp.fatec.agenda.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.agenda.dto.EnderecoAlunoDTO;
import br.gov.sp.fatec.agenda.model.Aluno;

public class AlunoDAO {

    private final DatabaseHelper helper;

    public AlunoDAO(DatabaseHelper helper) {
        this.helper = helper;
    }

    public void insere(Aluno aluno, Long enderecoId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno, enderecoId);
        db.insert("Aluno", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno, Long enderecoId) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("telefone", aluno.getTelefone());
        dados.put("email", aluno.getEmail());
        dados.put("genero", aluno.getGenero());
        dados.put("id_endereco", enderecoId);
        return dados;
    }

    public List<EnderecoAlunoDTO> buscaAlunos() {
        String sql = "SELECT a.*, e.* FROM Aluno as a INNER JOIN Endereco as e ON (a.id_endereco = e.id);";
        String where[] = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<EnderecoAlunoDTO> alunos = new ArrayList<>();
        while (cursor.moveToNext()) {
            EnderecoAlunoDTO enderecoAlunoDTO = new EnderecoAlunoDTO();
            enderecoAlunoDTO.setId_aluno(cursor.getLong(cursor.getColumnIndex("id")));
            enderecoAlunoDTO.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            enderecoAlunoDTO.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            enderecoAlunoDTO.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            enderecoAlunoDTO.setCep(cursor.getString(cursor.getColumnIndex("cep")));
            enderecoAlunoDTO.setLogradouro(cursor.getString(cursor.getColumnIndex("logradouro")));
            enderecoAlunoDTO.setComplemento(cursor.getString(cursor.getColumnIndex("complemento")));
            enderecoAlunoDTO.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
            enderecoAlunoDTO.setLocalidade(cursor.getString(cursor.getColumnIndex("localidade")));
            enderecoAlunoDTO.setUf(cursor.getString(cursor.getColumnIndex("uf")));

            alunos.add(enderecoAlunoDTO);
        }
        cursor.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        db.delete("Aluno", "id = ?", params);
    }

    public void altera(Aluno aluno, Long endercoId) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno, endercoId);

        String[] params = {aluno.getId().toString()};
        db.update("Aluno", dados, "id = ?", params);
    }
}

