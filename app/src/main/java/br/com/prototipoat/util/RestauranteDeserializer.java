package br.com.prototipoat.util;

//import com.google.gson.Gson;
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParseException;
//
//import java.lang.reflect.Type;
//
//import br.com.prototipoat.model.Restaurante;
//
///**
// * Created by 'Thiago on 28/09/2016.
// */
//
//public class RestauranteDeserializer implements JsonDeserializer<Object> {
//    @Override
//    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonElement restaurante = json.getAsJsonObject();
//
//        if( json.getAsJsonObject().get("restaurante") != null){
//            restaurante = json.getAsJsonObject().get("restaurante");
//        }
//        return (new Gson().fromJson(restaurante, Restaurante.class));
//    }
//}
