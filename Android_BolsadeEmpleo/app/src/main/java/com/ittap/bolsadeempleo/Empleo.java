package com.ittap.bolsadeempleo;

import com.ittap.bolsadeempleo.api.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Empleo extends BaseModel {

    public static final String TABLE_NAME = "empleos";


    public int id;

    public String puesto;
    public double salario;
    public String descripcion;
    public int vacantes;
    public String domicilio;
    public String empresa;
    public String municipio;
    public String contacto;
    public int active;

    @Override
    public void setFromJson(JSONObject jsonObject) throws JSONException {
        id          = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
        puesto      = jsonObject.has("puesto") ? jsonObject.getString("puesto") : "";
        salario     = jsonObject.has("salario") ? jsonObject.getDouble("salario") : 0.0;
        descripcion = jsonObject.has("descripcion") ? jsonObject.getString("descripcion") : "";
        vacantes    = jsonObject.has("vacantes") ? jsonObject.getInt("vacantes") : 0;
        domicilio      = jsonObject.has("domicilio") ? jsonObject.getString("domicilio") : "";
        empresa      = jsonObject.has("empresa") ? jsonObject.getString("empresa") : "";
        municipio      = jsonObject.has("municipio") ? jsonObject.getString("municipio") : "";
        contacto      = jsonObject.has("contacto") ? jsonObject.getString("contacto") : "";
        active          = jsonObject.has("active") ? jsonObject.getInt("active") : 0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Class getFormActivity() {
        return null;
    }

    @Override
    public String toString() {
        return puesto + " " + empresa;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        return null;
    }
}
