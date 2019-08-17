package com.ittap.bolsadeempleo;

import com.ittap.bolsadeempleo.api.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



public class Save extends BaseModel {
    public int id;
    public int id_empleo;
    public String empleo;
    public String empresa;

    @Override
    public void setFromJson(JSONObject jsonObject) throws JSONException {
        id      = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
        id_empleo      = jsonObject.has("id_empleo") ? jsonObject.getInt("id_empleo") : 0;
        empleo      = jsonObject.has("empleo") ? jsonObject.getString("empleo") : "";
        empresa      = jsonObject.has("empresa") ? jsonObject.getString("empresa") : "";
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
        return empresa + " " + empleo;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        return null;
    }
}
