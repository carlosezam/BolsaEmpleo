package com.ittap.bolsadeempleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ittap.bolsadeempleo.api.URLs;
import com.ittap.bolsadeempleo.curriculum.Trabajo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsEmpleoActivity extends AppCompatActivity {


    TextView puesto, salario, descripcion, vacantes, empresa, domicilio, active, municipio, contacto;

    Empleo data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_empleo);

        init_views();
    }

    protected void init_views()
    {
        puesto = (TextView) findViewById(R.id.empleo_puesto);
        salario = (TextView) findViewById(R.id.empleo_salario);
        descripcion = (TextView) findViewById(R.id.empleo_descripcion);
        vacantes = (TextView) findViewById(R.id.empleo_vacantes);
        empresa = (TextView) findViewById(R.id.empleo_Empresa);
        domicilio = (TextView) findViewById(R.id.empleo_domicilio);
        municipio = (TextView) findViewById(R.id.empleo_municipio);
        active = (TextView) findViewById(R.id.empleo_active);
        contacto = (TextView) findViewById(R.id.empleo_contacto);
        data = new Empleo();

        Intent intent = getIntent();

        long id_empleo = intent.getLongExtra("id",0);

        read_data( id_empleo );

    }

    protected void read_data(final long id)
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
                hashMap.put("tabla", "empleos");
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue( request );
    }


    protected void put_form_data( Empleo data )
    {
        empresa.setText(data.empresa);
        puesto.setText( data.puesto);
        salario.setText( String.format("$ %.2f", data.salario));
        descripcion.setText( data.descripcion);
        vacantes.setText( String.valueOf( data.vacantes));
        domicilio.setText( data.domicilio );
        municipio.setText( data.municipio );
        active.setText( data.active == 1 ? "Si" : "No");
        contacto.setText( data.contacto );

    }
}
