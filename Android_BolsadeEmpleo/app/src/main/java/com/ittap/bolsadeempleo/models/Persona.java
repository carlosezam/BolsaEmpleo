package com.ittap.bolsadeempleo.models;

import com.ittap.bolsadeempleo.api.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;


public class Persona {
    public int id = 0;
    public int id_usuario;
    public String curp;
    public String nombres;
    public String ape_pat;
    public String ape_mat;
    public String telefono;
    public String domicilio;
    public String sexo;
    public String fecha_nac;
    public String edo_civil;
    public boolean licencia;

    public Persona( int id_usuario )
    {
        this.id_usuario = id_usuario;
    }

    public Persona( int id, int id_usuario, String curp, String nombres, String ape_pat, String ape_mat, String telefono, String domicilio, String edo_civil, String fecha_nac, boolean licencia)
    {
        this.id = id;
        this.id_usuario = id_usuario;
        this.curp = curp;
        this.nombres = nombres;
        this.ape_pat = ape_pat;
        this.ape_mat = ape_mat;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.edo_civil = edo_civil;
        this.fecha_nac = fecha_nac;
        this.licencia = licencia;
    }

    public Persona(JSONObject persona) throws JSONException
    {
        id = persona.has("id") ? persona.getInt("id") : 0;
        id_usuario = persona.has("id_usuario") ? persona.getInt("id_usuario") : 0;
        curp = persona.has("curp") ? persona.getString("curp") : "";
        nombres = persona.has("nombres") ? persona.getString("nombres") : "";
        ape_pat = persona.has("ape_pat") ? persona.getString("ape_pat") : "";
        ape_mat = persona.has("ape_mat") ? persona.getString("ape_mat") : "";
        sexo = persona.has("sexo") ? persona.getString("sexo") : "";
        telefono = persona.has("telefono") ? persona.getString("telefono") : "";
        domicilio = persona.has("domicilio") ? persona.getString("domicilio") : "";
        edo_civil = persona.has("edo_civil") ? persona.getString("edo_civil") : "";
        fecha_nac = persona.has("fecha_nac") ? persona.getString("fecha_nac") : "";
        licencia = persona.has("licencia") ? (persona.getInt("licencia") == 1) : false;
    }

    public HashMap<String,String> toHashMap()
    {
        HashMap<String,String> hashMap = new HashMap<>();
        if( id != 0 ) hashMap.put("id", String.valueOf(id));
        hashMap.put("id_usuario", String.valueOf(id_usuario));
        hashMap.put("curp", curp);
        hashMap.put("nombres", nombres);
        hashMap.put("ape_pat", ape_pat);
        hashMap.put("ape_mat", ape_mat);
        hashMap.put("domicilio", domicilio);
        hashMap.put("sexo", sexo);
        hashMap.put("telefono", telefono);
        hashMap.put("edo_civil", edo_civil);
        hashMap.put("fecha_nac", fecha_nac);
        hashMap.put("licencia", licencia ? "1":"0" );

        hashMap.put("tabla","personal");
        return hashMap;
    }

    public HashMap<String,String> toReadHashMap()
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("tabla", "personal");
        hashMap.put("id_usuario", String.valueOf(id_usuario));
        return hashMap;
    }
}
