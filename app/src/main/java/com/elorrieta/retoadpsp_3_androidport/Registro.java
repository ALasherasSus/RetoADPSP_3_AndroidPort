package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etContra;
    private EditText etContra2;
    private SharedPreferences p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etUsuario=(EditText)findViewById(R.id.etUsuario);
        etContra=(EditText)findViewById(R.id.etContra);
        etContra2=(EditText)findViewById(R.id.etContra2);
    }

    public void registrarse(View v){
        //PROVISIONAL: DEBE CAMBIARSE AL CONECTAR LA APLICACIÓN A LA BASE DE DATOS
        p = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        if (etUsuario.getText().toString().length() == 0) {
            Toast notificacion= Toast.makeText(this,getString(R.string.introUsuario),Toast.LENGTH_LONG);
            notificacion.show();
        } else if (etContra.getText().toString().length() == 0) {
            Toast notificacion= Toast.makeText(this,getString(R.string.introContra),Toast.LENGTH_LONG);
            notificacion.show();
        } else if (!etContra.getText().toString().equals(etContra2.getText().toString())) {
            Toast notificacion= Toast.makeText(this,getString(R.string.registroContraDesigual),Toast.LENGTH_LONG);
            notificacion.show();
        } else if (etUsuario.getText().toString().length() > 0 && etContra.getText().toString().length() > 0 && etContra.getText().toString().equals(etContra2.getText().toString())) {
            editor.putString("usuario", etUsuario.getText().toString());
            editor.putString("password", etContra.getText().toString());
            editor.apply();
            Toast notificacion= Toast.makeText(this,getString(R.string.exito),Toast.LENGTH_LONG);
            notificacion.show();
            finish();
        }
    }
}