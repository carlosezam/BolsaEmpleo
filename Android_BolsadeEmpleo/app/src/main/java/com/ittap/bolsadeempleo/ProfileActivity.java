package com.ittap.bolsadeempleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ittap.bolsadeempleo.api.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    TextView textView_username;
    TextView textView_email;
    Button button_logout;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(ProfileActivity.this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textView_username = (TextView) findViewById(R.id.profile_textView_username);
        textView_email = (TextView) findViewById(R.id.profile_textView_email);
        button_logout = (Button) findViewById(R.id.profile_button_logout);

        usuario = SharedPrefManager.getInstance(this).getUsuario();

        textView_email.setText(usuario.email);
        textView_username.setText(usuario.username);

        findViewById(R.id.profile_button_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}
