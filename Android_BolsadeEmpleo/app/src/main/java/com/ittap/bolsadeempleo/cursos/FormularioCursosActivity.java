package com.ittap.bolsadeempleo.cursos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.ittap.bolsadeempleo.VolleySingleton;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FormularioCursosActivity extends AppCompatActivity {

    EditText editText_nombre;
    EditText editText_descripion;
    EditText editText_fecha_ini;
    EditText editText_fecha_fin;
    EditText editText_horas;

    Button button_guardar;
    Button button_candelar;
    Button button_eliminar;

    int id_usuario;
    Curso curso;
    HashMap<String,EditText> mapEditText;
    String action;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cursos);
        setTitle("Datos del curso");
        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        if ( action.equals("read") || action.equals("update"))
        {
            id = intent.getLongExtra("id",1);
        }

        id_usuario = SharedPrefManager.getInstance(this).getUsuario().id;
        curso = new Curso( id_usuario );

        editText_nombre = (EditText) findViewById(R.id.cursos_nombre);
        editText_descripion = (EditText) findViewById(R.id.cursos_descripcion);
        editText_fecha_ini = (EditText) findViewById(R.id.cursos_fecha_ini);
        editText_fecha_fin = (EditText) findViewById(R.id.cursos_fecha_fin);
        editText_horas = (EditText) findViewById(R.id.cursos_horas);

        button_guardar = (Button) findViewById(R.id.cursos_btn_guardar);
        button_candelar = (Button) findViewById(R.id.cursos_btn_cancelar);

        mapEditText = new HashMap<>();
        mapEditText.put("nombre", editText_nombre);
        mapEditText.put("descripcion", editText_descripion);
        mapEditText.put("fecha_ini", editText_fecha_ini);
        mapEditText.put("fecha_fin", editText_fecha_fin);
        mapEditText.put("horas", editText_horas);




        if ( action.equals("read") || action.equals("update"))
        {
            cargar_curso();
        }

        if( action.equals("create") || action.equals("update"))  {

            establecer_modo( true );
        } else {
            establecer_modo( false );
        }

        button_candelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if( action.equals("update") || action.equals("create")) {

            button_guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guardar_curso();
                }
            });


        }
    }

    public void establecer_modo( boolean enabled )
    {
        editText_nombre.setEnabled( enabled );
        editText_descripion.setEnabled( enabled );
        editText_fecha_ini.setEnabled( enabled );
        editText_fecha_fin.setEnabled( enabled );
        editText_horas.setEnabled( enabled );
        button_guardar.setEnabled( enabled );
    }

    public void update_model_gurso()
    {
        curso.nombre = editText_nombre.getText().toString();
        curso.descripcion = editText_descripion.getText().toString();
        curso.fecha_ini = editText_fecha_ini.getText().toString();
        curso.fecha_fin = editText_fecha_fin.getText().toString();

        String string_horas = editText_horas.getText().toString();

        curso.horas = TextUtils.isEmpty( string_horas )? 0 : Integer.parseInt( string_horas );
    }

    public void put_curso( Curso curso )
    {
        editText_nombre.setText( curso.nombre );
        editText_descripion.setText( curso.descripcion );
        editText_fecha_ini.setText( curso.fecha_ini );
        editText_fecha_fin.setText( curso.fecha_fin );
        editText_horas.setText( String.valueOf( curso.horas ) );
    }
    protected void cargar_curso()
    {
        StringRequest request = new StringRequest(Request.Method.POST, URLs.CURRICULA_VIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            curso = new Curso(jsonObject);

                            put_curso( curso );
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
                hashMap.put("tabla", "cursos");
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( request );
    }

    protected void guardar_curso()
    {
        update_model_gurso();

        String url;
        if ( action.equals("create") )
            url = URLs.CURRICULA_CREATE;
        else
            url = URLs.CURRICULA_UPDATE;

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        show_toast("datos guardados");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if( error != null )
                        {
                            String data = new String ( error.networkResponse.data );
                            Log.d("Error Listener", data );
                            Log.d("persona:", curso.toHashMap().toString() );
                            show_errors( data );
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                    return curso.toHashMap();
                }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( request );
    }

    protected void eliminar_curso()
    {

    }

    protected void show_errors( String data )
    {
        try {
            JSONObject jsonObject = new JSONObject( data );
            Iterator<String> errors = jsonObject.keys();

            while ( errors.hasNext() )
            {
                String key = errors.next();
                String value = jsonObject.getString( key );
                if( mapEditText.containsKey( key ) ){
                    mapEditText.get( key ).setError( value );
                    mapEditText.get( key ).requestFocus();
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

    protected void show_toast( String message )
    {
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
    }


}
