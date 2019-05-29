package br.gov.sp.fatec.agenda.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String genero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
