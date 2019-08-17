package com.ittap.bolsadeempleo.curriculum;

import com.ittap.bolsadeempleo.api.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Lenguaje extends BaseModel {
    public static final String TABLE_NAME = "lenguajes";
    public static int id_usuario = 0;

    public int id;
    public String nombre;
    public int habla;
    public int lee;
    public int escribe;

    public Lenguaje()
    {
    }

    @Override
    public void setFromJson(JSONObject jsonObject) throws JSONException {
        id          = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
        id_usuario  = jsonObject.has("id_usuario") ? jsonObject.getInt("id_usuario") : 0;
        nombre      = jsonObject.has("nombre") ? jsonObject.getString("nombre") : "";
        habla      = jsonObject.has("nombre") ? jsonObject.getInt("habla") : 0;
        lee      = jsonObject.has("nombre") ? jsonObject.getInt("lee") : 0;
        escribe      = jsonObject.has("nombre") ? jsonObject.getInt("escribe") : 0;

    }

    @Override
    public Class getFormActivity() {
        return CompetenciaActivity.class;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public HashMap<String,String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();

        if (id != 0) hashMap.put("id", String.valueOf(id));

        hashMap.put("id_usuario", String.valueOf(id_usuario));
        hashMap.put("tabla", TABLE_NAME);
        hashMap.put("nombre", nombre);
        hashMap.put("habla", String.valueOf(habla));
        hashMap.put("lee", String.valueOf(lee));
        hashMap.put("escribe", String.valueOf(escribe));
        return hashMap;
    }
}
