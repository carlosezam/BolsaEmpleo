package com.ittap.bolsadeempleo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ittap.bolsadeempleo.api.AsyncPostRequest;
import com.ittap.bolsadeempleo.api.RequestHandler;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;
import com.ittap.bolsadeempleo.api.VolleyMultiPartRequest;
import com.ittap.bolsadeempleo.models.Persona;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;
import com.ittap.bolsadeempleo.api.DataPart;

public class PersonalInfoActivity extends AppCompatActivity {

    EditText editText_curp;
    EditText editText_nombre;
    EditText editText_paterno;
    EditText editText_materno;
    EditText editText_domicilio;
    EditText editText_numero;
    EditText editText_fecha;
    Spinner spinner_estado_civil;
    CheckBox checkBox_licencia;
    Button button_guardar;
    RadioButton radio_h;
    RadioButton radio_m;

    ImageButton imageButton;
    private Uri imageUri;

    HashMap<String, EditText> map_views;
    boolean is_new_record = false;
    Usuario usuario;
    Persona persona;
    String[] edos_civiles = { "soltero(a)", "casado(a)", "divorciado(a)", "viudo(a)", "union libre" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        setTitle("Datos personales");
        usuario = SharedPrefManager.getInstance(this).getUsuario();

        imageButton = (ImageButton) findViewById(R.id.personal_photo);

        editText_curp = (EditText) findViewById(R.id.personal_curp);
        editText_nombre = (EditText) findViewById(R.id.personal_nombre);
        editText_paterno = (EditText) findViewById(R.id.personal_paterno);
        editText_materno = (EditText) findViewById(R.id.personal_materno);
        editText_domicilio = (EditText) findViewById(R.id.personal_domicilio);
        editText_numero = (EditText) findViewById(R.id.personal_numero);
        editText_fecha = (EditText) findViewById(R.id.personal_fecha_nac);
        spinner_estado_civil = (Spinner) findViewById(R.id.personal_estado);
        checkBox_licencia = (CheckBox) findViewById(R.id.personal_licencia);
        radio_h = (RadioButton) findViewById(R.id.personal_sexo_h);
        radio_m = (RadioButton) findViewById(R.id.personal_sexo_m);
        button_guardar = (Button) findViewById(R.id.personal_btn_guardar);

        List<String> estados_civiles = new ArrayList<>();
        for( String edo : edos_civiles) estados_civiles.add( edo );

        ArrayAdapter spinnerAdpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, estados_civiles);
        spinnerAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estado_civil.setAdapter(spinnerAdpater);

        Log.d("feha", editText_fecha.getText().toString() );

        is_new_record = true;
        persona = new Persona( usuario.id );
        get_user();

        map_views = new HashMap<>();
        map_views.put("curp", editText_curp );
        map_views.put("nombres", editText_nombre);
        map_views.put("ape_pat", editText_paterno);
        map_views.put("ape_mat", editText_materno );
        map_views.put("fecha_nac", editText_fecha);
        map_views.put("domicilio", editText_domicilio);
        map_views.put("telefono", editText_numero);

        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_user();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });

        get_image();
    }





















    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        final String tags = "image";



        //our custom volley request
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, URLs.CURRICULA_UPLOAD,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            //JSONObject obj = new JSONObject(new String(response.data));
                            //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            //show_toast("response: " + response );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", String.valueOf(usuario.id));
                params.put("tabla", "foto");
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {



                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("imageFile", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    protected void get_image()
    {
        String url_image = URLs.CURRICULA_IMAGE + usuario.id + ".jpg";
        ImageRequest imageRequest = new ImageRequest(url_image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageButton.setImageBitmap(response);
                        imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        show_toast("No se ha podido cargar la imagen");
                    }
                }
        );

        VolleySingleton.getInstance(this).addToRequestQueue( imageRequest );
    }

    protected void onSelectImageClick(View v)
    {
        CropImage.startPickImageActivity(PersonalInfoActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK )
        {
            Uri imgUri = CropImage.getPickImageResultUri(PersonalInfoActivity.this, data);

            startCropImageActivuty( imgUri );
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                ((ImageButton)findViewById(R.id.personal_photo)).setImageURI(result.getUri());


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());

                    uploadBitmap( bitmap );
                } catch ( IOException e)
                {
                    e.printStackTrace();
                }


                show_toast( "Cropped image: " + result.getUri());
            } else if( resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                show_toast("Cropping failed: " + result.getError());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startCropImageActivuty(Uri imageUri)
    {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setMinCropResultSize(200,200)
                .start(this);
    }
    protected Persona getPersona()
    {
        if(is_new_record) persona = new Persona( usuario.id );

        persona.curp = editText_curp.getText().toString();
        persona.nombres = editText_nombre.getText().toString();
        persona.ape_pat = editText_paterno.getText().toString();
        persona.ape_mat = editText_materno.getText().toString();
        persona.domicilio = editText_domicilio.getText().toString();
        persona.telefono = editText_numero.getText().toString();
        persona.fecha_nac = editText_fecha.getText().toString();
        persona.edo_civil = spinner_estado_civil.getSelectedItem().toString();
        persona.licencia = checkBox_licencia.isChecked();


        persona.sexo = radio_h.isChecked() ? "M" : (radio_m.isChecked() ? "F" : null);

        return persona;
    }

    protected void putPersona( Persona persona )
    {
        editText_curp.setText( persona.curp );
        editText_nombre.setText( persona.nombres );
        editText_paterno.setText( persona.ape_pat );
        editText_materno.setText( persona.ape_mat );
        editText_domicilio.setText( persona.domicilio );
        editText_numero.setText( persona.telefono );
        editText_fecha.setText( persona.fecha_nac );

        spinner_estado_civil.setSelection(0);
        for( int i = 0; i < edos_civiles.length; ++i )
        {
            if( edos_civiles[i].equalsIgnoreCase( persona.edo_civil) ) spinner_estado_civil.setSelection( i );
        }


        checkBox_licencia.setChecked( persona.licencia );


        if( persona.sexo.equalsIgnoreCase("F") )
            radio_m.setChecked(true);
        else
            radio_h.setChecked(true);


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
                if( map_views.containsKey( key ) ){
                    map_views.get( key ).setError( value );
                    map_views.get( key ).requestFocus();
                } else {
                    show_toast( value );
                    return;
                }

            }

        } catch ( JSONException e )
        {
            e.printStackTrace();
        }

    }
    protected void get_user(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRICULA_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response Listener", response );

                        try{
                            JSONArray jsonArray = new JSONArray(response);

                            if ( jsonArray.length() == 0 ) {
                                is_new_record = true;
                                return;
                            } else {
                                is_new_record = false;
                            }

                            JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                            persona = new Persona( jsonObject );

                            putPersona( persona );

                        } catch ( JSONException e )
                        {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if( error != null && error.networkResponse != null ) {
                            String data = new String(error.networkResponse.data);
                            show_toast(data);
                            Log.d("Response listener", data);
                        } else {
                            show_toast("error desconocido");
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = getPersona().toReadHashMap();
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }

    protected void set_user()
    {
        String url = is_new_record ? URLs.CURRICULA_CREATE : URLs.CURRICULA_UPDATE;
        getPersona().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        show_toast("datos guardados");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if( error != null )
                        {
                            String data = new String ( error.networkResponse.data );
                            Log.d("Error Listener", data );
                            Log.d("persona:", getPersona().toHashMap().toString() );
                            show_errors( data );
                        }
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return getPersona().toHashMap();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue( stringRequest );
    }
    protected void show_toast( String message )
    {
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
    }





}
