package com.ittap.bolsadeempleo.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ittap.bolsadeempleo.LoginActivity;
import com.ittap.bolsadeempleo.Usuario;



public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "bolsadeempleosharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_AUTH_KEY = "keyauthkey";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager instance;
    private static Context context;

    private SharedPrefManager(Context context)
    {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if ( instance == null )
        {
            instance = new SharedPrefManager(context);
        }

        return instance;
    }

    public void user_login(Usuario usuario)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, usuario.id );
        editor.putString(KEY_USERNAME, usuario.username);
        editor.putString(KEY_EMAIL, usuario.email);
        editor.putString(KEY_AUTH_KEY, usuario.auth_key);
        editor.apply();
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public Usuario getUsuario()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Usuario(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_AUTH_KEY, null)
        );
    }

    public void logout()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}


