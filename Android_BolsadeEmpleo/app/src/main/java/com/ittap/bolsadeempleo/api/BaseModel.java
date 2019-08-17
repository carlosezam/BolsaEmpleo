package com.ittap.bolsadeempleo.api;

import com.ittap.bolsadeempleo.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public abstract class BaseModel {


    public abstract void setFromJson(JSONObject jsonObject) throws JSONException;

    public abstract int getId();
    public abstract Class getFormActivity();


    public abstract String toString();
    public abstract HashMap<String,String> toHashMap();
}
