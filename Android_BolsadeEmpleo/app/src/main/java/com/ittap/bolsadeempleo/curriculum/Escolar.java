package com.ittap.bolsadeempleo.curriculum;

import com.ittap.bolsadeempleo.api.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



public class Escolar extends BaseModel {
    public static final String TABLE_NAME = "escolares";
    public static int id_usuario = 0;

    public int id;
    public int no_nivel;
    public String escuela;
    public String documento;
    public String profescion;
    public String fecha_ini;
    public String fecha_fin;

    public Escolar()
    {
    }

    @Override
    public void setFromJson(JSONObject jsonObject) throws JSONException {
        id          = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
        id_usuario  = jsonObject.has("id_usuario") ? jsonObject.getInt("id_usuario") : 0;
        no_nivel = jsonObject.has("no_nivel") ? jsonObject.getInt("no_nivel") : 0;
        escuela      = jsonObject.has("escuela") ? jsonObject.getString("escuela") : "";
        documento = jsonObject.has("documento") ? jsonObject.getString("documento") : "";
        profescion = jsonObject.has("profesion") ? jsonObject.getString("profesion") : "";
        fecha_ini = jsonObject.has("fecha_ini") ? jsonObject.getString("fecha_ini") : "";
        fecha_fin = jsonObject.has("fecha_fin") ? jsonObject.getString("fecha_fin") : "";
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
        return escuela;
    }

    @Override
    public HashMap<String,String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("tabla", TABLE_NAME);
        hashMap.put("id_usuario", String.valueOf(id_usuario));
        if (id != 0) hashMap.put("id", String.valueOf(id));

        hashMap.put("no_nivel", String.valueOf(no_nivel));
        hashMap.put("escuela", escuela);
        hashMap.put("documento", documento);
        hashMap.put("profescion", profescion);
        hashMap.put("fecha_ini", fecha_ini);
        hashMap.put("fecha_fin", fecha_fin);
        return hashMap;
    }
}
