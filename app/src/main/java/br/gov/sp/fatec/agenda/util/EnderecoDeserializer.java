package br.gov.sp.fatec.agenda.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.gov.sp.fatec.agenda.model.Endereco;

public class EnderecoDeserializer implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement element = json.getAsJsonObject();

        if (json.getAsJsonObject() != null) {
            element = json.getAsJsonObject();
        }

        return (new Gson().fromJson(element, Endereco.class));
    }
}
