package br.gov.sp.fatec.agenda.service;

import br.gov.sp.fatec.agenda.model.Endereco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRetrofitService {

    String BASE_URL = "https://viacep.com.br/ws/";

    @GET("{CEP}/json")
    Call<Endereco> getEnderecoByCep(@Path("CEP") String CEP);
}
