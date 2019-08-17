package com.ittap.bolsadeempleo.curriculum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ittap.bolsadeempleo.R;
import com.ittap.bolsadeempleo.Usuario;
import com.ittap.bolsadeempleo.VolleySingleton;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompetenciaActivity extends AppCompatActivity {

    EditText nombre;
    EditText descripcion;

    Button btnGuardar;
    Button btnCancelar;

    private final String TABLA = "competencias";
    Competencia data;
    HashMap<String,EditText> editTextHashMap;
    Usuario usuario;
    String action;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competencia);

        init_views();

        usuario = SharedPrefManager.getInstance(this).getUsuario();
        Competencia.id_usuario = usuario.id;

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        id = intent.getLongExtra("id",0);

        data = new Competencia();
        if( action.equals("read") || action.equals("update"))  read_data();
        if( action.equals("create") || action.equals("update") ) set_editable( true ); else set_editable( false );
    }

    protected void set_editable( boolean editable )
    {
        nombre.setEnabled( editable );
        descripcion.setEnabled( editable );
        btnGuardar.setEnabled( editable );
    }

    protected void init_views()
    {
        nombre = (EditText)findViewById(R.id.competencia_nombre);
        descripcion = (EditText)findViewById(R.id.competencia_descripcion);

        btnGuardar = (Button) findViewById(R.id.competencia_btn_guardar);
        btnCancelar = (Button) findViewById(R.id.competencia_btn_cancelar);

        editTextHashMap = new HashMap<>();
        editTextHashMap.put("nombre", nombre);
        editTextHashMap.put("descripcion",descripcion);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_data();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void save_data()
    {
        get_form_data();

        String url;
        if ( action.equals("create") ) url = URLs.CURRICULA_CREATE; else  url = URLs.CURRICULA_UPDATE;

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clear_errors();
                        show_toast("datos guardados");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error != null )
                {
                    String response_data = new String ( error.networkResponse.data );
                    Log.d("Error Listener", response_data );
                    Log.d("persona:", data.toHashMap().toString() );
                    show_errors( response_data );
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data.toHashMap();
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( request );
    }

    protected void read_data()
    {
        StringRequest request = new StringRequest(Request.Method.POST, URLs.CURRICULA_VIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            data.setFromJson(jsonObject);
                            put_form_data( data );
                        } catch ( JSONException e ){ e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id_usuario", String.valueOf(usuario.id));
                hashMap.put("tabla", TABLA);
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( request );
    }

    protected void get_form_data() {
        data.nombre = nombre.getText().toString();
        data.descripcion = descripcion.getText().toString();
    }

    protected void put_form_data( Competencia data )
    {
        nombre.setText(data.nombre);
        descripcion.setText( data.descripcion );
    }

    protected void show_toast( String message )
    {
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
    }

    protected void clear_errors()
    {
        for ( HashMap.Entry<String,EditText> entry : editTextHashMap.entrySet() )
        {
            if ( entry.getValue() != null ) entry.getValue().setError(null);
        }
    }

    protected void show_errors( String data )
    {

        try {
            JSONObject jsonObject = new JSONObject( data );
            Iterator<String> errors = jsonObject.keys();
            EditText editText = null;
            while ( errors.hasNext() )
            {
                String key = errors.next();
                String value = jsonObject.getString( key );
                if( editTextHashMap.containsKey( key ) && ( editText = editTextHashMap.get(key)) != null ){
                    editText.setError( value );
                    editText.requestFocus();
                } else {
                    show_toast( value );
                }

                return;

            }

        } catch ( JSONException e )
        {
            e.printStackTrace();
        }

    }
}
