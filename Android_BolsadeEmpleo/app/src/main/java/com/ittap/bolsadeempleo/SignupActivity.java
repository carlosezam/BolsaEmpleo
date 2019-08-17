package com.ittap.bolsadeempleo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ittap.bolsadeempleo.api.RequestHandler;
import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText editText_username;
    EditText editText_email;
    EditText editText_password;
    EditText editText_confirm;
    RelativeLayout layaout;

    HashMap<String,View> map_views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        layaout = (RelativeLayout) findViewById(R.id.layaout_signup);

        editText_email = (EditText) layaout.findViewById(R.id.editText_email);
        editText_username = (EditText) layaout.findViewById(R.id.editText_username);
        editText_password = (EditText) layaout.findViewById(R.id.editText_password);
        editText_confirm = (EditText) layaout.findViewById(R.id.editText_confirm);

        map_views = new HashMap<>();
        map_views.put( "email", editText_email );
        map_views.put("username", editText_username);
        map_views.put("password", editText_password);
        map_views.put("confirm", editText_confirm );

        layaout.findViewById(R.id.button_singup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup_user();
            }
        });

        layaout.findViewById(R.id.textview_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignupActivity.this, ProfileActivity.class));
            }
        });
    }

    private void signup_user()
    {


        final String username = editText_username.getText().toString().trim();
        final String email = editText_email.getText().toString().trim();
        final String password = editText_password.getText().toString().trim();
        final String confirm = editText_confirm.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            editText_username.setError("Ingresa un nombre de usuario");
            editText_username.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
            editText_email.setError("Ingresa un correo electrónico");
            editText_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editText_email.setError("Ingresa un correo valido");
            editText_email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password))
        {
            editText_password.setError("Ingresa una contraseña");
            editText_password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirm))
        {
            editText_confirm.setError("Ingresa nuevamente tu contraseña");
            editText_confirm.requestFocus();
            return;
        }

        if(!confirm.equals(password))
        {
            editText_confirm.setError("Las contraseñas no coinciden");
            editText_confirm.requestFocus();
            return;
        }



        class SignupTask extends AsyncTask<Void, Void, HashMap<String,String> > {

            private ProgressBar progressBar;

            @Override
            protected HashMap<String,String> doInBackground(Void... params) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String,String> data = new HashMap<>();
                data.put("username", username);
                data.put("email", email);
                data.put("password", password);
                return requestHandler.sendPostRequest(URLs.URI_SIGNUP, data);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressbar_singup);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(HashMap<String,String> s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                int response_code = 0;
                String response_data = "";
                String response_message = "";
                //Toast.makeText(SignupActivity.this, "OnPostExecute: " + s.toString(), Toast.LENGTH_LONG ).show();

                if( s == null ) {
                    show_toast("Error desconocido");
                    return;
                }

                if(s.containsKey("exception")){
                    show_toast("Exception: " + s.get("exception"));
                    return;
                }

                if(s.containsKey("response_code") ){
                    //show_toast("Response code:" + s.get("response_code"));
                    response_code = Integer.parseInt(s.get("response_code"));
                }
                if(s.containsKey("response_message") ){
                    //show_toast("Response code:" + s.get("response_code"));
                    response_message = s.get("response_message");
                }
                if(!s.containsKey("response_data")){
                    show_toast("No se recibió ningun dato");
                    return;
                }

                response_data = s.get("response_data");

                try{

                    if( response_code == 201 )
                    {
                        show_toast("Usuario registrado");
                        JSONObject user = new JSONObject(response_data);
                        Log.d("JSON Parsing", "json_user: " + user.toString());

                        Usuario usuario = new Usuario(
                                user.getInt("id"),
                                user.getString("username"),
                                user.getString("email"),
                                user.getString("auth_key")
                        );

                        SharedPrefManager.getInstance(getApplicationContext()).user_login(usuario);

                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else if( response_code == 422 ){

                        JSONArray json_errors = new JSONArray(response_data);
                        Log.d("JSON Parsing", "json_errors: " + json_errors.toString() );

                        for ( int i = 0; i < json_errors.length(); ++i )
                        {
                            JSONObject error = json_errors.getJSONObject(i);
                            String field = error.getString("field");
                            String message = error.getString("message");
                            EditText view = (EditText) map_views.get(field);
                            view.setError(message);
                            view.requestFocus();
                            return;
                        }


                    } else {
                        show_toast( String.format("response code: %03d, response message:%s\nresponse data:%s", response_code, response_data));
                    }

                } catch (JSONException e)
                {
                    show_toast("Datos recibidos no reconocidos");
                }


            }


        }

        SignupTask signupTask = new SignupTask();
        signupTask.execute();


    }

    private void set_validation_errors()
    {

    }
    private void show_toast( String message )
    {
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG ).show();
    }


}
