package com.ittap.bolsadeempleo.curriculum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ittap.bolsadeempleo.R;
import com.ittap.bolsadeempleo.VolleySingleton;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;
import com.ittap.bolsadeempleo.cursos.Curso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EscolarActivity extends AppCompatActivity {

    EditText escuela;
    Spinner nivel;
    EditText documento;
    EditText profesion;
    EditText fecha_ini;
    EditText fecha_fin;

    Button btnGuardar;
    Button btnCancelar;

    ArrayAdapter spinner_adapter;

    HashMap<String,EditText> editTextHashMap;
    String action = "create";
    long id = 0;
    int id_usuario = 0;

    Escolar data;
    private final String TABLA = "escolares";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolar);

        //Referencia las vistas
        init_views();

        // Obtiene el id del usuario atual
        id_usuario = SharedPrefManager.getInstance(this).getUsuario().id;
        Escolar.id_usuario = id_usuario;

        // Obtiene los extras pasados por el Intent
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        id = intent.getLongExtra("id",0);

        data = new Escolar();

        if( action.equals("read") || action.equals("update"))  read_data();
        if( action.equals("create") || action.equals("update") ) set_editable( true ); else set_editable( false );

    }

    protected void set_editable( boolean editable )
    {
        escuela.setEnabled( editable );
        nivel.setEnabled( editable );
        documento.setEnabled( editable );
        profesion.setEnabled( editable );
        fecha_ini.setEnabled( editable );
        fecha_fin.setEnabled( editable );
        btnGuardar.setEnabled( editable );
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
                hashMap.put("id_usuario", String.valueOf(id_usuario));
                hashMap.put("tabla", TABLA);
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( request );
    }

    protected void get_form_data()
    {
        data.escuela = escuela.getText().toString();
        data.no_nivel = nivel.getSelectedItemPosition();
        data.documento = documento.getText().toString();
        data.profescion = profesion.getText().toString();
        data.fecha_ini = fecha_ini.getText().toString();
        data.fecha_fin = fecha_fin.getText().toString();
    }
    protected void put_form_data( Escolar data )
    {
        escuela.setText(data.escuela);
        nivel.setSelection( data.no_nivel );
        documento.setText( data.documento );
        profesion.setText( data.profescion );
        fecha_ini.setText( data.fecha_ini );
        fecha_fin.setText( data.fecha_fin );
    }

    protected void init_views()
    {
        escuela = (EditText) findViewById(R.id.escolar_escuela);
        nivel = (Spinner) findViewById(R.id.escolar_nivel);
        documento = (EditText) findViewById(R.id.escolar_documento);
        profesion = (EditText) findViewById(R.id.escolar_profesion);
        fecha_ini = (EditText) findViewById(R.id.escolar_fecha_ini);
        fecha_fin = (EditText) findViewById(R.id.escolar_fecha_fin);

        btnGuardar = (Button) findViewById(R.id.escolar_btn_guardar);
        btnCancelar = (Button) findViewById(R.id.escolar_btn_cancelar);

        spinner_adapter = ArrayAdapter.createFromResource( EscolarActivity.this, R.array.escolar_niveles, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivel.setAdapter(spinner_adapter);

        editTextHashMap = new HashMap<>();
        editTextHashMap.put("escuela", escuela);
        editTextHashMap.put("no_nivel", null);
        editTextHashMap.put("documento",documento);
        editTextHashMap.put("profesion", profesion);
        editTextHashMap.put("fecha_ini", fecha_ini);
        editTextHashMap.put("fecha_fin", fecha_fin);

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
}
