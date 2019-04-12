package br.gov.sp.fatec.agenda.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.gov.sp.fatec.agenda.model.Aviso;

public class AvisoDAO {

    private final static ArrayList<Aviso> avisos = new ArrayList<>();

    public List<Aviso> todos() {
        return (List<Aviso>) avisos.clone();
    }

    public void insere(Aviso... avisos) {
        AvisoDAO.avisos.addAll(Arrays.asList(avisos));
    }

    public void altera(int posicao, Aviso aviso) {
        avisos.set(posicao, aviso);
    }

    public void remove(int posicao) {
        avisos.remove(posicao);
    }

    public void troca(int posicaoInicio, int posicaoFim) {
        Collections.swap(avisos, posicaoInicio, posicaoFim);
    }

    public void removeTodos() {
        avisos.clear();
    }
}
