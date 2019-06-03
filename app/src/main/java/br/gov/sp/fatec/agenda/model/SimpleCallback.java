package br.gov.sp.fatec.agenda.model;

public interface SimpleCallback<T> {
    void onResponse(T response);

    void onError(String error);
}
