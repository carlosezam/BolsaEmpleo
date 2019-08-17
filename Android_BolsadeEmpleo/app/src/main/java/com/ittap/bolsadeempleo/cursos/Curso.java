package com.ittap.bolsadeempleo.cursos;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Curso {
    private static final String tabla = "cursos";
    public int id_usuario;
    private boolean is_new_record;

    public int id;
    public String nombre;
    public String descripcion;
    public String fecha_ini;
    public String fecha_fin;
    public int horas;

    public Curso( int id_usuario ) { this.id_usuario = id_usuario; };


    public Curso(JSONObject jsonObject ) throws JSONException
    {
        id = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
        id_usuario = jsonObject.has("id_usuario") ? jsonObject.getInt("id_usuario") : 0;

        nombre = jsonObject.has("nombre") ? jsonObject.getString("nombre") : "";
        descripcion = jsonObject.has("descripcion") ? jsonObject.getString("descripcion") : "";
        fecha_ini = jsonObject.has("fecha_ini") ? jsonObject.getString("fecha_ini") : "";
        fecha_fin = jsonObject.has("fecha_fin") ? jsonObject.getString("fecha_fin") : "";
        horas = jsonObject.has("horas") ? jsonObject.getInt("horas") : 0;
    }

    public HashMap<String,String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        if (id != 0) hashMap.put("id", String.valueOf(id));
        hashMap.put("id_usuario", String.valueOf(id_usuario));
        hashMap.put("tabla", tabla);

        hashMap.put("nombre", nombre);
        hashMap.put("descripcion", descripcion);
        hashMap.put("fecha_ini", fecha_ini);
        hashMap.put("fecha_fin", fecha_fin);
        hashMap.put("horas", String.valueOf(horas));

        return hashMap;
    }

    public HashMap<String,String> getReadApiParams()
    {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id_usuario", String.valueOf(id_usuario));
        hashMap.put("tabla", tabla);
        return hashMap;
    }


}
