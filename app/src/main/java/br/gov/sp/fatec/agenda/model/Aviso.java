package br.gov.sp.fatec.agenda.model;

import java.io.Serializable;

public class Aviso implements Serializable {

    private final String titulo;
    private final String descricao;

    public Aviso(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
