package br.gov.sp.fatec.agenda.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.gov.sp.fatec.agenda.model.Endereco;
import br.gov.sp.fatec.agenda.model.SimpleCallback;
import br.gov.sp.fatec.agenda.util.EnderecoDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnderecoService {

    private Context context;
    private ApiRetrofitService service;

    public EnderecoService(Context context) {
        this.context = context;
        inicia();
    }

    private void inicia() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Endereco.class, new EnderecoDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiRetrofitService.class);

        final ApiRetrofitService service = retrofit.create(ApiRetrofitService.class);
    }

    public void getCep(String CEP, final SimpleCallback<Endereco> callback) {
        service.getEnderecoByCep(CEP).enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(response.body());
                } else {
                    if (response.body() != null) {
                        callback.onError("erro");
                    } else {
                        callback.onError("erro");
                    }
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t.getMessage());
            }
        });
    }

}
