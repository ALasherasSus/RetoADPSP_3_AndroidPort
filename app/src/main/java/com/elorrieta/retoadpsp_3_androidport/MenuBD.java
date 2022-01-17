package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MenuBD extends AppCompatActivity {

    String user;
    String password;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bd);

        Bundle bundle = getIntent().getExtras();
        user=bundle.getString("Usuario");
        password=bundle.getString("Contraseña");

    }
}