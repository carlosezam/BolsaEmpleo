package com.ittap.bolsadeempleo.curriculum;

import com.ittap.bolsadeempleo.api.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Trabajo extends BaseModel {
    public static final String TABLE_NAME = "trabajos";
    public static int id_usuario = 0;

    public int id;

    public String empresa;
    public String puesto;
    public String actividades;
    public String fecha_ini;
    public String fecha_fin;


    public Trabajo()
    {
    }

    @Override
    public void setFromJson(JSONObject jsonObject) throws JSONException {
        id          = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
        id_usuario  = jsonObject.has("id_usuario") ? jsonObject.getInt("id_usuario") : 0;
        empresa      = jsonObject.has("empresa") ? jsonObject.getString("empresa") : "";
        puesto = jsonObject.has("puesto") ? jsonObject.getString("puesto") : "";
        actividades = jsonObject.has("actividades") ? jsonObject.getString("actividades") : "";
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
        return puesto;
    }

    @Override
    public HashMap<String,String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("tabla", TABLE_NAME);
        hashMap.put("id_usuario", String.valueOf(id_usuario));
        if (id != 0) hashMap.put("id", String.valueOf(id));

        hashMap.put("empresa", empresa);
        hashMap.put("puesto", puesto);
        hashMap.put("actividades", actividades);
        hashMap.put("fecha_ini", fecha_ini);
        hashMap.put("fecha_fin", fecha_fin);
        return hashMap;
    }
}
